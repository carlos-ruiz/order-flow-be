# Use a lightweight OpenJDK image
FROM eclipse-temurin:21-jre-alpine

# Create a non-root user and group
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

# Set the working directory
WORKDIR /app

# Copy the jar file into the container
COPY target/ordersapp-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on (default Spring Boot is 8080)
EXPOSE 8080

RUN chown -R appuser:appgroup /app
USER appuser

# Set the active Spring profile to 'docker' by default
ENV SPRING_PROFILES_ACTIVE=docker

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
