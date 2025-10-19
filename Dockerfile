FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/crudapi-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

# mvn clean package
# docker build -t crudapi .
# docker run -p 8080:8080 crudapi