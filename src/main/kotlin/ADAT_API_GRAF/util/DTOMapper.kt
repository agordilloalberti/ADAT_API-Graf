package ADAT_API_GRAF.util

import ADAT_API_GRAF.dto.UsuarioRegisterDTO
import ADAT_API_GRAF.model.Usuario

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