package ADAT_API_GRAF.util

import ADAT_API_GRAF.dto.TareaDTO
import ADAT_API_GRAF.dto.UsuarioDTO
import ADAT_API_GRAF.model.Tarea
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
    fun userDTOToEntity(usuarioDTO: UsuarioDTO): Usuario{
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

    fun tareaDTOToEntity(tareaDTO: TareaDTO) : Tarea{
        return Tarea(
            null,
            tareaDTO.name,
            tareaDTO.descripcion,
            tareaDTO.completada,
            tareaDTO.usuario
                )
    }

    fun tareaEntityTODTO(tarea: Tarea) : TareaDTO{
        return TareaDTO(tarea.name,tarea.descripcion,tarea.completada,tarea.usuario)
    }

}