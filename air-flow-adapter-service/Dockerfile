FROM gradle:8.9.0-jdk21 AS build
ARG GITLAB_TOKEN_READ_CI
MAINTAINER Alexander <BigTows> Chapchuk
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project

RUN gradle clean air-flow-adapter-service:build -x air-flow-adapter-service:test

FROM amazoncorretto:21.0.4-al2

EXPOSE 8010

RUN mkdir /app
COPY --from=build /home/gradle/project/air-flow-adapter-service/build/libs/*.jar /app/application.jar
CMD ["java", "-jar","/app/application.jar"]