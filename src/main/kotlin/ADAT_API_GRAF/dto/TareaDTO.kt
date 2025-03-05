package ADAT_API_GRAF.dto

import ADAT_API_GRAF.model.Usuario

data class TareaDTO(
    val name : String,
    val descripcion : String?,
    val completada : Boolean = false,
    val usuario : String
)