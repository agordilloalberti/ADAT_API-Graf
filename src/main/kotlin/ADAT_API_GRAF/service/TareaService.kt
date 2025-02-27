package ADAT_API_GRAF.service

import ADAT_API_GRAF.dto.TareaAddDTO
import ADAT_API_GRAF.dto.TareaAdminAddDTO
import ADAT_API_GRAF.dto.TareaDTO
import ADAT_API_GRAF.error.exception.InvalidInputException
import ADAT_API_GRAF.error.exception.NotFoundException
import ADAT_API_GRAF.error.exception.UnauthorizedException
import ADAT_API_GRAF.model.Tarea
import ADAT_API_GRAF.repository.TareaRepository
import ADAT_API_GRAF.repository.UsuarioRepository
import ADAT_API_GRAF.util.DTOMapper
import org.springframework.beans.factory.annotation.*
import org.springframework.stereotype.*
import org.springframework.security.core.Authentication
import kotlin.jvm.optionals.getOrNull

@Service
class TareaService {

    @Autowired
    private lateinit var tareaRepository: TareaRepository
    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    //Normal user functions
    fun getTareas(authentication: Authentication): List<TareaDTO> {
        val username = authentication.name

        val user = usuarioRepository.findByUsername(username).orElseThrow{
            NotFoundException("El usuario $username no existe")
        }

        val tareas = tareaRepository.findAll()
        val tareasResult = mutableListOf<TareaDTO>()

        for (tarea in tareas){
            if (tarea.usuario.username==username){
                tareasResult.add(DTOMapper.tareaEntityTODTO(tarea))
            }
        }

        return tareasResult.toList()
    }

    fun insertTarea(tarea: TareaAddDTO, authentication: Authentication) : TareaDTO{
        val user = usuarioRepository.findByUsername(authentication.name).orElseThrow {
            NotFoundException("El usuario ${authentication.name} no existe")
        }

        val tarea2 = tareaRepository.findByName(tarea.name).getOrNull()

        if (tarea2!=null){
            throw InvalidInputException("La tarea ${tarea.name} ya existe")
        }

        val tareaDTO = TareaDTO(tarea.name,tarea.descripcion,false,user)

        tareaRepository.insert(DTOMapper.tareaDTOToEntity(tareaDTO))

        return tareaDTO
    }

    fun updateTarea(name: String, tarea: TareaAddDTO, authentication: Authentication) : TareaDTO{
        val user = usuarioRepository.findByUsername(authentication.name).orElseThrow {
            NotFoundException("El usuario ${authentication.name} no existe")
        }

        val tareaFind = tareaRepository.findByName(name).orElseThrow {
            NotFoundException("La tarea $name no existe")
        }

        if (tareaFind.usuario.username!=user.username){
            throw UnauthorizedException("El usuario ${user.username} no puede editar las tareas de otro usuario")
        }

        val tareaUpdate = Tarea(tareaFind._id,tarea.name,tarea.descripcion,tareaFind.completada,tareaFind.usuario)


        tareaRepository.delete(tareaFind)
        tareaRepository.insert(tareaUpdate)

        return TareaDTO(tareaUpdate.name,tareaUpdate.descripcion,tareaUpdate.completada,tareaUpdate.usuario)
    }

    fun completeTarea(name: String, authentication: Authentication) : TareaDTO{
        val user = usuarioRepository.findByUsername(authentication.name).orElseThrow {
            NotFoundException("El usuario ${authentication.name} no existe")
        }

        val tareaFind = tareaRepository.findByName(name).orElseThrow {
            NotFoundException("La tarea $name no existe")
        }

        if (tareaFind.usuario.username!=user.username){
            throw UnauthorizedException("El usuario ${user.username} no puede editar las tareas de otro usuario")
        }

        val tareaUpdate = Tarea(tareaFind._id,tareaFind.name,tareaFind.descripcion,true,tareaFind.usuario)

        tareaRepository.delete(tareaFind)
        tareaRepository.insert(tareaUpdate)

        return TareaDTO(tareaUpdate.name,tareaUpdate.descripcion,tareaUpdate.completada,tareaUpdate.usuario)
    }

