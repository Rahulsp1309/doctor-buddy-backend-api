FROM openjdk:20

COPY target/doc_buddy_service.jar /usr/app/

WORKDIR /usr/app

ENTRYPOINT ["java","-jar","doc_buddy_service.jar"]