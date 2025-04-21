# Sistema Educativo Microservicios

Este repositorio contiene una aplicación de sistema educativo basada en microservicios desarrollada con Spring Boot. A continuación, se proporciona la documentación completa para configurar, ejecutar y utilizar el sistema.

## Estructura del Proyecto

```bash
sistema-educativo-microservicios-richard/
│
├── docker-compose.yml              # Configuración Docker Compose para todos los servicios
│

discovery-server/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── educativo/
│   │   │           └── discovery/
│   │   │               ├── DiscoveryApplication.java  # Clase principal con @EnableEurekaServer
│   │   │               └── config/                    # Configuraciones adicionales si las hay
│   │   │
│   │   └── resources/
│   │       ├── application.properties                 # Configuración del servidor Eureka
│   │       └── static/                                # Recursos estáticos para la UI de Eureka
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── educativo/
│                   └── discovery/
│                       └── DiscoveryApplicationTests.java  # Pruebas del servidor
│
├── Dockerfile                                     # Configuración para construir imagen Docker
├── pom.xml                                        # Dependencias del proyecto
└── .mvn/                                          # Archivos de soporte de Maven

│

usuarios-servicio/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── educativo/
│   │   │           └── usuarios/
│   │   │               ├── UsuariosApplication.java   # Clase principal con @EnableDiscoveryClient
│   │   │               ├── config/                    # Configuraciones de Spring y seguridad
│   │   │               │   ├── SecurityConfig.java    # Configuración de seguridad
│   │   │               │   └── WebConfig.java         # Configuraciones web adicionales
│   │   │               │
│   │   │               ├── controller/                # Controladores REST
│   │   │               │   ├── AuthController.java    # Controlador de autenticación
│   │   │               │   ├── UsuarioController.java # Controlador de usuarios
│   │   │               │   └── DashboardController.java # Dashboard de monitorización
│   │   │               │
│   │   │               ├── dto/                       # Objetos de transferencia de datos
│   │   │               │   ├── JwtResponse.java       # Respuesta con token JWT
│   │   │               │   ├── LoginRequest.java      # Solicitud de inicio de sesión
│   │   │               │   └── RegistroRequest.java   # Solicitud de registro
│   │   │               │
│   │   │               ├── model/                     # Entidades JPA
│   │   │               │   └── Usuario.java           # Modelo de usuario
│   │   │               │
│   │   │               ├── repository/                # Repositorios JPA
│   │   │               │   └── UsuarioRepository.java # Acceso a datos de usuarios
│   │   │               │
│   │   │               └── security/                  # Configuración de seguridad
│   │   │                   ├── JwtAuthenticationFilter.java # Filtro JWT
│   │   │                   ├── JwtTokenProvider.java       # Generación/validación de tokens
│   │   │                   └── UserDetailsServiceImpl.java # Implementación de UserDetailsService
│   │   │
│   │   └── resources/
│   │       ├── application.properties                 # Configuración del microservicio
│   │       ├── application-dev.properties             # Configuración para entorno de desarrollo
│   │       ├── application-prod.properties            # Configuración para producción
│   │       ├── data.sql                               # Datos iniciales (opcional)
│   │       └── templates/                             # Plantillas Thymeleaf
│   │           └── dashboard.html                     # Plantilla para el dashboard
│   │
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── educativo/
│       │           └── usuarios/
│       │               ├── UsuariosApplicationTests.java  # Pruebas generales
│       │               ├── controller/                    # Pruebas de controladores
│       │               │   ├── AuthControllerTest.java
│       │               │   └── UsuarioControllerTest.java
│       │               │
│       │               ├── repository/                    # Pruebas de repositorios
│       │               └── security/                      # Pruebas de seguridad
│       │
│       └── resources/
│           └── application.properties                 # Configuración para pruebas
│
├── Dockerfile                                     # Configuración Docker
├── pom.xml                                        # Dependencias del proyecto
└── .mvn/                                          # Archivos de soporte de Maven

asignaturas-servicio/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── educativo/
│   │   │           └── asignaturas/
│   │   │               ├── AsignaturasApplication.java  # Clase principal con @EnableDiscoveryClient
│   │   │               ├── config/                      # Configuraciones
│   │   │               │   ├── SecurityConfig.java      # Configuración de seguridad
│   │   │               │   └── MethodSecurityConfig.java # Configuración de seguridad por método
│   │   │               │
│   │   │               ├── controller/                  # Controladores REST
│   │   │               │   ├── AsignaturaController.java # Gestión de asignaturas
│   │   │               │   └── DashboardController.java  # Dashboard de monitorización
│   │   │               │
│   │   │               ├── model/                       # Entidades JPA
│   │   │               │   └── Asignatura.java          # Modelo de asignatura
│   │   │               │
│   │   │               ├── repository/                  # Repositorios JPA
│   │   │               │   └── AsignaturaRepository.java # Acceso a datos de asignaturas
│   │   │               │
│   │   │               └── security/                    # Configuración de seguridad
│   │   │                   ├── JwtAuthenticationFilter.java # Filtro JWT
│   │   │                   └── JwtTokenProvider.java        # Validación de tokens
│   │   │
│   │   └── resources/
│   │       ├── application.properties                   # Configuración del microservicio
│   │       ├── application-dev.properties               # Configuración para desarrollo
│   │       ├── data.sql                                 # Datos iniciales (opcional)
│   │       └── templates/                               # Plantillas Thymeleaf
│   │           └── dashboard.html                       # Plantilla para el dashboard
│   │
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── educativo/
│       │           └── asignaturas/
│       │               ├── AsignaturasApplicationTests.java  # Pruebas generales
│       │               └── controller/                       # Pruebas de controladores
│       │                   └── AsignaturaControllerTest.java
│       │
│       └── resources/
│           └── application.properties                   # Configuración para pruebas
│
├── Dockerfile                                       # Configuración Docker
├── pom.xml                                          # Dependencias del proyecto
└── .mvn/                                            # Archivos de soporte de Maven

matriculas-servicio/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── educativo/
│   │   │           └── matriculas/
│   │   │               ├── MatriculasApplication.java   # Clase principal con @EnableDiscoveryClient y @EnableFeignClients
│   │   │               ├── client/                      # Clientes Feign para comunicación entre servicios
│   │   │               │   ├── AsignaturaClient.java    # Cliente para el servicio de asignaturas
│   │   │               │   └── UsuarioClient.java       # Cliente para el servicio de usuarios
│   │   │               │
│   │   │               ├── config/                      # Configuraciones
│   │   │               │   ├── FeignClientConfig.java   # Configuración de Feign Client
│   │   │               │   ├── SecurityConfig.java      # Configuración de seguridad
│   │   │               │   └── MethodSecurityConfig.java # Configuración de seguridad por método
│   │   │               │
│   │   │               ├── controller/                  # Controladores REST
│   │   │               │   ├── MatriculaController.java # Gestión de matrículas
│   │   │               │   └── DashboardController.java # Dashboard de monitorización
│   │   │               │
│   │   │               ├── model/                       # Modelos de datos
│   │   │               │   ├── Matricula.java           # Entidad principal
│   │   │               │   ├── Asignatura.java          # Modelo para datos de asignaturas
│   │   │               │   └── Usuario.java             # Modelo para datos de usuarios
│   │   │               │
│   │   │               ├── repository/                  # Repositorios JPA
│   │   │               │   └── MatriculaRepository.java # Acceso a datos de matrículas
│   │   │               │
│   │   │               └── security/                    # Configuración de seguridad
│   │   │                   ├── JwtAuthenticationFilter.java # Filtro JWT
│   │   │                   └── JwtTokenProvider.java        # Validación de tokens
│   │   │
│   │   └── resources/
│   │       ├── application.properties                   # Configuración del microservicio
│   │       ├── application-dev.properties               # Configuración para desarrollo
│   │       └── templates/                               # Plantillas Thymeleaf
│   │           └── dashboard.html                       # Plantilla para el dashboard
│   │
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── educativo/
│       │           └── matriculas/
│       │               ├── MatriculasApplicationTests.java  # Pruebas generales
│       │               └── controller/                      # Pruebas de controladores
│       │                   └── MatriculaControllerTest.java
│       │
│       └── resources/
│           └── application.properties                   # Configuración para pruebas
│
├── Dockerfile                                       # Configuración Docker
├── pom.xml                                          # Dependencias del proyecto
└── .mvn/                                            # Archivos de soporte de Maven

```

