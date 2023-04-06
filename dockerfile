FROM openjdk:17-jdk-slim-buster

RUN apt-get update && apt-get install -y curl gzip tar

WORKDIR /app

COPY build/libs/CustomLambdaRuntime.jar .
RUN chmod 755 CustomLambdaRuntime.jar

ENTRYPOINT ["java", "-jar", "CustomLambdaRuntime.jar"]