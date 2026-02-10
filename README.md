# SysAdmin Monitor Dashboard

Sistema de monitorizacion en tiempo real que recoge metricas de CPU y RAM de una maquina y las envia a una API REST

## Como funciona

Un script en Python lee el estado del sistema (CPU y RAM) cada 5 segundos usando psutil y lo envia por POST a un backend en Java Spring Boot que lo guarda en base de datos

## Stack

- **Backend:** Java 21 + Spring Boot + Spring Data JPA + H2
- **Agente:** Python 3 + psutil + requests

## Como arrancarlo

### Backend

Abrir el proyecto backend/ en IntelliJ y ejecutar MonitorApplication.java

El servidor arranca en http://localhost:8080

### Agente Python
```bash
    cd agent
    pip install -r requirements.txt
    python agent.py
```
### Consola H2

Para ver los datos guardados ir a http://localhost:8080/h2-console

- JDBC URL: jdbc:h2:mem:monitordb
- User: sa
- Password: vacio

## Estado del proyecto

- [x] API REST con endpoint POST /api/metrics
- [x] Agente Python que envia metricas automaticamente
- [x] Base de datos H2 en memoria
- [ ] Endpoint GET para consultar metricas
- [ ] Frontend con graficos (Chart.js)
- [ ] Migracion a PostgreSQL