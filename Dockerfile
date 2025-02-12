# Use an official Kotlin image from the Docker Hub as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
RUN mkdir /app


# Copy the JAR file from the builder image
COPY --from=builder /app/build/libs/meetApp-ktor-backend-0.0.1.jar /app/meetApp-ktor-backend.jar

# Expose the port the application will run on
EXPOSE 8080

# Run the JAR file using Java
CMD ["java", "-jar", "meetApp-ktor-backend.jar"]
