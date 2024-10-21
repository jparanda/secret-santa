# Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

# Add Maintainer Info
LABEL maintainer="juan.aranda.galvis@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8090

# The application's jar file
ARG JAR_FILE=target/secret-santa-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} secret-santa.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/secret-santa.jar"]