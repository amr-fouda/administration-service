# Administration service
Spring boot CRUD project

## Code review
* **Clean code** using maven plugins (PMD & findbugs) and Sonarqube code analysis
* **Unit testing** using Junit and Mockito
* **Integration testing** using MockMvc
* **Test coverage** Fail build if less than 75%

## Run prerequisite
* JDK 8
* Maven 3+

## How to run locally?
* **Build the project** mvn clean install 
* **Run the project** mvn spring-boot:run
* **Run the end point using** 
    * **Postman**
    * **Swagger UI** http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
