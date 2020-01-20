FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar planner-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/planner-0.0.1-SNAPSHOT.jar"]