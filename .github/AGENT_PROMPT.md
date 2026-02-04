# Agent Prompt Examples for sql-loader-kotlin-spring

This file contains example prompts for AI coding agents working on this project.

## Project Context

This is a lean CLI utility in Kotlin using Spring Boot, Gradle, and JDBC. The tool executes SQL data load scripts from the command line in a minimal container for Kubernetes environments.

## Core Principles

1. **Minimalism**: Keep the codebase lean and focused
2. **Test-First Development**: Write tests before implementation
3. **High Code Quality**: Maintain clean, readable code
4. **Container-Ready**: Optimize for minimal size and fast startup

## Example Iteration Prompts

### Adding a New Feature

```
I'd like to add support for environment variable substitution in SQL files.

Requirements:
- Allow placeholders like ${DB_NAME} in SQL files
- Replace with environment variables at runtime
- Add tests for the substitution logic
- Update README with examples

Please:
1. Start with tests
2. Implement the minimal changes needed
3. Update documentation
4. Ensure backward compatibility
```

### Fixing a Bug

```
The SQL loader doesn't properly handle multi-line comments in SQL files.

Steps to reproduce:
1. Create an SQL file with /* ... */ comments
2. Run the loader
3. It fails with a parsing error

Please:
1. Add a test that reproduces the issue
2. Fix the SQL statement splitting logic
3. Ensure single-line (--) comments still work
4. Run all tests to ensure no regressions
```

### Improving Performance

```
The SQL loader is slow for large files. Profile and optimize the execution.

Please:
1. Add a test with a large SQL file (1000+ statements)
2. Identify performance bottlenecks
3. Implement optimizations (e.g., batch execution)
4. Measure and document the improvement
5. Ensure all existing tests still pass
```

### Adding Database Support

```
Add support for MySQL in addition to PostgreSQL.

Requirements:
- Auto-detect database type from JDBC URL
- Use appropriate SQL syntax for each database
- Add MySQL tests using Testcontainers
- Update README with MySQL examples

Please:
1. Create tests for MySQL
2. Implement minimal changes to support both databases
3. Update documentation
4. Keep the codebase lean
```

### Refactoring

```
The SqlLoaderService class is getting complex. Refactor for better maintainability.

Please:
1. Identify responsibilities that can be extracted
2. Create separate classes/functions with single responsibilities
3. Ensure all tests still pass
4. Keep the public API unchanged for backward compatibility
5. Update documentation if needed
```

### Adding CI/CD Enhancement

```
Add code coverage reporting to the GitHub Actions workflow.

Requirements:
- Use JaCoCo for coverage
- Generate coverage reports in CI
- Add coverage badge to README
- Fail build if coverage drops below 80%

Please:
1. Add JaCoCo plugin to Gradle
2. Update GitHub Actions workflow
3. Add coverage badge to README
4. Document the coverage requirement
```

### Container Optimization

```
Optimize the Docker image for smaller size and faster startup.

Please:
1. Use multi-stage builds
2. Use a minimal base image (alpine)
3. Optimize JAR for size (exclude unnecessary dependencies)
4. Measure and document size/startup improvements
5. Update Dockerfile and documentation
```

## Best Practices for Agents

1. **Always start with tests**: Write or update tests before changing implementation code
2. **Make minimal changes**: Only change what's necessary to solve the problem
3. **Maintain backward compatibility**: Don't break existing APIs unless absolutely necessary
4. **Update documentation**: Keep README and comments in sync with code changes
5. **Run all tests**: Ensure no regressions before completing work
6. **Follow project conventions**: Use existing code style and patterns
7. **Think about containers**: Consider the impact on container size and startup time

## Common Commands

```bash
# Build the project
./gradlew build

# Run tests
./gradlew test

# Create JAR
./gradlew bootJar

# Run the CLI
java -jar build/libs/sql-loader.jar --help

# Run with Task
task build
task test
task package
```

## Code Structure

- `SqlLoaderApplication.kt`: Main Spring Boot application
- `SqlLoaderCommand.kt`: Picocli CLI command and argument parsing
- `SqlLoaderService.kt`: Core SQL execution logic
- `SqlLoaderServiceTest.kt`: Integration tests with Testcontainers

## Testing Strategy

- Use Testcontainers for integration tests
- Test with real databases (PostgreSQL)
- Cover happy path and error cases
- Test transaction rollback behavior
- Use descriptive test names

## When to Ask for Help

- If you're unsure about breaking changes
- If performance requirements are unclear
- If you need clarification on requirements
- If tests are failing and you can't determine why
- If you're considering major architectural changes
