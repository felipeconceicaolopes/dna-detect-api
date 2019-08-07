FROM maven:3.5.3-jdk-8-alpine as target
WORKDIR /dna-api
COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .
RUN mvn clean install
RUN rm -rf ~/.m2/repository

ENTRYPOINT ["java","-jar","dna-0.0.1-SNAPSHOT.jar"]