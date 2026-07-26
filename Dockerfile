FROM maven:3-eclipse-temurin-21 as build
WORKDIR /workspace

COPY src ./src
COPY pom.xml ./
RUN mvn clean package -DskipTests -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true -Dmaven.resolver.transport=wagon

FROM eclipse-temurin:21 as runtime
WORKDIR /app
COPY --from=build /workspace/target/*.jar /app/*.jar
EXPOSE 49175
ENV SPRING_PROFILES_ACTIVE=postgres-docker
ENTRYPOINT ["java", "-jar", "/app/*.jar"]

# Old
#FROM eclipse-temurin:21 as runtime
#WORKDIR /app
#COPY ./target/*.jar /app/*.jar
#EXPOSE 49175
#ENV SPRING_PROFILES_ACTIVE=postgres-docker
#ENTRYPOINT ["java", "-jar", "/app/*.jar"]