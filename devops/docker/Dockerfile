FROM maven:3.5.3-jdk-8-alpine
WORKDIR /dna-api
COPY . .
RUN mvn -Denv=prod clean install
RUN rm -rf ~/.m2/repository

ENTRYPOINT ["java","-jar","target/dna-0.0.1-SNAPSHOT.jar"]
