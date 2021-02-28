FROM openjdk:11

EXPOSE 8084

ADD ./build/libs/*.jar transaction-service.jar

ENTRYPOINT ["java","-jar","/transaction-service.jar"]
