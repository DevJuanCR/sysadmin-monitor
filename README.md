# SysAdmin Monitor Dashboard

Sistema de monitorizacion en tiempo real que recoge metricas de CPU y RAM de una maquina y las envia a una API REST

## Como funciona

Un script en Python lee el estado del sistema (CPU y RAM) cada 5 segundos usando `psutil` y lo envia por POST a un backend en Java Spring Boot que lo guarda en base de datos. Un frontend con `Chart.js` muestra los graficos en tiempo real

## Stack

- **Backend:** Java 21 + Spring Boot + Spring Data JPA
- **Base de datos:** PostgreSQL 16 (Docker) / H2 para desarrollo
- **Agente:** Python 3 + `psutil` + `requests`
- **Frontend:** HTML5 + CSS3 + JavaScript + `Chart.js`

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
    mvn spring-boot:run -Dspring-boot.run.profiles=prod
```
### Agente Python
```bash
    cd agent
    pip install -r requirements.txt
    python agent.py
```
### Dashboard

Abrir `http://localhost:8080/index.html` en el navegador

## Estado del proyecto

- [x] API REST con endpoint POST `/api/metrics`
- [x] Agente Python que envia metricas automaticamente
- [x] Base de datos H2 en memoria
- [x] Endpoint GET para consultar metricas
- [x] Frontend con graficos de CPU y RAM en tiempo real
- [X] Migracion a PostgreSQL
- [x] Perfiles de Spring (dev/prod)