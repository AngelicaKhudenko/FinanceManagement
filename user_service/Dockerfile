FROM eclipse-temurin:17-jre-focal
MAINTAINER ITAcademy

ADD ./target/user_service-1.0.0.jar /app/

CMD ["java", "-Xmx200m", "-jar", "/app/user_service-1.0.0.jar"]

EXPOSE 8080
