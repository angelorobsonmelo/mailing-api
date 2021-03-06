[![Build Status](https://travis-ci.org/angelorobsonmelo/mailing-api.svg?branch=master)](https://travis-ci.org/angelorobsonmelo/mailing-api)
## Mailing List
 System for managing surveys, ads within the organization.
 Booby system API with Java and Spring Boot.
 ### RESTful API Details
The RESTful API for Mailing List contains the following features:
* Project created with Spring Boot and Java 8
* Postgresql database with JPA and Spring Data JPA
* Authentication and authorization with Spring Security and JWT (JSON Web Token) tokens
* Database migration with Flyway
* Unit tests and integration with JUnit and Mockito
* Continuous integration with TravisCI
* Swagger documentation
 ### Running the application
Be sure to have Maven installed and added to the PATH of your operating system, just like Git.
 ```
 git clone https://github.com/angelorobsonmelo/mailing-api.git
 cd mailing-api
 mvn spring-boot:run
 Access the endpoints through the http://localhost:8080 url
 ```
 ### Importing the project in Eclipse or STS
 At the terminal, perform the following operation:
 ```
 mvn eclipse:eclipse
 ```
 In Eclipse/STS, import the project as a Maven project.
