# Hibernate: Welcome to Hibernate !
#### Technologies
![java]
![hibernate]
![spring]
![flyway]

#### Prerequisites
- have **docker** installed
- have a PostgreSQL database up and running
- have at least JDK8 installed on your machine

## SetUp
To get all the dependencies included by your Spring setup:
```
mvn dependency:tree 
```
Compile your project by Maven by clicking the button in IntelliJ or:
```
mvn compile
``` 
To run your database Flyway:
```
docker-compose up
``` 
To test your Code by maven:
```
mvn package -DskipTests
``` 

## GetStart
Start your application `MaStoreApplication`


## Troubleshoot
> :red_circle: error
> 
> Please leave the comments.

[java basics]: icons/Java.png
[spring]: icons/spring.png
[flyway]: icons/Flyway.png
[hibernate]: icons/hibernate-brand.png
[postgresql]: icons/postgresql.png
