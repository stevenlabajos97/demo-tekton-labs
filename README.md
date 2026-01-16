# Calculadora con Porcentaje Dinámico (Spring Boot)

API REST en **Spring Boot (Java 21)** que suma dos números y aplica un porcentaje dinámico obtenido de un servicio externo.  
Incluye **caché en memoria**, **historial de llamadas en PostgreSQL** y **despliegue en Docker**.

---

## 📦 Requisitos

- Java 21
- Maven
- Docker & Docker Compose
- Postman para pruebas

---

## 🛠️ Tecnologías

- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL (Docker)
- Spring Cache (Caffeine, memoria)
- JUnit + Mockito para tests
- Docker & docker-compose  

## 🏃‍♂️ Ejecutar con Docker

1️⃣ **Clonar el repositorio**

```bash
git clone https://github.com/stevenlabajos97/demo-tekton-labs.git
cd demo-tekton-labs
```

2️⃣ **Levantar PostgreSQL y la API con Docker Compose**

```bash
docker-compose up --build
```

Esto levantará:

PostgreSQL en localhost:5432

API Spring Boot en localhost:8080


## Endpoints

| Método | URL             | Descripción                                                     |
|--------|----------------|-----------------------------------------------------------------|
| GET    | /api/calculate | Recibe `num1` y `num2` (params), devuelve suma + %              |
| GET    | /api/history   | Recibe `page` y `size` (params), devuelve historial de llamadas |

## Collection Postman

[demo.postman_collection](demo.postman_collection)

 ## Pruebas Unitarias

Ejecutar tests:

```bash
./mvnw test
```

##### Cobertura de tests:

PercentageServiceTest → éxito, fallo con cache, fallo sin cache

CalculationServiceTest → suma + porcentaje + registro de historial

## Futuras mejoras

- Migrar caché a Redis para entornos distribuidos

- Paginación y filtros más avanzados en el historial

- Métricas y logging