FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml and download dependencies (cached layer)
COPY hotels-ms/pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build
COPY hotels-ms/src ./src
RUN mvn clean package -DskipTests -B

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Use exec form for proper signal handling
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