    fun deleteTarea(name: String, authentication: Authentication): TareaDTO{
        val user = usuarioRepository.findByUsername(authentication.name).orElseThrow {
            NotFoundException("El usuario ${authentication.name} no existe")
        }

        val tareaFind = tareaRepository.findByName(name).orElseThrow {
            NotFoundException("La tarea $name no existe")
        }

        if (tareaFind.usuario.username!=user.username){
            throw UnauthorizedException("El usuario ${user.username} no puede editar las tareas de otro usuario")
        }

        tareaRepository.delete(tareaFind)

        return TareaDTO(tareaFind.name,tareaFind.descripcion,tareaFind.completada,tareaFind.usuario)
    }

    //Admin functions

    fun getTareasAdmin(username: String,authentication: Authentication) : List<TareaDTO>{
        val admin = usuarioRepository.findByUsername(authentication.name).orElseThrow {
            NotFoundException("El usuario ${authentication.name} no existe")
        }

        val user = usuarioRepository.findByUsername(username).orElseThrow {
            NotFoundException("El usuario $username no existe")
        }

        val tareas = tareaRepository.findAll()
        val tareasResult = mutableListOf<TareaDTO>()

        for (tarea in tareas){
            if (tarea.usuario.username==username){
                tareasResult.add(DTOMapper.tareaEntityTODTO(tarea))
            }
        }

        return tareasResult.toList()
    }

    fun insetAdminTarea(tarea: TareaAdminAddDTO, authentication: Authentication) : TareaDTO?{
        val admin = usuarioRepository.findByUsername(authentication.name).orElseThrow {
            NotFoundException("El usuario ${authentication.name} no existe")
        }

        val user = usuarioRepository.findByUsername(tarea.usuario).orElseThrow {
            NotFoundException("El usuario ${authentication.name} no existe")
        }

        val tarea2 = tareaRepository.findByName(tarea.name).getOrNull()

        if (tarea2!=null){
            throw InvalidInputException("La tarea ${tarea.name} ya existe")
        }

        val tareaDTO = TareaDTO(tarea.name,tarea.descripcion,false,user)

        return tareaDTO
    }

    fun updateTareaAdmin(name: String, tarea: TareaAdminAddDTO, authentication: Authentication) : TareaDTO{
        val admin = usuarioRepository.findByUsername(authentication.name).orElseThrow {
            NotFoundException("El usuario ${authentication.name} no existe")
        }

        val user = usuarioRepository.findByUsername(tarea.usuario).orElseThrow {
            NotFoundException("El usuario ${tarea.usuario} no existe")
        }

        val tareaFind = tareaRepository.findByName(name).orElseThrow {
            NotFoundException("La tarea $name no existe")
        }

        val tareaUpdate = Tarea(tareaFind._id,tarea.name,tarea.descripcion,tareaFind.completada,user)

        tareaRepository.delete(tareaFind)
        tareaRepository.insert(tareaUpdate)

        return TareaDTO(tareaUpdate.name,tareaUpdate.descripcion,tareaUpdate.completada,tareaUpdate.usuario)
    }

    fun completeTareaAdmin(name: String, authentication: Authentication): TareaDTO{
        val admin = usuarioRepository.findByUsername(authentication.name).orElseThrow {
            NotFoundException("El usuario ${authentication.name} no existe")
        }

        val tareaFind = tareaRepository.findByName(name).orElseThrow {
            NotFoundException("La tarea $name no existe")
        }

        val tareaUpdate = Tarea(tareaFind._id,tareaFind.name,tareaFind.descripcion,true,tareaFind.usuario)

        tareaRepository.delete(tareaFind)
        tareaRepository.insert(tareaUpdate)

        return TareaDTO(tareaUpdate.name,tareaUpdate.descripcion,tareaUpdate.completada,tareaUpdate.usuario)
    }
}