# Build stage
FROM openjdk:17-jdk-bullseye as builder
WORKDIR /workspace
COPY build.gradle settings.gradle* ./
COPY gradlew ./gradlew
COPY gradle ./gradle
RUN ./gradlew dependencies --no-daemon
COPY src ./src
RUN ./gradlew build --no-daemon -x test

# Package stage
FROM openjdk:17-jdk-slim
COPY --from=builder /workspace/build/libs/app.jar app.jar
ENV SPRING_PROFILES_ACTIVE=live

# Create and use a non-root user for security
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring

ENTRYPOINT ["java","-jar","/app.jar"]