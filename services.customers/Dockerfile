
### BUILD image
FROM maven:3-jdk-8 as build
# create app folder for sources
RUN mkdir -p /app
WORKDIR /app
COPY pom.xml /app
#Download all required dependencies into one layer
RUN mvn -B dependency:resolve dependency:resolve-plugins
#Copy source code
COPY src /app/src
# Build application
RUN mvn package
ENTRYPOINT ["java","-jar","/app/target/services.customers-0.0.1-SNAPSHOT.jar"]
