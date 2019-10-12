# Dna Detect Api
DNA API for comparison between Simians or Humans

## Stack
- Java 8
- Maven 3.6.1
- Docker
- JUnit
- Mockito
- RDS aws

## Recomendado
- STS / Eclipse
- Maven
- Docker
 
## Executando o projeto

 - In the project folder build the dockerimage:
    - ` sudo docker build -t ftiburcio/dna -f dna-api.dockerfile .`
 - Run dockerimage:
 	- ` sudo docker run -p 8080:8080 ftiburcio/dna`

## Documentation
 - local: http://localhost:8080/swagger-ui.html
 - Prod:  https://dna-detect-api.herokuapp.com/api/swagger-ui.html