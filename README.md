# Technical challenge

The challenge is to create a REST API which stores, updates, retrieves and deletes Person entities.
Problems which are solved in code -
1. Secure API with JWT(Json Web Token) authentication method.
2. Solution can be tested without the need to install it locally.
3. Integrate Swagger which acts as a frontend to test the backend APIs.

## Assumption
I work on the assumption that favourite_color and hobby field are optional and rest of the input fields are mandatory.

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
```
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
```

### Test the Application

You can run all the test cases(Unit + Integration) written by executing the below command
```mvn test```
Java Code Coverage plugin is also added which generate the code coverage percentage. Generated file will be located under embl-backend/target/site/jacoco/index.html
```
D:\embl-backend>mvn test
[INFO] Scanning for projects...
[INFO]
[INFO] -----------------------< com.embl:embl-backend >------------------------
[INFO] Building embl-backend 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
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
[INFO] Nothing to compile - all classes are up to date
[INFO]
[INFO] --- maven-resources-plugin:3.1.0:testResources (default-testResources) @ embl-backend ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory D:\embl-backend\src\test\resources
[INFO]
[INFO] --- maven-compiler-plugin:3.8.1:testCompile (default-testCompile) @ embl-backend ---
[INFO] Nothing to compile - all classes are up to date
[INFO]
[INFO] --- maven-surefire-plugin:2.22.2:test (default-test) @ embl-backend ---
[INFO]
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.embl.person.dto.AuthTokenTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.045 s - in com.embl.person.dto.AuthTokenTest
[INFO] Running com.embl.person.dto.PersonDtoTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.003 s - in com.embl.person.dto.PersonDtoTest
[INFO] Running com.embl.person.dto.UserDtoTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.002 s - in com.embl.person.dto.UserDtoTest
[INFO] Running com.embl.person.util.UrlKeysTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.005 s - in com.embl.person.util.UrlKeysTest
```
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

```
D:\embl-backend>docker build -t embl-backend .
Sending build context to Docker daemon  52.84MB
Step 1/4 : FROM openjdk:8-jre-alpine
 ---> f7a292bbb70c
Step 2/4 : VOLUME /tmp
 ---> Using cache
 ---> 3268177d573e
Step 3/4 : ADD target/embl-backend-0.0.1-SNAPSHOT.jar app.jar
 ---> 94114ceb0d30
Step 4/4 : ENTRYPOINT ["java", "-Xmx512m", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
 ---> Running in 02679e8def34
Removing intermediate container 02679e8def34
 ---> f62979ba2f78
Successfully built f62979ba2f78
Successfully tagged embl-backend:latest
SECURITY WARNING: You are building a Docker image from Windows against a non-Windows Docker host. All files and directories added to build context will have '-rwxr-xr-x' permissions. It is recommended to double check and reset permissions for sensitive files and directories.
```



### Run the Application

You can run the backend application by executing the following command:
```
mvn spring-boot:run
```
```
D:\embl-backend>mvn spring-boot:run

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.2.4.RELEASE)

2020-02-06 11:25:56.224  INFO 27632 --- [           main] com.embl.person.Application              : Starting Application on Harsh301301 with PID 27632 (D:\embl-backend\target\classes started by harshvardhan in D:\embl-backend)
2020-02-06 11:25:56.226 DEBUG 27632 --- [           main] com.embl.person.Application              : Running with Spring Boot v2.2.4.RELEASE, Spring v5.2.3.RELEASE
2020-02-06 11:25:56.226  INFO 27632 --- [           main] com.embl.person.Application              : No active profile set, falling back to default profiles: default
2020-02-06 11:25:56.842  INFO 27632 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2020-02-06 11:25:56.894  INFO 27632 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 46ms. Found 2 JPA repository interfaces.
2020-02-06 11:25:57.112  INFO 27632 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration' of type [org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2020-02-06 11:25:57.629  INFO 27632 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
```
OR by executing
```
java -jar target/embl-backend-0.0.1-SNAPSHOT.jar
```
```
D:\embl-backend\target>java -jar embl-backend-0.0.1-SNAPSHOT.jar

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.2.4.RELEASE)

2020-02-06 11:26:59.355  INFO 20536 --- [           main] com.embl.person.Application              : Starting Application v0.0.1-SNAPSHOT on Harsh301301 with PID 20536 (D:\embl-backend\target\embl-backend-0.0.1-SNAPSHOT.jar started by harshvardhan in D:\embl-backend\target)
2020-02-06 11:26:59.358 DEBUG 20536 --- [           main] com.embl.person.Application              : Running with Spring Boot v2.2.4.RELEASE, Spring v5.2.3.RELEASE
2020-02-06 11:26:59.359  INFO 20536 --- [           main] com.embl.person.Application              : No active profile set, falling back to default profiles: default
2020-02-06 11:27:00.215  INFO 20536 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2020-02-06 11:27:00.297  INFO 20536 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 70ms. Found 2 JPA repository interfaces.
2020-02-06 11:27:00.740  INFO 20536 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration' of type [org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2020-02-06 11:27:01.394  INFO 20536 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
```
OR by executing below docker run command
```
docker run -d -p 8080:8080 --name embl-backend embl-backend:latest
```
```
D:\embl-backend>docker run -p 8080:8080 --name embl-backend embl-backend:latest

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.2.4.RELEASE)

2020-02-06 05:58:24.444  INFO 1 --- [           main] com.embl.person.Application              : Starting Application v0.0.1-SNAPSHOT on 9864f0fbed62 with PID 1 (/app.jar started by root in /)
2020-02-06 05:58:24.447 DEBUG 1 --- [           main] com.embl.person.Application              : Running with Spring Boot v2.2.4.RELEASE, Spring v5.2.3.RELEASE
2020-02-06 05:58:24.448  INFO 1 --- [           main] com.embl.person.Application              : No active profile set, falling back to default profiles: default
2020-02-06 05:58:25.721  INFO 1 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2020-02-06 05:58:25.853  INFO 1 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 113ms. Found 2 JPA repository interfaces.
2020-02-06 05:58:26.410  INFO 1 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration' of type [org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2020-02-06 05:58:26.756  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
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
          200                    |    OK

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
           200                   |    OK

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
          200                     |    OK

8.    ```
      Delete Person By Id (DELETE -> /api/v1/person/{id})
      ```
      * Click on the `Person Api` and select the `DELETE /api/v1/person/{id}` api and pass the person Id and then click on the 'Try it out' button. Http resposne `No Content(204)` response will be displayed.

         Possible Http codes will be -

          Codes                   |    Description
          ------------------------|------------------------------------
          401                     |    Authentication is required to access the resource
          403                     |    You don't have required permission to access the resource



