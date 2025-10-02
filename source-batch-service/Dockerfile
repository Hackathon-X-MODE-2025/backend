FROM gradle:8.9.0-jdk21 AS build
ARG GITLAB_TOKEN_READ_CI
MAINTAINER Alexander <BigTows> Chapchuk
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project

RUN gradle clean source-batch-service:build -x source-batch-service:test


FROM amazoncorretto:21.0.8-al2023

EXPOSE 8010

RUN mkdir /app

RUN yum install -y --skip-broken https://download.postgresql.org/pub/repos/yum/reporpms/EL-9-x86_64/pgdg-redhat-repo-latest.noarch.rpm

RUN yum install -y postgresql17


COPY --from=build /home/gradle/project/source-batch-service/build/libs/*.jar /app/application.jar
CMD ["java", "-jar","/app/application.jar"]