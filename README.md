# Parcial Spring Boot - Chuleta Practica

## Ejecutar en Linux
```bash
sudo apt update
sudo apt install openjdk-17-jdk maven -y

cd ~/ruta/parcial-apuntes-spring-boot
mvn clean compile
mvn spring-boot:run
```

## Base de datos en examen
- Por defecto: H2 en memoria (ya configurada)
- URL app: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
- JDBC: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: vacio

## Estructura minima que siempre te piden
```text
src/main/java/com/parcial/apuntes/
  models/        # Entidades JPA (@Entity)
  repositories/  # Interfaces JpaRepository + @Query
  services/      # Logica + validaciones + @Transactional
  controllers/   # Endpoints REST
```

## Archivos clave de chuleta
- `CHULETA.md`: estructura general + checklist + recetas rapidas
- `src/main/java/com/parcial/apuntes/chuleta/ParcialChuleta.java`: snippets buscables en un solo archivo

## Comandos rapidos
```bash
# compilar
mvn clean compile

# ejecutar
mvn spring-boot:run

# test
mvn test

# matar puerto 8080 en linux
lsof -i :8080
kill -9 <PID>
```
