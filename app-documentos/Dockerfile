FROM eclipse-temurin:21-jre-jammy

# Crea el directorio /app
RUN mkdir /app

# Define /app como el directorio de trabajo
WORKDIR /app

# Copia el jar
COPY build/libs/app-documentos-1.0-SNAPSHOT.jar .

# Establece el comando por defecto para ejecutar la aplicación
CMD ["java", "-jar", "app-documentos-1.0-SNAPSHOT.jar"]