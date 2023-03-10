# Hibernate: Welcome to Hibernate !
#### Technologies
![java]
![hibernate]

![spring] ![flyway]

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

[java]: https://img.shields.io/badge/java-%E2%98%85%E2%98%85%20%20-yellow.svg?longCache=true&style=for-the-badge&logoColor=ffffff&logo=java "java basics"
[spring]: icons/spring.png
[flyway]: icons/Flyway.png
[hibernate]: https://img.shields.io/badge/Hibernate-%E2%98%85%E2%98%85%20%20-yellow.svg?longCache=true&style=for-the-badge&logoColor=ffffff&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAPCAYAAADUFP50AAAABGdBTUEAALGPC/xhBQAAAAlwSFlzAAALEwAACxMBAJqcGAAAAAd0SU1FB+IMHhQhJqfTRjYAAAAZdEVYdENvbW1lbnQAQ3JlYXRlZCB3aXRoIEdJTVBXgQ4XAAAB+0lEQVQoz12TP2hTURTGf++viTFqRaT+KaWhHYwVo1SqgxAhooNToMJbHBReobxSB4tk0THFqaUZbAZxCwWJTQenClqqoEaRajsoVLAENxXa/M9716F5SV4OnOFe7ved7zvnXIlm3J6YfGxEA9O6JjWEcG9RgblYPHmPrpABDNMKV+ti4u1GBVlCbQLU5puplWxiFGAlm2gBJcO0VCAFjGuKxPURP709CsJbIA9cjsWTjc6KfcBdgFpDsPmrju10CyMCjDXVtTzMuLIkCX4UakSGDnLyWBDRNqsqij798fXC0sXoeNkFevht2+bMyC0G+npxcbIk8X3r5/n5p4smMGeYFjKQAKoAjhBcuhARg6EhfPuP4A/spe4/zFp+g91iadYwrUOZdAoZ2AYWAPZpGteiV9A0zWNwu/Cb9/kv7vEJgJxJp2xgVgixMxgaEOeGw1J3ZzLZnCiWS+7xpmFaowrAt08f/oVOn9UfPrh/NRjwe0Cf1zfF4tKypLdV6EDQw/5m+VHBdpwT7VbC+laJ1a9VFNnDl5MB1l49A6BaK99x7DpuVmt1hvt1Th1VO2e7C8y0KjbXyQe8AG500v/Zcci9K+Lsjed5Jp0aawmIxZPE4skKMA9UOoE9B2RCx1sezdaSd0YsnnwJrHp+ggzhfg2fLk1l0qm/hmnxH4N9rX3CJ4pAAAAAAElFTkSuQmCC "Hibernate basics"
[postgresql]: icons/postgresql.png
