FROM eclipse-temurin:17-jre-alpine AS base

FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /usr/src/my-spring-app

RUN apk update && \
    apk add --no-cache openjdk17 curl bash && \
    curl -fsSL https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz | tar xzf - && \
    rm -rfv apache-maven-3.9.6-bin.tar.gz && \
    ln -sf /usr/src/my-spring-app/apache-maven-3.9.6/bin/mvn /usr/local/bin/mvn

COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .
RUN mvn clean package -DskipTests

FROM base

ARG JAR_FILE=target/*.jar

COPY --from=builder /usr/src/my-spring-app/${JAR_FILE} app.jar

RUN mkdir -p app/logs
RUN touch /app/logs/app.log

EXPOSE 8080

HEALTHCHECK --interval=3s --timeout=5s CMD curl --fail http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java","-jar","/app.jar"]
