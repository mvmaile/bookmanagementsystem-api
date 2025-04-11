# Stage 1: Build the application with Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set the working directory
WORKDIR /app

# Copy pom.xml and download dependencies (cache step)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the entire project and build the application
COPY . .
RUN mvn clean install -DskipTests

# Stage 2: Create the final image with JRE
FROM eclipse-temurin:21-jre-alpine

# Set working directory
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Start the application
ENTRYPOINT ["java", "-Dspring.profiles.active=server", "-jar", "/app/app.jar"]

