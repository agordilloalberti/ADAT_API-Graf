package ADAT_API_GRAF.dto

import ADAT_API_GRAF.model.Direccion

data class UsuarioDTO(
    val username : String,
    val name : String,
    val surname : String,
    val rol : String?,
    val direccion: Direccion
)
