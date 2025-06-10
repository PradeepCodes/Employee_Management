# Stage 1: Build the application using Maven
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app

# Copy your source code to the image
COPY . .

# Package the application, skipping tests for faster builds
RUN mvn clean package -DskipTests

# Stage 2: Run the application using a lightweight Java image
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy only the packaged jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080 for the application
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
