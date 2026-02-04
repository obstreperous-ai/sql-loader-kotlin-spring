package ai.obstreperous.sqlloader

import org.springframework.stereotype.Component
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters
import java.io.File
import java.util.concurrent.Callable

@Component
@Command(
    name = "sql-loader",
    mixinStandardHelpOptions = true,
    version = ["0.1.0"],
    description = ["Executes SQL scripts from the command line"]
)
class SqlLoaderCommand(
    private val sqlLoaderService: SqlLoaderService
) : Callable<Int> {

    @Parameters(
        index = "0",
        description = ["Path to the SQL file to execute"]
    )
    lateinit var sqlFile: File

    @Option(
        names = ["-u", "--url"],
        description = ["JDBC URL"],
        required = true
    )
    lateinit var jdbcUrl: String

    @Option(
        names = ["-n", "--username"],
        description = ["Database username"],
        required = true
    )
    lateinit var username: String

    @Option(
        names = ["-p", "--password"],
        description = ["Database password"],
        required = false,
        defaultValue = ""
    )
    var password: String = ""

    override fun call(): Int {
        return try {
            if (!sqlFile.exists()) {
                System.err.println("Error: SQL file not found: ${sqlFile.absolutePath}")
                return 1
            }

            val config = DatabaseConfig(jdbcUrl, username, password)
            sqlLoaderService.executeSqlFile(sqlFile, config)
            println("SQL file executed successfully: ${sqlFile.name}")
            0
        } catch (e: Exception) {
            System.err.println("Error executing SQL file: ${e.message}")
            e.printStackTrace()
            1
        }
    }
}

data class DatabaseConfig(
    val jdbcUrl: String,
    val username: String,
    val password: String
)
