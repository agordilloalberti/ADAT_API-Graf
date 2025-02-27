package ADAT_API_GRAF.dto

import ADAT_API_GRAF.model.Direccion

data class UsuarioDTO(
    val username : String,
    val password : String,
    val name : String,
    val surname : String,
    val direccion: Direccion,
    val rol : String?
)
