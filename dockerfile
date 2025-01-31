# Use official Maven image to build the application
FROM maven:3.8.4-openjdk-17-slim AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and dependencies to download them first (to leverage Docker cache)
COPY pom.xml .

# Download dependencies (without copying the entire source code to avoid unnecessary rebuilds)
RUN mvn dependency:go-offline

# Copy the source code into the container
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use a minimal Java 17 runtime image to run the application
FROM openjdk:17-slim

# Set the working directory for the application
WORKDIR /app

# Copy the jar file from the builder image
COPY --from=builder /app/target/userrole-0.0.1-SNAPSHOT.jar /app/userrole.jar

# Expose the port your app will be running on
EXPOSE 8084

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/userrole.jar"]
