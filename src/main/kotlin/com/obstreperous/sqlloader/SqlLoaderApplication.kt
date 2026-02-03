package com.obstreperous.sqlloader

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import picocli.CommandLine
import picocli.CommandLine.IFactory
import org.springframework.stereotype.Component

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class SqlLoaderApplication

fun main(args: Array<String>) {
    System.exit(
        runApplication<SqlLoaderApplication>(*args) { }
            .getBean(CommandLineRunner::class.java)
            .let { 0 }
    )
}

@Component
class SqlLoaderRunner(
    private val sqlLoaderCommand: SqlLoaderCommand,
    private val factory: IFactory
) : CommandLineRunner {
    override fun run(vararg args: String) {
        val exitCode = CommandLine(sqlLoaderCommand, factory).execute(*args)
        if (exitCode != 0) {
            throw RuntimeException("SQL Loader failed with exit code $exitCode")
        }
    }
}
