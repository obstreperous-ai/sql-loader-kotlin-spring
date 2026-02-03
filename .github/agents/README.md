# Custom Agents for SQL Loader Kotlin Spring

This directory contains custom agent profiles for GitHub Copilot to provide specialized expertise for different aspects of this project.

## Available Custom Agents

### 1. Kotlin Expert (`kotlin-expert.md`)
**When to use:** For writing and refactoring Kotlin code
- Idiomatic Kotlin patterns and features
- Null safety and type system
- Functional programming in Kotlin
- Code quality and best practices

### 2. Spring Boot Expert (`spring-boot-expert.md`)
**When to use:** For Spring Boot application architecture and configuration
- Dependency injection and IoC
- CommandLineRunner and CLI patterns
- Configuration management
- Application lifecycle
- Container optimization for Spring Boot

### 3. Testing Expert (`testing-expert.md`)
**When to use:** For writing and improving tests
- Test-first development (TDD)
- JUnit 5 and testing frameworks
- Unit and integration testing
- Mocking and test data
- Test organization and best practices

### 4. Gradle Expert (`gradle-expert.md`)
**When to use:** For build configuration and dependency management
- Gradle Kotlin DSL
- Dependency management
- Build optimization
- Plugin configuration
- JAR packaging

### 5. SQL and JDBC Expert (`sql-jdbc-expert.md`)
**When to use:** For database operations and SQL script handling
- JDBC programming
- SQL script execution
- Connection management
- Transaction handling
- SQL injection prevention

### 6. Container Optimization Expert (`container-optimization-expert.md`)
**When to use:** For Docker and Kubernetes deployment
- Minimal container images
- Multi-stage builds
- JVM tuning for containers
- Kubernetes configurations
- Startup time and memory optimization

## How Custom Agents Work

Custom agents are specialized AI assistants with:
- **Focused expertise** in a specific domain
- **Tailored guidelines** for that domain
- **Consistent behavior** across development sessions
- **Domain-specific best practices** built in

## Usage Tips

1. **Choose the right agent** for your task - use the "When to use" guide above
2. **Combine agents** when tasks span multiple domains (e.g., Kotlin + Spring Boot)
3. **Trust the experts** - these agents have deep domain knowledge
4. **Follow their guidelines** - they enforce project standards and best practices

## Integration with Project

These custom agents complement the project's `copilot-instructions.md` by providing deeper expertise in specific areas. The instructions file sets overall project guidelines, while custom agents provide specialized knowledge for focused tasks.
