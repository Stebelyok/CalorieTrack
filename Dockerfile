FROM openjdk:17-jdk-slim as build
RUN apt-get update && apt-get install -y maven
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean install

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/CalorieTrack-0.0.1-SNAPSHOT.jar /app/calorietrack.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/calorietrack.jar"]