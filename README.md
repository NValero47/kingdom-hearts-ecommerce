# E-Commerce Kingdom Hearts

Proyecto de E-Commerce de productos de Kingdom Hearts desarrollado con **Java 21**, **Spring Boot**, y **Eclipse Java EE**.

## Resumen

- Backend: Spring Boot con servicios JPA.
- Frontend: Thymeleaf, jQuery y Mustache.
- Base de datos: H2 en memoria para pruebas.
- Permite gestionar productos, ver detalles y añadir al carrito.
- Entorno de desarrollo: Eclipse Java EE.

## Setup / Cómo ejecutar el proyecto

Sigue estos pasos para configurar y ejecutar la aplicación en tu entorno local:

1. Preparar el proyecto: Descarga el archivo ZIP del proyecto desde el [release de GitHub](https://github.com/NValero47/kingdom-hearts-ecommerce/releases/download/v1.0/kingdom-hearts-ecommerce.zip)
   y descomprímelo en tu **workspace de Eclipse Java EE**. En Eclipse, selecciona File -> Import -> Existing Maven Projects y elige **la carpeta principal del proyecto** (la que contiene todos los archivos y carpetas del proyecto, NO otra).

3. Configuración de Eclipse: Ve a Window -> Preferences -> XML -> Wild Web Developer y habilita todas las opciones para asegurar la compatibilidad con Thymeleaf y los archivos XML de Spring Boot.

4. Ejecutar la aplicación: Abre la carpeta de paquetes src/main/java, ve al paquete com.nicolas.clienteservidor, haz clic derecho sobre TiendakhApplication.java, selecciona Run As -> Java Application.
   Luego abre tu navegador y escribe http://localhost:8082/ para que la aplicación se ejecute.

6. Restaurar la base de datos inicial: Si quieres volver a los datos iniciales de la base de datos H2, cierra la aplicación, borra la carpeta `data` dentro del proyecto y vuelve a ejecutar la app. La base de datos se recreará automáticamente con los datos iniciales.

## Enlaces
- Descargar proyecto comprimido: [https://github.com/NValero47/kingdom-hearts-ecommerce/releases/download/v1.0/kingdom-hearts-ecommerce.zip](https://github.com/NValero47/kingdom-hearts-ecommerce/releases/download/v1.0/kingdom-hearts-ecommerce.zip)
- Repositorio en GitHub: [https://github.com/NValero47/kingdom-hearts-ecommerce](https://github.com/NValero47/kingdom-hearts-ecommerce)
