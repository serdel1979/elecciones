# Usa una imagen base de Java
FROM adoptopenjdk:11-jre-hotspot

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo WAR desde el directorio "target" del proyecto al contenedor
COPY target/12354854985.war /app/app.war

# Expone el puerto 8080 para acceder a la aplicación web
EXPOSE 8080

# Comando para ejecutar la aplicación cuando el contenedor se inicie
CMD ["java", "-jar", "app.war"]