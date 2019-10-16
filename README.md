# Dna Detect Api
DNA API para comparação entre Simians e Humanos

## Stack
- Java 8

- Maven 3.6.1

- Docker

- JUnit

- Mockito

- Heroku

## Pré-requisitos
- Java

- Maven

- Docker

## Executando o projeto
### Dev

- Na pasta do projeto faça o build do docker-compose:
`sudo docker-compose -f devops/docker/docker-compose.yml build`

- Rode o docker-compose:
`sudo docker-compose -f devops/docker/docker-compose.yml up`

### Local

- Na pasta do projeto rode o docker-compose do banco de dados:
`sudo docker-compose -f devops/docker/docker-compose-db.yml up -d`

- Builde o projeto:
`mvn -Denv=local install`

- Rode o projeto:
`java -Denv=local -jar target/dna-0.0.1-SNAPSHOT.jar`

## Documentação

- local: http://localhost:8080/swagger-ui.html
- Dev: http://localhost:8080/swagger-ui.html
- Prod: https://dna-detect-api.herokuapp.com/api/swagger-ui.html