package ADAT_API_GRAF.model

import org.bson.codecs.pojo.annotations.BsonId
import org.springframework.data.mongodb.core.mapping.Document

@Document("Tarea")
data class Tarea(
    @BsonId
    val _id : String?,
    val name: String,
    val descripcion : String?,
    val completada : Boolean,
    val usuario: Usuario
)