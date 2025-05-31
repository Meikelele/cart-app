# ---------------------------------
# Etap 1: budowanie (build) za pomocą Mavena
# ---------------------------------
FROM maven:3.8.1-openjdk-11 AS build
WORKDIR /app

# Kopiujemy plik pom.xml i folder src/ do kontenera
COPY pom.xml .
COPY src ./src

# Budujemy JAR (pomijamy testy dla przyspieszenia)
RUN mvn clean package -DskipTests

# ---------------------------------
# Etap 2: uruchomienie (runtime) na JRE
# ---------------------------------
FROM openjdk:11-jre-slim
WORKDIR /app

# Kopiujemy wytworzony JAR z poprzedniego etapu
COPY --from=build /app/target/product-service-0.0.1-SNAPSHOT.jar app.jar

# Mówimy Dockerowi, że w kontenerze ma być port 8080 otwarty
EXPOSE 8080

# Domyślna komenda przy starcie kontenera
ENTRYPOINT ["java", "-jar", "app.jar"]
