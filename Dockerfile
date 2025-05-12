# Use the official OpenJDK 21 base image
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /resources
COPY . .
RUN mvn package -DskipTests


FROM openjdk:17-jdk-slim
# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY --from=builder /resources/target/demo-0.0.1-SNAPSHOT.jar app.jar

COPY src/main/resources/prod_application.properties application.properties
# Expose the port on which the application will run
EXPOSE 8080

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
