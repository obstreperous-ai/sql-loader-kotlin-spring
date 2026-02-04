FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="obstreperous-ai"
LABEL description="Lean SQL loader CLI utility for Kubernetes"

# Create app directory
WORKDIR /app

# Copy the JAR
COPY build/libs/sql-loader.jar /app/sql-loader.jar

# Create a non-root user
RUN addgroup -g 1000 sqlloader && \
    adduser -D -u 1000 -G sqlloader sqlloader && \
    chown -R sqlloader:sqlloader /app

USER sqlloader

ENTRYPOINT ["java", "-jar", "/app/sql-loader.jar"]
CMD ["--help"]
