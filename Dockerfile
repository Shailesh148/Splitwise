FROM openjdk:14-alpine
COPY /build/libs/splitwise-0.0.1-SNAPSHOT.jar splitwise-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","splitwise-0.0.1-SNAPSHOT.jar"]