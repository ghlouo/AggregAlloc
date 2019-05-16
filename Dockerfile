FROM maven:3.6.1-jdk-8-alpine

# COPY . /opt/app
#run before mvn install   in script in order for cicd be faster (working with ready jar file)
COPY target/allocation-0.0.1-SNAPSHOT.jar /opt/app/app.jar

WORKDIR /opt/app


#CMD mvn spring-boot:run
CMD java -jar -Dspring.profiles.active=dev app.jar
#docker build -t allocation:1 .

#docker run -p 8090:8090 --name allocation2.1.1 --network=bridge allocation:1

#mysql.server start
#ekscli create-cluster
