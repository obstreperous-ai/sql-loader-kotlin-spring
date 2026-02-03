# Copilot Instructions for sql-loader-kotlin-spring

## Project Overview
This is a lean CLI utility in Kotlin using Spring Boot, Gradle, and JDBC. The tool executes a SQL data load script from the command line in a minimal container for K8s environments.

## Core Principles
- **Minimalism**: Keep the codebase lean and focused. Avoid unnecessary dependencies or complexity.
- **Test-First Development**: Write tests before implementation. All new features require tests.
- **High Code Quality**: Maintain clean, readable, and maintainable code.
- **Container-Ready**: Optimize for minimal container size and fast startup for K8s deployments.

## Build Commands
- `./gradlew build` — Build the project
- `./gradlew test` — Run all tests
- `./gradlew clean` — Clean build artifacts
- `./gradlew bootJar` — Create executable JAR

## Code Style and Conventions

### Kotlin
- Follow official Kotlin coding conventions
- Use idiomatic Kotlin features (data classes, extension functions, null safety)
- Prefer immutability (val over var)
- Use meaningful, descriptive names for classes, functions, and variables
- Keep functions small and focused on a single responsibility

### Spring Boot
- Use constructor injection (avoid field injection)
- Prefer configuration properties over hardcoded values
- Use Spring Boot starter dependencies for consistency
- Keep Spring configuration minimal and explicit

### Testing
- Write unit tests for all business logic
- Use descriptive test names that explain the scenario and expected outcome
- Follow AAA pattern: Arrange, Act, Assert
- Use JUnit 5 and appropriate assertion libraries
- Mock external dependencies appropriately

### Gradle
- Keep build.gradle.kts clean and organized
- Document any non-standard dependencies or configuration
- Use Kotlin DSL for Gradle configuration

## Workflow Requirements
- All pull requests must include appropriate tests
- Code must pass all existing tests before merging
- Keep commits atomic and focused
- Use clear, descriptive commit messages

## Project-Specific Guidelines
- JDBC interactions should be simple and direct (no ORM overhead)
- SQL scripts are the primary interface for data loading
- Error handling should be clear and actionable for CLI users
- Logging should be minimal but informative for debugging
- Command-line interface should follow standard CLI conventions
