#ADAT_API-GRAF

Entidad/Documento usuario:

Entidad principal la cual tendra un nombre de usuario como clave primaria, una contraseña hasheada, un nombre y apellidos,
una dirección, la cual es un documento y un rols, que puede ser "ADMIN" o "USER".

  String username: PK
  String password: Hashed
  String name
  String surname
  Direccion direccion : Entity
  String roles : "ADMIN" || "USER"


Entidad/Documento direccion:

Esta es una entidad son colección propia dentro de mongo ya que su uso es el de almacenar la dirección del usuario.
Esta estara mapedada unicamente dentro de los usuarios y no tiene restricciones

  String calle
  String num
  String municipio
  String proovincia
  String cp
  