## Arquitectura y Comunicación entre Servicios

El sistema utiliza una arquitectura de microservicios con los siguientes componentes:

1. **Discovery Server (Eureka)**: Registra todos los microservicios y permite que se descubran entre sí.

2. **Usuarios Servicio**: Gestiona la autenticación, registro y administración de usuarios.

3. **Asignaturas Servicio**: Gestiona las asignaturas/cursos disponibles.

4. **Matrículas Servicio**: Gestiona las matrículas de estudiantes en asignaturas.

### Comunicación entre Servicios

- Los servicios se comunican entre sí utilizando Feign Client.
- El servicio de Matrículas consume datos de los servicios de Usuarios y Asignaturas.
- La autenticación se realiza mediante tokens JWT generados por el servicio de Usuarios.
- Todos los servicios se registran en Eureka para el descubrimiento dinámico.

## Inicio del Proyecto con Docker

### Iniciar todos los servicios

```bash
docker-compose up -d
```

### Iniciar un servicio específico

```bash
docker-compose up -d discovery-server
docker-compose up -d usuarios-servicio
docker-compose up -d asignaturas-servicio
docker-compose up -d matriculas-servicio
```

### Detener todos los servicios

```bash
docker-compose down
```

### Detener un servicio específico

```bash
docker-compose stop discovery-server
docker-compose stop usuarios-servicio
docker-compose stop asignaturas-servicio
docker-compose stop matriculas-servicio
```

