package com.AGS.ADAT_API.Graf.dto

import com.AGS.ADAT_API.Graf.model.Direccion

data class UsuarioDTO(
    val username : String,
    val name : String,
    val surname : String,
    val rol : String?,
    val direccion: Direccion
)
