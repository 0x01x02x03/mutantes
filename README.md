<h1 align="center">🧬 Mutantes API - Magneto Recruiter</h1>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-blue?logo=java" alt="Java 17"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql" alt="PostgreSQL"/>
  <img src="https://img.shields.io/badge/Lombok-enabled-orange?logo=lombok" alt="Lombok"/>
  <img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="MIT License"/>
</p>

---

## ✨ Funcionalidad

> Detecta si un humano es mutante mediante análisis de ADN (NxN).  
> Si hay **más de una secuencia de 4 letras iguales** en horizontal, vertical o diagonal → es mutante.

---

## 📦 Tecnologías

<div align="center">

| Tecnología      | Versión      |
|-----------------|--------------|
| ☕ Java         | 17           |
| 🌱 Spring Boot | 3.x          |
| 🌐 Spring Web  |              |
| 🗄️ Spring Data JPA |         |
| 🐘 PostgreSQL  | 15           |
| 🍃 Lombok      |              |
| 🧪 JUnit + Jacoco |           |

</div>

---

## 🚀 Endpoints REST

### 🔍 Verificar Mutante

```http
POST /mutant/
Content-Type: application/json

{
  "dna": ["ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"]
}
```

- 🧬 **200 OK** si es mutante  
- 🚫 **403 Forbidden** si no es mutante

---

### 📊 Obtener estadísticas

```http
GET /stats
```

Respuesta:
```json
{
  "count_mutant_dna": 40,
  "count_human_dna": 100,
  "ratio": 0.4
}
```

---

## 💻 Ejecutar localmente

### ✅ Pre-requisitos

- JDK 17+
- PostgreSQL corriendo localmente en:
  - **Host:** localhost
  - **Puerto:** 5432
  - **Usuario:** postgres_user
  - **Contraseña:** postgres_pass
  - **DB:** mutantsdb

```sql
CREATE DATABASE mutantsdb;
```

### ⚙️ Configuración

Edita el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mutantsdb
spring.datasource.username=postgres_username
spring.datasource.password=postgres_password
```

### ▶️ Ejecutar

```bash
./mvnw spring-boot:run
```

---

## 🧪 Tests y cobertura

```bash
./mvnw test
./mvnw jacoco:report
```

Luego abre el reporte:  
`target/site/jacoco/index.html`

---

## 📂 Estructura del proyecto

```
com.magneto.mutantes
├── controller
├── service
├── model
├── repository
├── util
```

---

## 🛡️ Mejoras de Seguridad

Actualmente, la API no implementa mecanismos de autenticación ni autorización, por lo que **se recomienda agregar seguridad** para proteger los endpoints y los datos sensibles.  
Algunas sugerencias de mejora:

- Implementar autenticación y autorización (por ejemplo, usando Spring Security y JWT).
- Validar y sanitizar todas las entradas para evitar ataques de inyección.
- Limitar la exposición de información sensible en los mensajes de error.
- **Seguridad por desconocimiento:** Se recomienda utilizar herramientas como [HashiCorp Vault](https://www.vaultproject.io/) u otros sistemas de gestión de secretos para aislar y proteger credenciales, contraseñas y datos sensibles fuera del código fuente y archivos de configuración. Esto ayuda a minimizar el riesgo en caso de filtraciones y a cumplir con buenas prácticas de seguridad.

---

## 📜 Licencia

MIT © [Emilio Filipigh](https://github.com/emiliofilipigh)