### Reconstruir imágenes después de cambios

```bash
docker-compose build usuarios-servicio

# O todos los servicios
docker-compose build
```

## Ejecución de Pruebas

En este proyecto se han implementado pruebas unitarias y de integración para garantizar la calidad y robustez de cada microservicio. A continuación, se detalla la estructura de pruebas y cómo ejecutarlas.

Tipos de Pruebas Implementadas
Pruebas Unitarias con Mockito
Estas pruebas verifican el comportamiento de componentes individuales aislando sus dependencias mediante mocks.

Pruebas de Integración con WebTestClient
Estas pruebas verifican la interacción entre componentes y la API REST utilizando solicitudes HTTP reales pero en un entorno controlado.

Ejecución de Pruebas Específicas
Para ejecutar pruebas específicas de cada microservicio, se pueden utilizar los siguientes comandos:

Microservicio de Usuarios

```bash
# Ejecutar todas las pruebas del microservicio
mvn test -pl usuarios-servicio

# Ejecutar prueba específica
mvn test -pl usuarios-servicio -Dtest=AuthControllerTest
mvn test -pl usuarios-servicio -Dtest=UsuarioControllerTest
```

Microservicio de Asignaturas

```bash
# Ejecutar todas las pruebas del microservicio
mvn test -pl asignaturas-servicio

# Ejecutar prueba específica
mvn test -pl asignaturas-servicio -Dtest=AsignaturaControllerTest
mvn test -pl asignaturas-servicio -Dtest=AsignaturaWebTestClientIntegrationTest
```

Microservicio de Matrículas

```bash
# Ejecutar todas las pruebas del microservicio
mvn test -pl matriculas-servicio

# Ejecutar prueba específica
mvn test -pl matriculas-servicio -Dtest=MatriculaControllerTest
mvn test -pl matriculas-servicio -Dtest=MatriculaWebTestClientIntegrationTest
```

### Detalles de las Pruebas Implementadas

Pruebas Unitarias

| Microservicio    | Clase de Prueba    | Descripción                           | Resultado Esperado |
| :--------- | :------- | :--------------------------------------- | :--------------------------------------- |
| `Usuarios`   | `AuthControllerTest` | Verifica el proceso de autenticación y generación de token JWT | Validación de credenciales correcta generación de token JWT y devolución de datos del usuario |
| `Usuarios`    | `UsuarioControllerTest` | Verifica la obtención y listado de usuarios | Recuperación correcta de datos de usuarios desde el repositorio |
| `Asignaturas` | `AsignaturaControllerTest` | Verifica el listado de asignaturas | Devolución de lista de asignaturas desde el repositorio |
| `Matrículas` | `MatriculaControllerTest` | Verifica la creación de una matrícula | Creación correcta de matrícula con validación de usuario y asignaturas |

Pruebas de Integración

