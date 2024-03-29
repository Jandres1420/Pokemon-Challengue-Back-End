# Pokelab Back-End

# Developed by:
- [Gabriel Cosson](https://github.com/gabrielnievescosson)
- [Andrés Pico](https://github.com/Jandres1420)

**Table of Contents**
- [Tools and libraries](#tools)
- [Instructions to run the application](#instructions)
- [Unit tests: indicate the code coverage achieved](#test)

## Technologies.
- Java
- SpringBoot
- Maven
- PostgreSQL
- JPA
- Lombok
- Hibernate

## Instructions.

Make sure to have installed the following before continuing:
- <a href="https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html">JDK 11<a>
- <a href="https://www.postgresql.org/download/">PostgreSQL 15<a> (including pgAdmin)
- <a href="https://maven.apache.org/download.cgi">Maven<a> and add the respective environment variables

### First Step
Clone the <a href="https://github.com/Jandres1420/Pokemon-Challengue-Back-End">Back-End Repository<a>

### Second Step
Open pgAdmin, set <i>admin</i> as the default password and create a database on PostgreSQL named <i>pokemon</i>
![](/img/creatingDatabase.png)

### Third Step
Then, inside of <i>Schemas</i> create a new Schema with the name <i>local</i>

![](/img/creatingSchema.png)

### Third step
Open the cloned repository on IntelliJ, set the previous installed SDK on the program and then write the following command on the terminal:

```bash
mvn clean install
```

  ### Fourth Step
Find the pokemonChallengueApplication.java file and run the application

![](/img/spring-boot-aplication.png)

You should see that the application has created all the tables and the relations with each other
![](/img/created_tables.png)
