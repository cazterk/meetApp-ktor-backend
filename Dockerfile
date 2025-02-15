# Stage 1: Cache Gradle dependencies
FROM gradle:latest AS cache
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME=/home/gradle/cache_home
COPY build.gradle.* gradle.properties /home/gradle/app/
COPY gradle /home/gradle/app/gradle
WORKDIR /home/gradle/app
RUN gradle clean build -i --stacktrace

# Stage 2: Build Application
FROM gradle:latest AS build
COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle
COPY . /usr/src/app/
WORKDIR /usr/src/app
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# Build the fat JAR, Gradle also supports shadow
# and boot JAR by default.
RUN gradle buildFatJar --no-daemon

# Declare build arguments
ARG ktor_version
ARG kotlin_version
ARG logback_version
ARG kmongo_version
ARG commons_codec_version

# Set environment variables for the build process
ENV KTOR_VERSION=$ktor_version
ENV KOTLIN_VERSION=$kotlin_version
ENV LOGBACK_VERSION=$logback_version
ENV KMONGO_VERSION=$kmongo_version
ENV COMMONS_CODEC_VERSION=$commons_codec_version

# Set environment variables inside Dockerfile
ENV JWT_SECRET="somekey"
ENV MONGO_PASSWORD="somekey"

# Stage 3: Create the Runtime Image
FROM amazoncorretto:21 AS runtime
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/meetApp-ktor-backend.jar
# Command to run the application when the container starts
ENTRYPOINT ["java", "-jar", "/app/meetApp-ktor-backend.jar"]
