package com.obstreperous.sqlloader

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.ExitCodeGenerator
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import picocli.CommandLine
import picocli.CommandLine.IFactory
import org.springframework.stereotype.Component
import kotlin.system.exitProcess

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class SqlLoaderApplication

fun main(args: Array<String>) {
    exitProcess(SpringApplication.exit(SpringApplication.run(SqlLoaderApplication::class.java, *args)))
}

@Component
class SqlLoaderRunner(
    private val sqlLoaderCommand: SqlLoaderCommand,
    private val factory: IFactory
) : CommandLineRunner, ExitCodeGenerator {
    
    private var exitCode: Int = 0

    override fun run(vararg args: String) {
        exitCode = CommandLine(sqlLoaderCommand, factory).execute(*args)
    }

    override fun getExitCode(): Int = exitCode
}
