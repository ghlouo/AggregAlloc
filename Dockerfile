FROM maven:3.6.1-jdk-8-alpine

COPY . /opt/app

WORKDIR /opt/app
CMD mvn spring-boot:run
