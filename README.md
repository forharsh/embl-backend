# Technical challenge

The challenge is to create a REST API which stores, updates, retrieves and deletes Person entities.
Problems which are solved in code -
1. Secure API with JWT(Json Web Token) authentication method.
2. Solution can be tested without the need to install it locally.


### Tech Stack
The tech stack I choose to use are list as follow:
* Java 8
* Library
  * Lombok
  * Model Mapper
  * H2 InMemory Database
  * Spring Security
  * jjwt (JWT Token Library)
  * Apache Common codec (Util Library)
* Spring-boot (Application)


### Run the Application

You can package and run the backend application by executing the following command:

```
mvn package spring-boot:run
```

This will start an instance running on the default port `8080`.

You can also build a single executable JAR file that contains all the necessary dependencies, classes, and resources, and run that.
The JAR file will be create by executing the command `mvn package`, and the JAR file will be located under target directory.

Then you can run the JAR file:
```
java -jar target/embl-backend-0.0.1-SNAPSHOT.jar
```
* A Data Loader is written in the application start up which create two users with role ROLE_ADMIN and ROLE_USER.
* Java Code Coverage plugin is also added which display the report code coverage percentage. The report coerage file is present at location embl-backend/target/site/jacoco/index.html


After successfully running the application, you can now open your browser with the following url:
 ```
 http://localhost:8080/h2-console
 ```
 Login with username -  "sa" and password - "password" and after login three tables are created PERSON, USER and ROLES
 * PERSON  table contains related to person entity.
 * USER and ROLES table contains user and assigned roles.

 Using POSTMAN try hit the below url to generate jwt token for user having role admin
 ```
 POST : http://localhost:8080/api/v1/generate-token
 Request Body:
 {
 	"username": "admin",
 	"password": "YWRtaW4="
 }
 password "YWRtaW4=" which is an BASE64 encoded version of "admin"
 ```
 After hitting the url we will receive the jwt token which is a combination of header.payload.signature. Sample response below :
 ```
 {
     "username": "admin",
     "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInNjb3BlcyI6WyJST0xFX0FETUlOIl0sImlzcyI6Ik9DQyIsImlhdCI6MTU4MDY2MDU3MywiZXhwIjoxNTgwNjYyMzczfQ.MVEeovt5s34TpguD2_OD7kOXwTVFDa4HLYwl4BQxTXo"
 }
 ```
 ```
 Note : The validity of jwt token is defined for 30 minutes. After 30 minutes, token will be expired. In code generation symmetric HSA26 algorithm is used
 to sign token.
 ```
 Now we can access the below urls passing the jwt token in request headers.
 * Create Person Entity
 ```
 POST -> localhost:8080/api/v1/person
 Request Headers -> Authorization : Bearer <jwt-token>
 Request Body ->
 {
 	"first_name": "Harsh-2",
 	"last_name": "Vardhan",
 	"age": 29,
 	"favourite_colour": "red",
 	"hobby":["shopping"]
 }
 ```
 * Retrieve all persons
 ```
 GET -> localhost:8080/api/v1/person
 Request Headers -> Authorization : Bearer <jwt-token>
 ```
 * Retrieve  persons by Id
  ```
  GET -> localhost:8080/api/v1/person/1
  Request Headers -> Authorization : Bearer <jwt-token>
  ```
 * Update person by Id
 ```
 PUT -> localhost:8080/api/v1/person/1
 Request Headers -> Authorization : Bearer <jwt-token>
  Request Body ->
  {
  	"first_name": "Harsh-2",
  	"last_name": "Vardhan",
  	"age": 29,
  	"favourite_colour": "red",
  	"hobby":["shopping"]
  }
 ```
 * Delete person by Id
 ```
 DELETE -> localhost:8080/api/v1/person/1
 Request Headers -> Authorization : Bearer <jwt-token>
 ```