# Stage 1: Build the application using Maven
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app

# Copy only the inner Employee_Management directory
COPY Employee_Management /app

RUN mvn clean package -DskipTests

# Stage 2: Run the application using lightweight Java image
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
