package ai.obstreperous.sqlloader

import org.springframework.stereotype.Service
import java.io.File
import java.sql.Connection
import java.sql.DriverManager

@Service
class SqlLoaderService {

    fun executeSqlFile(sqlFile: File, config: DatabaseConfig) {
        val sql = sqlFile.readText()
        executeStatements(sql, config)
    }

    private fun executeStatements(sql: String, config: DatabaseConfig) {
        getConnection(config).use { connection ->
            connection.autoCommit = false
            try {
                val statements = splitSqlStatements(sql)
                statements.forEach { statement ->
                    if (statement.isNotBlank()) {
                        connection.createStatement().use { stmt ->
                            stmt.execute(statement)
                        }
                    }
                }
                connection.commit()
            } catch (e: Exception) {
                connection.rollback()
                throw e
            }
        }
    }

    private fun getConnection(config: DatabaseConfig): Connection {
        return DriverManager.getConnection(config.jdbcUrl, config.username, config.password)
    }

    private fun splitSqlStatements(sql: String): List<String> {
        // Simple semicolon split - can be enhanced for more complex SQL
        return sql.split(";")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
    }
}
