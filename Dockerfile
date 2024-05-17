FROM openjdk:17

WORKDIR /app

COPY pom.xml .
RUN mvn package

COPY target/*.jar .

EXPOSE 8085

CMD ["java", "-jar", "spring-boot-app.jar"]