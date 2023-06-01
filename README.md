# Pokelab Back-End

# Developed by:
- Gabriel Nieves
- Andrés Pico

**Table of Contents**
- [Tools and libraries](#tools)
- [Instructions to run the application](#instructions)
- [Unit tests: indicate the code coverage achieved](#test)

## Tools
<img src="img/javaImg.png" width="100" height="100"/><img src="img/spring-boot-logo.png" width="150" height="100"/>

<img src="img/mavenImg.png" width="170" height="100"/><img src="img/postgresImg.png" width="150" height="150"/>

- JPA
- Lombok
- Swagger
- Hibernate


## Instructions.

Make sure to have installed the following before continuing:
- JDK 11 
- <a href="https://www.postgresql.org/download/">PostgreSQL<a> (including pgAdmin)
- Install Maven
- PostgreSQL installed

### First Step

Create a database on PostgreSQL named pokemon  
![](/img/creatingDatabase.png)

Then in this database create a Schema called local

![](/img/creatingSchema.png)

### Second step
On the cloned repository use the command in the terminal.

```bash
mvn clean install
```

Then go to the class pokemonChallengueApplication.java and run the application

![](/img/spring-boot-aplication.png)

You should see that the application has created all the tables and the relations with each other

![](/img/created_tables.png)
### Third step

Downloading and importing Insomnia file 

![](/img/insomnia_import.png)

When exporting go to the corresponding collection file, and try the end points

![](/img/imported_file.png)


## Test

### Unit test coverage achieved: 78%

![](/img/testing.png)

## Link for SwaggerAPI

http://localhost:8080/swagger-ui.html



