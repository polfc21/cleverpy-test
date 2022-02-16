# Cleverpy-test (Versión sin Spring Security)
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

## Especificaciones
Antes de exponer los actores y casos de uso y la arquitectura, hay que especificar
que se ha decidido que solo se puede añadir películas si tiene un director asociado, es decir,
no es posible crear una película sin director, aunque sí que se puede hacer sin que aún tenga actores
asociados. De la misma manera, no se puede eliminar un director que tenga películas asociadas, antes 
se tendrá que borrar estas películas para luego borrar al director.

## Actores y casos de uso identificados
Después de haber contextualizado el modelo del dominio del proyecto, el siguiente paso es establecer
qué actores interactuarán con el sistema creado y de qué manera lo harán. 
Se intentó establecer dos tipos de actores, uno con rol de Administrador y otro con rol de Customer.
En esta versión, se ha optado por no hacer diferenciación, ya que no se ha incluido seguridad, así que 
todos los usuarios pueden atacar a todos los endpoints. Así, se podrá 
crear, actualizar y borrar objetos de las clases ya especificadas, como también recuperar 
datos de todas las entidades, ya sea filtrando de manera específica (mediante diferentes atributos) o
recuperando todas las entidades que existen.
De esta manera, surgen los siguientes diagramas de casos de uso.
### Tareas de Crete, Update and Delete
![UseCaseAdminMovie](/plantuml/UseCaseAdminMovie.png)
![UseCaseAdminDirector](/plantuml/UseCaseAdminDirector.png)
![UseCaseAdminActor](/plantuml/UseCaseAdminActor.png)
### Tareas de Read
![UseCaseUserMovie](/plantuml/UseCaseUserMovie.png)
![UseCaseUserDirector](/plantuml/UseCaseUserDirector.png)
![UseCaseUserActor](/plantuml/UseCaseUserActor.png)

## Arquitectura
Finalmente se expondrá la arquitectura con la que se ha desarrollado la API REST. Se ha optado
por una arquitectura de capas, en las que primero nos encontramos la capa API, que engloba
los controllers que reciben las requests y las gestionan atacando a los services correspondientes.
También en esta capa, tenemos el handler que gestiona las excepciones que se pueden producir
cuando una request no cumple con los requisitos exigidos, y los DTO que se reciben y se envían,
los cuales conocen a las entities que se guardan en las bases de datos. En la capa DOMAIN tenemos
los services que tienen la lógica del negocio, los cuales atacan a los repositories para realizar cualquier
operación CRUD sobre la entidad que se esté gestionando. También en esta capa tenemos las excepciones que 
se lanzarán cuando haya algún tipo de error y los validadores que se han personalizado para diferentes
atributos de los DTO como sería el año de película, el género de los actores o directores y el tipo de género
de la película. En la capa DATA tenemos tanto los Repositorios que atacan a la base de datos, como 
las entidades que se guardan en esta base de datos. La capa CONFIGURATIONS tiene todas las clases
relacionadas con configurar el CORS y la documentación de esta API.

![PackageWithoutClasses](/plantuml/PackageWithoutClasses.png)
### Reparto de paquetes y clases
Concretamente, en el siguiente diagrama, se pueden ver las relaciones que se han establecido entre
todas las clases. Por motivos de tamaño y claridad, se verá cada diagrama por separado, es decir, primero
veremos cuando se gestionan las películas, después con los directores y finalmente con los actores.
#### Arquitectura Movies
![ArchitectureMovie](/plantuml/ArchitectureMovie.png)
#### Arquitectura Directors
![ArchitectureDirector](/plantuml/ArchitectureDirector.png)
#### Arquitectura Actors
![ArchitectureActor](/plantuml/ArchitectureActor.png)

## Tests de la API
Para testear la API, se ha realizado toda una batería de tests que cubren toda la aplicación, 
de la misma manera, se ha incluido una carpeta postman en la raíz, en la que se pueden probar los
diferentes endpoints (ya está configurada con todas las requests, se ha utilizado una variable 
de entorno que se llama url y corresponde a localhost:8081, que es el puerto que se ha puesto
para conectarse con la API). 
También, se ha incluido una carpeta scripts-sql, donde hay dos ficheros .sql, en el que uno se define el 
esquema de la base de datos, y en el otro se incluyen sentencias insert, cosa que me ha permitido probar
estos endpoints con unos datos ya inicializados.
