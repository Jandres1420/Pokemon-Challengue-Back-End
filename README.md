# Pokemon back end challengue

# Develop by:
- Gabriel Nieves
- Andrés Pico

**Table of Contents**
- [Tools and libraries](#tools)
- [Instructions to run the application](#instructions)
- [Unit tests: indicate the code coverage achieved](#test)

## Tools

![](/img/javaImg.png=250x250)
![](/img/spring-boot-logo.png=250x250)
![](/img/mavenImg.png=250x250)
![](/img/postgresImg.png=250x250)
- JPA
- Lombok
- Swagger
- Hibernate

### waiting for response (Libraries)


## Instructions.

- Use Jdk 11 
- Have installed maven
- PostgreSQL installed

### First Step

Create a database on postSQL named pokemon  
![](/img/creatingDatabase.png)

Then in this database create a Schema called local

![](/img/creatingSchema.png)

### Second step
Use on the cloned repository the command in the terminal.

```bash
mvn clean install
```

Then go to the class pokemonChallengueApplication.java and run the application

![](/img/spring-boot-aplication.png)

Yo should see that the application has created all the tables and their relations

![](/img/created_tables.png)
### Third step

Downloading and importing insomnia file 

![](/img/insomnia_import.png)

When exporting go to the corresponding collection file, and try the end points

![](/img/imported_file.png)


## Test

![](/img/testing.png)


