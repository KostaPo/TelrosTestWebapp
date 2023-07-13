FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/*.jar
WORKDIR /etc/java
EXPOSE 8080/tcp
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]