| Microservicio    | Clase de Prueba    | Descripción                           | Resultado Esperado |
| :--------- | :------- | :--------------------------------------- | :--------------------------------------- |
| `Asignaturas`   | `AsignaturaWebTestClientIntegrationTest` | Verifica la API REST de asignaturas | Operaciones CRUD correctas a través de endpoints REST |
| `Matrículas`    | `MatriculaWebTestClientIntegrationTest` | Verifica el flujo completo de creación y consulta de matrículas | Creación correcta de matrícula y recuperación de datos relacionados |

### Cobertura de Pruebas

Las pruebas cubren los siguientes aspectos críticos de la aplicación:

- Autenticación y seguridad: Generación y validación de tokens JWT
- Acceso a datos: Interacción con repositorios y bases de datos
- Comunicación entre servicios: Uso correcto de Feign Clients
- Autorización basada en roles: Validación de permisos según el rol del usuario
- API REST: Correcto funcionamiento de los endpoints expuestos

### Resultados

Al ejecutar las pruebas, Maven mostrará un resumen similar al siguiente:

```bash
[INFO] Results:
[INFO] 
[INFO] Tests run: XX, Failures: 0, Errors: 0, Skipped: 0
```

- Tests run: Número total de pruebas ejecutadas
- Failures: Pruebas que fallaron por aserciones incorrectas
- Errors: Pruebas que generaron excepciones inesperadas
- Skipped: Pruebas omitidas

Para ver resultados detallados, se pueden consultar los informes generados en:

{microservicio}/target/surefire-reports/

### Pruebas en el Pipeline CI/CD

El sistema incluye integración con GitHub Actions para ejecutar automáticamente las pruebas en cada cambio:

- El pipeline de CI ejecuta todas las pruebas unitarias y de integración
- Solo si todas las pruebas son exitosas, se continúa con el proceso de CD
- Se generan informes de pruebas disponibles como artefactos en GitHub Actions

## Monitoreo y Visualización: Prometheus y Grafana

El sistema cuenta con monitoreo centralizado y visualización de métricas a través de **Prometheus** y **Grafana**, ya preconfigurados y disponibles al levantar el entorno con Docker Compose.

### Acceso a Prometheus

