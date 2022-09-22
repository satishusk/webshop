# Webshop
E-commerce site. Analogue of Russian Avito

## Running Webshop locally
Webshop is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven/) or [Gradle](https://spring.io/guides/gs/gradle/). You can build a jar file and run it from the command line (it should work just as well with Java 11 or newer):

```
git clone https://github.com/hexiez/webshop.git
cd webshop
mvnw package
java -jar target/*.jar
```

Or you can run it from Maven directly using the Spring Boot Maven plugin. If you do this it will pick up changes that you make in the project immediately (changes to Java source files require a compile as well - most people use an IDE for this):

```
mvnw spring-boot:run
```

## Endpoint list

| Method | URL                    | Description                  |
|--------|------------------------|------------------------------|
| POST   | /users                 | create user                  |
| GET    | /users                 | get all users                |
| GET    | /users/{id}            | get user with id {id}        |
| PATCH  | /users/{id}/activate   | activate user with id {id}   |
| PATCH  | /users/{id}/deactivate | deactivate user with id {id} |
| PUT    | /users/{id}            | update user with id {id}     |
| DELETE | /users/{id}            | delete user with id {id}     |
| GET    | /roles/{name}          | get role with name {name}    |
| POST   | /roles                 | create role                  |
| DELETE | /roles/{name}          | delete role with name {name} |
| GET    | /products              | get all products             |
| GET    | /products/{id}         | get product with id {id}     |
| POST   | /products              | create product               |
| DELETE | /products/{id}         | delete product with id {id}  |
| GET    | /images/{id}           | get image with id {id}       |


