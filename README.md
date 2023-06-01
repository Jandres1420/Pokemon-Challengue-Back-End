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
- <a href="https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html">JDK 11<a>
- <a href="https://www.postgresql.org/download/">PostgreSQL<a> (including pgAdmin)
- <a href="https://maven.apache.org/download.cgi">Maven<a> and add the respective environment variables

### First Step
Clone the <a href="https://github.com/Jandres1420/Pokemon-Challengue-Back-End">Back-End Repository<a>

### Second Step
Open pgAdmin, set <i>admin</i> as the default password and create a database on PostgreSQL named <i>pokemon</i>
![](/img/creatingDatabase.png)

Then in this database create a Schema called local

![](/img/creatingSchema.png)

### Third step
Openb the cloned repository on IntelliJ and write the following command on the terminal:

```bash
mvn clean install
```

Then go to the class pokemonChallengueApplication.java and run the application

![](/img/spring-boot-aplication.png)

You should see that the application has created all the tables and the relations with each other
![](/img/created_tables.png)
  
### Fourth step

Downloading and importing Insomnia file 

![](/img/insomnia_import.png)

When exporting go to the corresponding collection file, and try the end points

![](/img/imported_file.png)


## Test

### Unit test coverage achieved: 78%

![](/img/testing.png)

## Link for SwaggerAPI

http://localhost:8080/swagger-ui.html



