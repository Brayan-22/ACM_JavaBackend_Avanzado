FROM eclipse-temurin:21-jre

WORKDIR /app

COPY order-orchestrator/target/order-orchestrator.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]