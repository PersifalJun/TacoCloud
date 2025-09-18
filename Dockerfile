# syntax=docker/dockerfile:1.7
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Кеш зависимостей Maven для быстрых сборок
COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -e -DskipTests -ntp dependency:go-offline

# Сборка приложения
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -DskipTests -ntp package

# Рантайм-слой
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
