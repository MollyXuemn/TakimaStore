## Build image
FROM maven:3.6.1-jdk-13 AS takima-store-build
ENV TAKIMA_HOME /opt/takima-store
WORKDIR $TAKIMA_HOME
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests
  
  ## run image
FROM openjdk:12.0.2-oracle
ENV TAKIMA_HOME /opt/takima-store
ENV DB_HOST=db
ENV DB_PORT=5432
WORKDIR $TAKIMA_HOME
  
  # Download wait-for-it.sh
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh wait-for-it.sh
RUN chmod +x $TAKIMA_HOME/wait-for-it.sh
  
  # Copy artifact from build-image
COPY --from=takima-store-build $TAKIMA_HOME/target/*.jar $TAKIMA_HOME/takima-store.jar

ENTRYPOINT bash ./wait-for-it.sh ${DB_HOST}:$DB_PORT -- java -jar ma-store.jar

