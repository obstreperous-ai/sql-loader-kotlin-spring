package com.obstreperous.sqlloader

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.nio.file.Files
import java.sql.DriverManager

@Testcontainers
class SqlLoaderServiceTest {

    companion object {
        @Container
        @JvmStatic
        val postgres: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
    }

    @Test
    fun `should execute SQL file successfully`() {
        // Arrange
        val service = SqlLoaderService()
        val tempFile = Files.createTempFile("test", ".sql").toFile()
        tempFile.writeText("""
            CREATE TABLE users (
                id SERIAL PRIMARY KEY,
                name VARCHAR(100) NOT NULL
            );
            
            INSERT INTO users (name) VALUES ('Alice');
            INSERT INTO users (name) VALUES ('Bob');
        """.trimIndent())

        val config = DatabaseConfig(
            jdbcUrl = postgres.jdbcUrl,
            username = postgres.username,
            password = postgres.password
        )

        // Act
        service.executeSqlFile(tempFile, config)

        // Assert
        DriverManager.getConnection(config.jdbcUrl, config.username, config.password).use { conn ->
            conn.createStatement().use { stmt ->
                val rs = stmt.executeQuery("SELECT COUNT(*) FROM users")
                rs.next()
                assertEquals(2, rs.getInt(1))
            }
        }

        // Cleanup
        tempFile.delete()
    }

    @Test
    fun `should rollback on error`() {
        // Arrange
        val service = SqlLoaderService()
        val tempFile = Files.createTempFile("test", ".sql").toFile()
        tempFile.writeText("""
            CREATE TABLE test_table (
                id SERIAL PRIMARY KEY
            );
            
            INSERT INTO invalid_table VALUES (1);
        """.trimIndent())

        val config = DatabaseConfig(
            jdbcUrl = postgres.jdbcUrl,
            username = postgres.username,
            password = postgres.password
        )

        // Act & Assert
        var exceptionThrown = false
        try {
            service.executeSqlFile(tempFile, config)
        } catch (e: Exception) {
            exceptionThrown = true
            assertTrue(e.message?.contains("invalid_table") == true)
        }

        assertTrue(exceptionThrown)

        // Verify table wasn't created due to rollback
        DriverManager.getConnection(config.jdbcUrl, config.username, config.password).use { conn ->
            conn.createStatement().use { stmt ->
                var tableExists = false
                try {
                    stmt.executeQuery("SELECT 1 FROM test_table LIMIT 1")
                    tableExists = true
                } catch (e: Exception) {
                    // Expected - table should not exist
                }
                assertFalse(tableExists)
            }
        }

        // Cleanup
        tempFile.delete()
    }

    @Test
    fun `should handle empty SQL file`() {
        // Arrange
        val service = SqlLoaderService()
        val tempFile = Files.createTempFile("test", ".sql").toFile()
        tempFile.writeText("")

        val config = DatabaseConfig(
            jdbcUrl = postgres.jdbcUrl,
            username = postgres.username,
            password = postgres.password
        )

        // Act - should not throw
        service.executeSqlFile(tempFile, config)

        // Cleanup
        tempFile.delete()
    }
}
