#ADAT_API-GRAF

-----------------------------------------
ENTIDADES
-----------------------------------------
**Entidad/Documento usuario:**

Entidad principal la cual tendrá un nombre de usuario como clave primaria, una contraseña hasheada, un nombre y apellidos,
una dirección, la cual es un documento y un rol, que puede ser "ADMIN" o "USER".

String username: PK
String password: Hashed
String name
String surname
Direccion direccion : Entity
String roles : "ADMIN" || "USER"


**Entidad/Documento direccion:**

Esta es una entidad son colección propia dentro de mongo, ya que su uso es el de almacenar la dirección del usuario.
Esta estará mapeada únicamente dentro de los usuarios y no tiene restricciones

String calle
String num
String municipio
String provincia
String cp

**Entidad/Documento tarea:**

Esta entidad será asignada a un usuario existente dentro de la base de datos, el nombre es el principal campo de búsqueda
por lo que no podrán existir dos tareas con el mismo nombre.

String nombre: PK
String descripción
Boolean completada
Usuario usuario: Entity
  
-----------------------------------------
ENDPOINTS
-----------------------------------------

Se podrán registrar usuarios, loguear usuarios, ver tareas, completar tareas, crear tareas, borrar tareas y editar tareas

**Registrar usuarios:**
Para registrar usuarios en la base de datos se solicita un nombre de usuario y se
debera comprobar que este no está en uso.

**Loguear usuarios:**
Para poder loguearse se debera proporcionar un nombre de usuario almacenado en la base de datos y
una contraseña que coincida con la contraseña del usuario registrado.

**Ver tareas:**
Para poder ver tareas es necesario loguearse como usuario, ya que cada usuario puede ver únicamente las
tareas que le pertenecen, esta regla no aplica para el administrador, que puede ver todas las tareas

**Completar tareas:**
Para poder marcar una tarea como completada se debera estar logueado, cada usuario solo puede marcar como
completada las tareas que le pertenecen a él, el administrador puede marcar las tareas que quiera como                      completada


**Crear tareas:**
Para crear una tarea se debera estar logueado como un usuario registrado, si el usuario no es administrador
solo podrá crear tareas en las cuales el campo usuario sea sí mismo, si se es administrador se pueden crear
tareas para cualquier usuario. Para crear una tarea se debe proporcionar un nombre que no está asignado a
otra tarea asi como una descripción y, en caso de ser administrador, un usuario, el cual debera estar registrado


(Se deben cumplir las mismas condiciones que para crear una tarea)

**Borrar tareas:**
Para borrar una tarea se debe proporcionar el nombre almacenado en la base de datos, si la solicitud proviene
de un usuario no-administrador solo podrá borrar tareas asignadas a él, el administrador puede borrar tareas
asignadas a otros usuarios.

**Editar tareas:**
Para editar tareas se deben cumplir las mismas condiciones que para borrar tareas con la diferencia de
solicitar los nuevos campos de la tarea, si es administrador se podra cambiar tanto el campo usuario como
el campo descripción, si no solo se podra editar la descripción.

-----------------------------------------
LÓGICA DE NEGOCIO
-----------------------------------------


**Usuarios:**

El username debera ser único.
La password debera estar hasheada.
Los roles deberán ser "USER" o "ADMIN"

**Tareas:**

El nombre debera ser único.
La descripción no debera superar los 100 caracteres.
El usuario debe existir en la base de datos
  
-----------------------------------------
EXCEPCIONES
-----------------------------------------

**NotFoundException -- 404**
En caso de no encontrar una tarea se lanzará esta excepción.

**UnauthorizedException -- 401**
En caso de intentar hacer login con un usuario inexistente, introducir una contraseña incorrecta o intentar acceder a
recursos reservados se lanzará esta excepción.

**InvalidInputException -- 400**
En caso de enviar datos no validos se lanzará esta excepción

-----------------------------------------
PRUEBAS
-----------------------------------------

**Registrar**

![Captura 1.PNG](Capturas/Captura%201.PNG)