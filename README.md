# cleverpy-test
# Introducción
Este proyecto corresponde con la prueba técnica especificada por Cleverpy Machine Learning,
para la posición de Backend. Así pues, el objetivo es construir una API REST sobre un modelo
de dominio basado en películas, actores, directores...
Queda decir que aunque se ha tomado como referencia la siguiente API
https://www.themoviedb.org/documentation/api, se ha optado por realizar una propia estructura de datos
de cada entidad, que se expondrá a continuación.

# Objetivos y Funcionalidades
Tal y como se exige en el documento donde hay toda la información de la prueba, 
los requisitos son los siguientes.

## Requisitos necesarios
* Almacenar películas, directores, actores en base de datos
* Implementar endpoints para listar todas estas entidades con paginación
* Implementar endpoints que nos permitan filtrar estas entidades en función de sus atributos (año, título, lenguaje ...)
* Realizar el proyecto sobre el Framework Spring Boot
* Escoger el gestor de dependencias del proyecto entre las opciones Maven o Gradle (se ha optado por Maven)
* Configurar CORS, ya que el API podrá ser invocado desde aplicaciones Web utilizando llamadas XHR
* Seguir el estándar de Java 8
* Seguir las buenas prácticas (código limpio y bien diseñado)
* Usar Hibernate

## Requisitos opcionales
* Testear la aplicación (tanto con tests unitarios, como con tests de integración)
* Usar la librería lombok
* Autenticar mediante JWT

## Ampliaciones
* Utilizar Docker
* Desplegar la API en un servicio propio o de terceros
* Utilizar Swagger para documentar la API

## Modelo de dominio
En este modelo de dominio se representan las clases del mundo real que la aplicación 
tendrá que gestionar. Así pues, es un punto de partida para contextualizar el posterior
desarrollo de software. De esta manera, vemos que las clases principales incluyen
película, actor, director.
También se ha especificado los atributos de cada clase, con lo que los objetos de las
clases tendrán los siguientes atributos:
* Película : título, año de publicación, lenguaje original de la película, duración de la película,
género de la película, director de la película y actores de la película.
* Persona : nombre, apellidos, país de origen, edad.
* Director : películas dirigidas
* Actor : películas interpretadas

![DomainModel](/plantuml/DomainModel.png)

## Actores y casos de uso identificados
Después de haber contextualizado el modelo del dominio del proyecto, el siguiente paso es establecer
qué actores interactuarán con el sistema creado y de qué manera lo harán. Se ha establecido dos tipos
de actores, el que tendrá rol de Administrador y el que tendrá rol de Usuario. El Administrador podrá
crear, actualizar y borrar objetos de las clases ya especificadas. El Usuario podrá recuperar datos
de todas las entidades, como también de manera específica (filtrando por diferentes atributos)
De esta manera, surgen los siguientes diagramas de casos de uso.
### Administrador
![UseCaseAdminMovie](/plantuml/UseCaseAdminMovie.png)
![UseCaseAdminDirector](/plantuml/UseCaseAdminDirector.png)
![UseCaseAdminActor](/plantuml/UseCaseAdminActor.png)
### Usuario
![UseCaseUserMovie](/plantuml/UseCaseUserMovie.png)
![UseCaseUserDirector](/plantuml/UseCaseUserDirector.png)
![UseCaseUserActor](/plantuml/UseCaseUserActor.png)

