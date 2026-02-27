=========================================================
      SISTEMA DE MICROSERVICIOS BANCARIOS (BANK-APP)
=========================================================

Este proyecto es un ecosistema de microservicios desarrollado con:
- Java 17 / Spring Boot
- Project Reactor (Programación Reactiva)
- MySQL (Persistencia)
- Redpanda / Kafka (Mensajería)
- Docker & Docker Compose (Infraestructura)

---------------------------------------------------------
1. REQUISITOS PREVIOS
---------------------------------------------------------
* Java 17 instalado.
* Docker Desktop o Docker Engine funcionando.
* Herramienta Bruno (para pruebas de API).

---------------------------------------------------------
2. COMPILACIÓN DE LOS SERVICIOS
---------------------------------------------------------
Antes de levantar los contenedores, es necesario generar los 
archivos .jar de cada microservicio.

A. Customer Service:
   cd customer-service
   .\mvnw clean package
   cd ..

B. Account Service:
   (Se omiten los tests porque requieren la infraestructura activa)
   cd account-service)
   .\mvnw clean package -DskipTests
   cd ..

---------------------------------------------------------
3. DESPLIEGUE CON DOCKER
---------------------------------------------------------
Desde el directorio raíz (donde está el docker-compose.yml):

> Para subir los servicios:
  docker compose up -d

> Para bajar los servicios:
  docker compose down

> Para ver los logs en tiempo real:
  docker compose logs -f

---------------------------------------------------------
4. BASE DE DATOS Y PERSISTENCIA
---------------------------------------------------------
* El script SQL de inicialización se encuentra en la carpeta /db.
* Docker se encarga de crear la base de datos "bank_db" y las 
  tablas automáticamente al iniciar el contenedor de MySQL.

---------------------------------------------------------
5. PRUEBAS DE ENDPOINTS (API)
---------------------------------------------------------
* En la carpeta /bruno encontrarás la colección de peticiones.
* Importa la carpeta completa en la aplicación "Bruno" para 
  probar los flujos de Clientes, Cuentas y Movimientos.

---------------------------------------------------------
6. NOTAS TÉCNICAS
---------------------------------------------------------
* Kafka/Redpanda: Si hay errores de conexión, verifica que 
  SPRING_KAFKA_BOOTSTRAP_SERVERS apunte a "redpanda:9092" 
  dentro de la red de Docker.
* Puertos:
  - Customer Service: 8081 (aprox)
  - Account Service:  8082 (aprox)
  - Redpanda:         9092 / 19092
  - MySQL:            3306
=========================================================