FROM openjdk:17-alpine
RUN apk add --no-cache curl

WORKDIR /app

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]