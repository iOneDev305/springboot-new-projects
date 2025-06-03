FROM openjdk:17-jdk-slim AS build
WORKDIR /app
COPY app/gradlew .
COPY app/gradle ./gradle
COPY app/build.gradle .
COPY app/settings.gradle .
COPY app/src ./src
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar --no-daemon -x test

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=dev
ENV JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8080/actuator/health || exit 1
ENTRYPOINT ["java", "-jar", "app.jar"] 