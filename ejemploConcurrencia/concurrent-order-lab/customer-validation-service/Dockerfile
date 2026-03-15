FROM eclipse-temurin:21-jre

WORKDIR /app

COPY customer-validation-service/target/customer-validation-service.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]