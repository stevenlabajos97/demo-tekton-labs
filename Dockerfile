# ===== Etapa 1: Build =====
FROM maven:3.9.12-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copiamos solo pom.xml primero para cachear dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos el código fuente
COPY src ./src

# Construimos el JAR sin tests
RUN mvn clean package -DskipTests -B

# ===== Etapa 2: Imagen final =====
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copiamos solo el JAR generado
COPY --from=build /app/target/*.jar app.jar

# Puerto que exponemos
EXPOSE 8080

# Ejecutamos la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]