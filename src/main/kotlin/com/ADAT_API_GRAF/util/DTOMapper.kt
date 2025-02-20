package com.AGS.ADAT_API.Graf.util

import com.AGS.ADAT_API.Graf.dto.UsuarioRegisterDTO
import com.AGS.ADAT_API.Graf.model.Usuario

object DTOMapper {
    /*
    val _id : String?,
    val username: String,
    val password: String,
    val name : String?,
    val surname : String?,
    val direccion: Direccion,
    val roles: String = "USER"
    */
    fun userDTOToEntity(usuarioDTO: UsuarioRegisterDTO): Usuario{
        return Usuario(
            null,
            usuarioDTO.username,
            usuarioDTO.password,
            usuarioDTO.name,
            usuarioDTO.surname,
            usuarioDTO.direccion,
            usuarioDTO.rol!!,
        )
    }

}