# SysAdmin Monitor Dashboard

Sistema de monitorizacion en tiempo real que recoge metricas de CPU y RAM de una maquina y las envia a una API REST

## Como funciona

Un script en Python lee el estado del sistema (CPU y RAM) cada 5 segundos usando `psutil` y lo envia por POST a un backend en Java Spring Boot que lo guarda en base de datos. Un frontend con `Chart.js` muestra los graficos en tiempo real

## Stack

- **Backend:** Java 21 + Spring Boot + Spring Data JPA
- **Base de datos:** PostgreSQL 16 (Docker) / H2 para desarrollo
- **Agente:** Python 3 + `psutil` + `requests`
- **Frontend:** HTML5 + CSS3 + JavaScript + `Chart.js`
- **Testing:** JUnit 5 + Mockito + MockMvc

## Como arrancarlo

### Opcion A - Desarrollo rapido (H2 en memoria)

Abrir el proyecto `backend/` en IntelliJ y ejecutar `MonitorApplication.java`

### Opcion B - Con PostgreSQL (Docker)

Levantar la base de datos:
```bash
    docker compose up -d
```
Arrancar el backend con perfil de produccion:
```bash
    cd backend
    .\mvnw spring-boot:run "-Dspring-boot.run.profiles=prod"
```
### Agente Python
```bash
    cd agent
    pip install -r requirements.txt
    python agent.py
```
### Dashboard

Abrir `http://localhost:8080/index.html` en el navegador

### Ejecutar tests
```bash
    cd backend
    .\mvnw test
```
## API

### POST `/api/metrics`

Envia una metrica. Los campos `cpuUsage` y `ramUsage` son obligatorios y deben estar entre 0 y 100

    {"cpuUsage": 45.2, "ramUsage": 67.8}

### GET `/api/metrics`

Devuelve las ultimas 20 metricas ordenadas por timestamp

## Estado del proyecto

- [x] API REST con endpoints POST y GET
- [x] Agente Python que envia metricas automaticamente
- [x] Base de datos H2 en memoria para desarrollo
- [x] PostgreSQL con Docker para produccion
- [x] Frontend con graficos de CPU y RAM en tiempo real
- [x] Perfiles de Spring (dev/prod)
- [x] Validacion de datos con Bean Validation
- [x] Manejo global de errores
- [x] Tests unitarios y de integracion (9 tests)