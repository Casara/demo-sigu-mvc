FROM openjdk:15-jdk-alpine
MAINTAINER rodrigocasara@gmail.com
VOLUME /opt
EXPOSE 8080
ARG JAR_FILE=build/libs/sigu-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} sigu.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/sigu.jar"]
