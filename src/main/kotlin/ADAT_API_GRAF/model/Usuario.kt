package ADAT_API_GRAF.model

import ADAT_API_GRAF.model.Direccion
import org.bson.codecs.pojo.annotations.BsonId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.index.Indexed

@Document("Usuarios")
data class Usuario(
    @BsonId
    val _id : String?,
    @Indexed(unique = true)
    val username: String,
    val password: String,
    val name : String?,
    val surname : String?,
    val direccion: Direccion,
    val roles: String = "USER"
)