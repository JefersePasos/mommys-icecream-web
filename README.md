Mommy’s IceCream Web
Descripción

Este proyecto es una aplicación web desarrollada en Spring Boot con Thymeleaf.
Su objetivo es permitir la gestión de productos dentro del sistema de una heladería.
El administrador puede registrar nuevos productos, editarlos, ocultarlos temporalmente o eliminarlos de forma permanente.
De esta forma se mantiene actualizado el catálogo que aparece en el sitio.


 feature/product

- **Java**: Lenguaje principal para la lógica de negocio.
- **Spring Boot**: Framework para el desarrollo ágil y eficiente del backend.
- **HTML**: Estructura del contenido en la interfaz de usuario.
- **CSS**: Estilos y diseño visual de la aplicación...
main

Estructura principal del proyecto
src/
 └── main/
      ├── java/MommysIceCreamWeb/demo/
      │     ├── controller/
      │     │     └── ProductoController.java
      │     ├── model/
      │     │     └── Producto.java
      │     ├── repository/
      │     │     └── ProductoRepository.java
      │     └── service/
      │           └── ProductoService.java
      └── resources/
            ├── templates/
            │     └── productos/
            │           ├── listar.html
            │           └── form.html
            ├── static/
            │     └── css/
            │           └── style.css
            └── application.properties

Conexión a la base de datos

El sistema está preparado para funcionar con MySQL.
En el archivo application.properties ya viene configurada una conexión local por defecto:

spring.datasource.url=jdbc:mysql://localhost:3306/mommys_icecream
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.thymeleaf.cache=false
server.port=8080


Si se va a usar otro usuario o contraseña de MySQL, solo se deben cambiar esas dos líneas.
La base de datos utilizada se llama mommys_icecream y puede importarse usando el archivo .sql que se incluye en el repositorio.

Cómo ejecutar el proyecto

Crear la base de datos mommys_icecream en MySQL (o importar el archivo .sql).

Verificar la conexión en application.properties.

Desde la carpeta del proyecto, ejecutar el siguiente comando:

mvn spring-boot:run


Una vez iniciado, abrir el navegador y entrar a:

http://localhost:8080/productos


Ahí se puede probar el sistema completo de registro y manejo de productos.

Funcionalidades

Registrar nuevos productos con nombre y estado.

Editar información de productos existentes.

Ocultar temporalmente un producto cuando no está disponible.

Eliminar productos de forma permanente.

Visualización del catálogo en una tabla con opciones de acción.

Estado actual del proyecto

El proyecto se encuentra funcional y listo para pruebas o despliegue.
Solo requiere tener MySQL instalado y la base de datos configurada correctamente.
El resto del código y las vistas están listas para usarse sin cambios adicionales.