- **URL**: [http://localhost:9090](http://localhost:9090)
- **Descripción**: Permite consultar y explorar métricas de todos los microservicios, así como del pipeline de CI/CD (GitHub Actions) si está configurado el exporter.
- **Configuración**: Prometheus está configurado para recolectar métricas de:
  - Microservicios Spring Boot (`/actuator/prometheus`)
  - Exporter de GitHub Actions (`/metrics` en el puerto 9171)
- **Verificación**: Ver el estado de los targets en `Status > Targets` y buscar métricas como `http_server_requests_seconds_count`, `jvm_memory_used_bytes`, `github_actions_workflow_run_status`, entre otras.

### Acceso a Grafana

- **URL**: [http://localhost:3000](http://localhost:3000)
- **Usuario/Contraseña por defecto**: admin / admin
- **Descripción**: Plataforma de visualización donde puedes importar dashboards comunitarios o crear paneles personalizados para monitorear el estado y rendimiento de los microservicios y pipelines de CI/CD.
- **Dashboards configurados**:
  - **4701**: Micrometer JVM dashboard
  - **10280**: Spring Boot 2.1 Statistics
  - **15187**: GitHub Actions Overview (para métricas de CI/CD)
- **Configuración**: Prometheus ya está configurado como fuente de datos en Grafana. Solo se debe importar los dashboards deseados adicionales desde la opción **Dashboards > Import**.

### Resumen de configuración

- **Prometheus** Se configuro para recolectar y almacenar métricas de todos los servicios y del pipeline de CI/CD.
- **Grafana** se configuro para visualizar y analizar métricas mediante dashboards listos para usar o personalizados.
- Ambas herramientas están disponibles automáticamente al iniciar el entorno con Docker Compose.

## Acceso a los Servicios

### Eureka Server (Discovery)

- **URL**: [http://localhost:8761/](http://localhost:8761/)
- **Descripción**: Muestra todos los microservicios registrados, su estado y metadatos.

### Dashboards de Microservicios

Cada microservicio tiene un dashboard para monitorear su estado:

- **Usuarios Dashboard**:
  - **URL**: [http://localhost:8081/dashboard](http://localhost:8081/dashboard)
  - **Muestra**: Estado del servicio, uso de CPU y memoria, información de servicio y componentes.

- **Asignaturas Dashboard**:
  - **URL**: [http://localhost:8082/dashboard](http://localhost:8082/dashboard)
  - **Muestra**: Estado del servicio, uso de CPU y memoria, información de servicio y componentes.

- **Matrículas Dashboard**:
  - **URL**: [http://localhost:8083/dashboard](http://localhost:8083/dashboard)
  - **Muestra**: Estado del servicio, uso de CPU y memoria, información de servicio y componentes.

## Guía de APIs y Endpoints

### Autenticación y Usuarios

#### Registro de usuario

```http
POST /auth/registro
```

| Campo      | Tipo     | Descripción                              |
| :--------- | :------- | :--------------------------------------- |
| `nombre`   | `string` | **Requerido**. Nombre del usuario        |
| `email`    | `string` | **Requerido**. Email del usuario (único) |
| `password` | `string` | **Requerido**. Contraseña del usuario    |

Ejemplo de solicitud:

```sh
curl -X POST http://localhost:8081/auth/registro \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Administrador",
    "email": "admin@example.com",
    "password": "Admin123!"
  }'
```

Respuesta exitosa:

```json
{
  "id": 1,
  "nombre": "Administrador",
  "email": "admin@example.com",
  "rol": "USER"
}
```

#### Cambiar rol de usuario a ADMIN

Los usuarios por defecto se crean con el rol USER, Para elevar un usuario a administrador, ejecute esta consulta SQL en la base de datos:

```sql
UPDATE usuarios_db.usuario SET rol = 'ADMIN' WHERE email = 'admin@example.com';
```

#### Iniciar sesión y obtener token JWT

```http
POST /auth/login
```

| Campo      | Tipo     | Descripción                           |
| :--------- | :------- | :------------------------------------ |
| `email`    | `string` | **Requerido**. Email del usuario      |
| `password` | `string` | **Requerido**. Contraseña del usuario |

Ejemplo de solicitud:

```sh
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@example.com",
    "password": "Admin123!"
  }'
```

Respuesta exitosa:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer",
  "id": 1,
  "nombre": "Administrador",
  "email": "admin@example.com",
  "rol": "ADMIN"
}
```

### Endpoints del Servicio de Usuarios

#### Listar todos los usuarios (solo admin)

```http
GET /usuarios
```

Ejemplo de solicitud:

```sh
curl -X GET http://localhost:8081/usuarios \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

Respuesta exitosa:

```json
[
  {
    "id": 1,
    "nombre": "Administrador",
    "email": "admin@example.com",
    "rol": "ADMIN"
  },
  {
    "id": 2,
    "nombre": "Usuario Ejemplo",
    "email": "usuario@example.com",
    "rol": "USER"
  }
]
```

#### Obtener usuario por ID

```http
GET /usuarios/{id}
```

| Parámetro | Tipo   | Descripción                             |
| :-------- | :----- | :-------------------------------------- |
| `id`      | `long` | **Requerido**. ID del usuario a obtener |

Ejemplo de solicitud:

```sh
curl -X GET http://localhost:8081/usuarios/1 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

Respuesta exitosa:

```json
{
  "id": 1,
  "nombre": "Administrador",
  "email": "admin@example.com",
  "rol": "ADMIN"
}
```

#### Actualizar usuario

```http
PUT /usuarios/{id}
```

| Parámetro | Tipo     | Descripción                                |
| :-------- | :------- | :----------------------------------------- |
| `id`      | `long`   | **Requerido**. ID del usuario a actualizar |
| `nombre`  | `string` | **Opcional**. Nuevo nombre del usuario     |
| `email`   | `string` | **Opcional**. Nuevo email del usuario      |

Ejemplo de solicitud:

```sh
curl -X PUT http://localhost:8081/usuarios/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -d '{
    "nombre": "Administrador Actualizado",
    "email": "admin@example.com"
  }'
```

Respuesta exitosa:

```json
{
  "id": 1,
  "nombre": "Administrador Actualizado",
  "email": "admin@example.com",
  "rol": "ADMIN"
}
```

### Endpoints del Servicio de Asignaturas

#### Listar todas las asignaturas

```http
GET /asignaturas
```

Ejemplo de solicitud:

```sh
curl -X GET http://localhost:8082/asignaturas \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

Respuesta exitosa:

```json
[
  {
    "id": 1,
    "nombre": "Programación Avanzada",
    "codigo": "PROG301",
    "creditos": 5
  },
  {
    "id": 2,
    "nombre": "Bases de Datos",
    "codigo": "BD201",
    "creditos": 4
  }
]
```

#### Obtener asignatura por ID

```http
GET /asignaturas/{id}
```

| Parámetro | Tipo   | Descripción                                  |
| :-------- | :----- | :------------------------------------------- |
| `id`      | `long` | **Requerido**. ID de la asignatura a obtener |

Ejemplo de solicitud:

```sh
curl -X GET http://localhost:8082/asignaturas/1 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

Respuesta exitosa:

```json
{
  "id": 1,
  "nombre": "Programación Avanzada",
  "codigo": "PROG301",
  "creditos": 5
}
```

#### Crear asignatura (solo admin)

```http
POST /asignaturas/crear
```

| Campo      | Tipo     | Descripción                                  |
| :--------- | :------- | :------------------------------------------- |
| `nombre`   | `string` | **Requerido**. Nombre de la asignatura       |
| `codigo`   | `string` | **Requerido**. Código único de la asignatura |
| `creditos` | `int`    | **Requerido**. Cantidad de créditos          |

Ejemplo de solicitud:

```sh
curl -X POST http://localhost:8082/asignaturas/crear \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -d '{
    "nombre": "Programación Avanzada",
    "codigo": "PROG301",
    "creditos": 5
  }'
```

Respuesta exitosa:

```json
{
  "id": 1,
  "nombre": "Programación Avanzada",
  "codigo": "PROG301",
  "creditos": 5
}
```

#### Actualizar asignatura (solo admin)

```http
PUT /asignaturas/{id}
```

| Parámetro  | Tipo     | Descripción                                     |
| :--------- | :------- | :---------------------------------------------- |
| `id`       | `long`   | **Requerido**. ID de la asignatura a actualizar |
| `nombre`   | `string` | **Opcional**. Nuevo nombre de la asignatura     |
| `codigo`   | `string` | **Opcional**. Nuevo código de la asignatura     |
| `creditos` | `int`    | **Opcional**. Nueva cantidad de créditos        |

Ejemplo de solicitud:

```sh
curl -X PUT http://localhost:8082/asignaturas/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -d '{
    "nombre": "Programación Avanzada",
    "codigo": "PROG301",
    "creditos": 6
  }'
```

Respuesta exitosa:

```json
{
  "id": 1,
  "nombre": "Programación Avanzada",
  "codigo": "PROG301",
  "creditos": 6
}
```

#### Eliminar asignatura (solo admin)

```http
DELETE /asignaturas/{id}
```

| Parámetro | Tipo   | Descripción                                   |
| :-------- | :----- | :-------------------------------------------- |
| `id`      | `long` | **Requerido**. ID de la asignatura a eliminar |

Ejemplo de solicitud:

```sh
curl -X DELETE http://localhost:8082/asignaturas/1 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

Respuesta exitosa:

```json
{
  "mensaje": "Asignatura eliminada correctamente"
}
```

### Endpoints del Servicio de Matrículas

#### Crear matrícula

```http
POST /matriculas
```

| Campo            | Tipo     | Descripción                                    |
| :--------------- | :------- | :--------------------------------------------- |
| `usuarioId`      | `long`   | **Requerido**. ID del usuario que se matricula |
| `asignaturasIds` | `array`  | **Requerido**. Array de IDs de asignaturas     |
| `periodo`        | `string` | **Opcional**. Periodo académico (ej: "2025-1") |

Ejemplo de solicitud:

```sh
curl -X POST http://localhost:8083/matriculas \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -d '{
    "usuarioId": 1,
    "asignaturasIds": [1, 2],
    "periodo": "2025-1"
  }'
```

Respuesta exitosa:

```json
{
  "matricula": {
    "id": 1,
    "usuarioId": 1,
    "asignaturasIds": [1, 2],
    "periodo": "2025-1",
    "fechaCreacion": "2025-04-10T10:15:30"
  },
  "usuario": {
    "id": 1,
    "nombre": "Administrador",
    "email": "admin@example.com"
  },
  "asignaturas": [
    {
      "id": 1,
      "nombre": "Programación Avanzada",
      "codigo": "PROG301",
      "creditos": 5
    },
    {
      "id": 2,
      "nombre": "Bases de Datos",
      "codigo": "BD201",
      "creditos": 4
    }
  ]
}
```

#### Obtener matrícula por ID

```http
GET /matriculas/{id}
```

| Parámetro | Tipo   | Descripción                                 |
| :-------- | :----- | :------------------------------------------ |
| `id`      | `long` | **Requerido**. ID de la matrícula a obtener |

Ejemplo de solicitud:

```sh
curl -X GET http://localhost:8083/matriculas/1 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

Respuesta exitosa:

```json
{
  "matricula": {
    "id": 1,
    "usuarioId": 1,
    "asignaturasIds": [1, 2],
    "periodo": "2025-1",
    "fechaCreacion": "2025-04-10T10:15:30"
  },
  "usuario": {
    "id": 1,
    "nombre": "Administrador",
    "email": "admin@example.com"
  },
  "asignaturas": [
    {
      "id": 1,
      "nombre": "Programación Avanzada",
      "codigo": "PROG301",
      "creditos": 5
    },
    {
      "id": 2,
      "nombre": "Bases de Datos",
      "codigo": "BD201",
      "creditos": 4
    }
  ]
}
```

#### Obtener matrículas por usuario

```http
GET /matriculas/usuario/{usuarioId}
```

| Parámetro   | Tipo   | Descripción                                                       |
| :---------- | :----- | :---------------------------------------------------------------- |
| `usuarioId` | `long` | **Requerido**. ID del usuario cuyas matrículas se quieren obtener |

Ejemplo de solicitud:

```sh
curl -X GET http://localhost:8083/matriculas/usuario/1 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

Respuesta exitosa:

```json
[
  {
    "matricula": {
      "id": 1,
      "usuarioId": 1,
      "asignaturasIds": [1, 2],
      "periodo": "2025-1",
      "fechaCreacion": "2025-04-10T10:15:30"
    },
    "usuario": {
      "id": 1,
      "nombre": "Administrador",
      "email": "admin@example.com"
    },
    "asignaturas": [
      {
        "id": 1,
        "nombre": "Programación Avanzada",
        "codigo": "PROG301",
        "creditos": 5
      },
      {
        "id": 2,
        "nombre": "Bases de Datos",
        "codigo": "BD201",
        "creditos": 4
      }
    ]
  }
]
```

#### Listar todas las matrículas (solo admin)

```http
GET /matriculas
```

Ejemplo de solicitud:

```sh
curl -X GET http://localhost:8083/matriculas \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

Respuesta exitosa:

```json
[
  {
    "matricula": {
      "id": 1,
      "usuarioId": 1,
      "asignaturasIds": [1, 2],
      "periodo": "2025-1",
      "fechaCreacion": "2025-04-10T10:15:30"
    },
    "usuario": {
      "id": 1,
      "nombre": "Administrador",
      "email": "admin@example.com"
    },
    "asignaturas": [
      {
        "id": 1,
        "nombre": "Programación Avanzada",
        "codigo": "PROG301",
        "creditos": 5
      },
      {
        "id": 2,
        "nombre": "Bases de Datos",
        "codigo": "BD201",
        "creditos": 4
      }
    ]
  }
]
```

## Notas Importantes

1. **Seguridad**: Todas las operaciones (excepto registro e inicio de sesión) requieren un token JWT válido.
2. **Roles**: El sistema tiene dos roles principales:
   - `USER`: Acceso limitado a operaciones de lectura y a sus propios datos.
   - `ADMIN`: Acceso completo a todas las operaciones y datos.
3. **Comunicación entre Servicios**: Se realiza mediante Feign Clients con propagación de tokens JWT.
4. **Base de Datos**: Cada microservicio tiene su propia base de datos independiente.

## Solución de Problemas

1. **Error de Conexión a Eureka**: Asegúrese de que el servidor de descubrimiento (discovery-server) esté en ejecución antes de iniciar los otros servicios.

2. **Error en las Pruebas**: Asegúrese de tener una configuración de prueba adecuada para cada servicio con una base de datos H2 en memoria.

3. **Error de Autenticación**: Verifique que el token JWT sea válido y no haya expirado.

4. **Error en Docker**: Si hay problemas al iniciar los contenedores, intente reconstruir las imágenes con `docker-compose build`.
