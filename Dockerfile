FROM openjdk:21-jdk-slim
VOLUME /app
COPY build/libs/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=live
ENTRYPOINT ["java","-jar","/app.jar"]