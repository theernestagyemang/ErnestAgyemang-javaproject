FROM openjdk:17-jdk-alpine
MAINTAINER baeldung.com
COPY target/docker-message-server-1.0.0.jar message-server-1.0.0.jar
ENTRYPOINT ["java","-jar","/message-server-1.0.0.jar"]
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]