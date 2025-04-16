FROM openjdk:21-jdk-slim
COPY target/*.jar app.jar
EXPOSE 5001
ENTRYPOINT ["java","-jar","/app.jar"]