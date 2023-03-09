## Build image
FROM maven:3.9.0-amazoncorretto-19 AS maven
WORKDIR /tmp/build
COPY ./pom.xml .
RUN mvn dependency:go-offline
COPY . .
RUN mvn package -DskipTests

  ## run image
FROM amazoncorretto:19-alpine
WORKDIR /takima-store
COPY --from=maven /tmp/build/target/*.jar /takima-store/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
