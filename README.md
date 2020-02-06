# Technical challenge

The challenge is to create a REST API which stores, updates, retrieves and deletes Person entities.
Problems which are solved in code -
1. Secure API with JWT(Json Web Token) authentication method.
2. Solution can be tested without the need to install it locally.
3. Integrate Swagger which acts as a frontend to test the backend APIs.


### Tech Stack
The tech stack I choose are as follow:
* Java 8
* Library
  * Lombok
  * Model Mapper
  * H2 InMemory Database
  * Spring Security
  * jjwt (JWT Token Library)
  * Apache Common codec (Util Library)
  * Swagger
  * Dockers
* Spring-boot (Application)


### Build the Application

You can build a single executable JAR file that contains all the necessary dependencies, classes, and resources, and run that.
The JAR file will be create by executing the command `mvn clean package`, and the JAR file will be located under target directory.
D:\embl-backend>mvn clean package
[INFO] Scanning for projects...
[INFO]
[INFO] -----------------------< com.embl:embl-backend >------------------------
[INFO] Building embl-backend 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- maven-clean-plugin:3.1.0:clean (default-clean) @ embl-backend ---
[INFO] Deleting D:\embl-backend\target
[INFO]
[INFO] --- jacoco-maven-plugin:0.7.9:prepare-agent (default) @ embl-backend ---
[INFO] argLine set to -javaagent:C:\\Users\\harshvardhan\\.m2\\repository\\org\\jacoco\\org.jacoco.agent\\0.7.9\\org.jacoco.agent-0.7.9-runtime.jar=destfile=D:\\embl-backend\\target\\jacoco.exec
[INFO]
[INFO] --- maven-resources-plugin:3.1.0:resources (default-resources) @ embl-backend ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 1 resource
[INFO] Copying 0 resource
[INFO]
[INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ embl-backend ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 30 source files to D:\embl-backend\target\classes

### Test the Application

You can run all the test cases written by executing the below command
```mvn test```
Java Code Coverage plugin is also added which generate the code coverage percentage. Generated file will be located under embl-backend/target/site/jacoco/index.html

### Deploy the Application
You can create a docker image by executing following command -
```
docker build -t embl-backend .
```
This command will create a docker image. Once the docker image is created we can push it into docker hub and let kubernetes
manage to create deployment, replicas and services. Based upon the load on the application we can increase/decrease the pods.

```
Note: Though, above process is optional as I want to explain we can create docker image and use it in kubernetes environment.
```

### Run the Application

You can run the backend application by executing the following command:
```
mvn spring-boot:run
```
OR by executing
```
java -jar target/embl-backend-0.0.1-SNAPSHOT.jar
```
OR by executing below docker run command
```
docker run -d -p 8080:8080 --name embl-backend embl-backend:latest
```
This will start an instance running on the default port `8080`.

* A Data Loader is written at the application start up which create two users with role ROLE_ADMIN and ROLE_USER.

After successfully running the application, you can open your browser with the following urls:
1.  ```
    http://localhost:8080/h2-console
    ```
     Login with username -  "sa" and password - "password" and after login three tables are created PERSON, USER and ROLES
     * PERSON  table contains related to person entity.
     * USER and ROLES table contains user and assigned roles.

2.  ```
    http://localhost:8080/swagger-ui.html
    ```
    Mentioned swagger link contains all the endpoints you can add request parameters and get the result.

3.  ```
    GENERATE TOKEN AP (POST -> /api/v1/generate-token)
    ```

      * Click on the `Generate Token API` and select the `POST /api/v1/generate-token` api and click on the `example value` and then click on the 'Try it out' button. The below response will be displayed.

        ```
        {
          "username": "admin",
          "token": <jwt-token>
        }
        ```
      * Copy the token and click on the Authorize button at upper right corner and add value as `Bearer <jwt-token>` and then click on 'Authorize' button. This step add the jwt token in the subsequent header.
        ```
         Note : The validity of jwt token is defined for 30 minutes. After 30 minutes, token will be expired. In code generation symmetric HSA26 algorithm is used
         to sign token.
        ```
4.  ```
    Create Person Entity (POST -> /api/v1/person)
    ```
      * Click on the `Person Api` and select the `POST /api/v1/person` api and click on the `example value` and then click on the 'Try it out' button. The below response will be displayed.
        ```
          {
            "id": 1,
            "first_name": "John",
            "last_name": "Smith",
            "age": 33,
            "favourite_colour": "red",
            "hobby": [
              "string"
            ]
          }
        ```
        Possible Http codes will be -

          Code                    | Description
          ------------------------|------------------------------------
          401                     | Authentication is required to access the resource
          403                     | You don't have required permission to access the resource
          404                     | The resource not found
          201                     | Created


5.   ```
        Retrieve all persons (GET -> /api/v1/person)
     ```
      * Click on the `Person Api` and select the `GET /api/v1/person` api and click on the `example value` and then click on the 'Try it out' button. The below response will be displayed.
        ```
          {
            "id": 1,
            "first_name": "John",
            "last_name": "Smith",
            "age": 33,
            "favourite_colour": "red",
            "hobby": [
                "string"
                    ]
          }
         ```
        Possible Http codes will be -

          Code                   |    Description
          -----------------------|------------------------------------
          401                    |    Authentication is required to access the resource
          403                    |    You don't have required permission to access the resource
          404                    |    The resource not found

6.    ```
        Retrieve  persons by Id (GET -> /api/v1/person/{id})
      ```
      * Click on the `Person Api` and select the `GET /api/v1/person/{id}` api and pass the person unique Id and click on the `example value` and then click on the 'Try it out' button. The below response will be displayed.
        ```
           {
              "id": 1,
             "first_name": "John",
             "last_name": "Smith",
             "age": 33,
             "favourite_colour": "red",
             "hobby": [
                  "string"
                     ]
           }
        ```
        Possible Http codes will be -

          Code                   |    Description
          -----------------------|------------------------------------
          401                    |    Authentication is required to access the resource
          403                    |    You don't have required permission to access the resource
          404                    |    The resource not found

7.    ```
        Update Person By Id (PUT -> /api/v1/person/{id})
      ```
      * Click on the `Person Api` and select the `PUT /api/v1/person/{id}` api and click on the `example value` and then click on the 'Try it out' button. The below response will be displayed.
         ```
                  {
                    "id": 1,
                    "first_name": "John",
                    "last_name": "Smith",
                    "age": 33,
                    "favourite_colour": "red",
                    "hobby": [
                      "string"
                    ]
                  }
         ```
         Possible Http codes will be -

          Codes                   |    Description
          ------------------------|------------------------------------
          401                     |    Authentication is required to access the resource
          403                     |    You don't have required permission to access the resource
          404                     |    The resource not found
          201                     |    Created

8.    ```
      Delete Person By Id (DELETE -> /api/v1/person/{id})
      ```
      * Click on the `Person Api` and select the `DELETE /api/v1/person/{id}` api and pass the person Id and then click on the 'Try it out' button. Http resposne `No Content(204)` response will be displayed.

         Possible Http codes will be -

          Codes                   |    Description
          ------------------------|------------------------------------
          401                     |    Authentication is required to access the resource
          403                     |    You don't have required permission to access the resource



