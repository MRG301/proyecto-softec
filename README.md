# Sistpolizas

Sistema de pólizas desarrollado con Spring Boot.

## Requisitos

- JDK 21 (definido en `pom.xml`).
- Maven 3 o usar el wrapper `mvnw` incluido en este repositorio.

## Compilación

Ejecute:

```bash
./mvnw clean package
```

(o `mvn clean package` si tiene Maven instalado).

## Ejecución

Para iniciar la aplicación Spring Boot puede usar:

```bash
./mvnw spring-boot:run
```

o bien, después de compilar, ejecutar el JAR generado:

```bash
java -jar target/sistpolizas-0.0.1-SNAPSHOT.jar
```

La aplicación queda disponible en `http://localhost:8080`.

Al iniciar, el servicio ejecuta una sincronización automática con un sistema
remoto configurado mediante la propiedad `remote.base-url` en
`application.properties`.

## Uso básico del API

Algunos ejemplos de los puntos de entrada REST:

- `GET /clientes` – lista de clientes.
- `GET /polizas` – lista de pólizas.
- `GET /beneficiarios` – lista de beneficiarios.

Ejemplo con `curl`:

```bash
curl http://localhost:8080/polizas
```

## Pruebas

Para ejecutar las pruebas unitarias:

```bash
./mvnw test
```

