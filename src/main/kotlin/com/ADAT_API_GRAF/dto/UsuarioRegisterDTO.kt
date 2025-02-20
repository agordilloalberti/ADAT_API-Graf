package com.AGS.ADAT_API.Graf.dto

import com.AGS.ADAT_API.Graf.model.Direccion

data class UsuarioRegisterDTO(
    val username: String,
    val password: String,
    val passwordRepeat: String,
    val name : String,
    val surname : String,
    val rol : String?,
    val direccion: Direccion
)
