FROM openjdk:8-jre-alpine
VOLUME /tmp
ADD target/embl-backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Xmx512m", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
