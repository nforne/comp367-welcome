# Use an OpenJDK base image
FROM eclipse-temurin:17-jdk-jammy

# Set working dir
WORKDIR /app

# Copy the built jar from the Maven build
COPY target/*.jar app.jar

# Expose port used by your webapp (adjust if different)
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
