package ADAT_API_GRAF.controller

import ADAT_API_GRAF.dto.TareaAdminAddDTO
import ADAT_API_GRAF.dto.TareaDTO
import ADAT_API_GRAF.error.exception.InvalidInputException
import ADAT_API_GRAF.service.TareaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/TareasAdmin")
class TareaAdminController {

    @Autowired
    private lateinit var tareaService: TareaService

    @GetMapping("/get")
    fun getTareasAdminSelf(
        authentication: Authentication,
    ) : ResponseEntity<List<TareaDTO>> {

        val tareas = tareaService.getTareas(authentication)

        return ResponseEntity(tareas, HttpStatus.OK)
    }

    @GetMapping("/getAll")
    fun getAllTareas(
        authentication: Authentication
    ) : ResponseEntity<List<TareaDTO>>{

        val tareas = tareaService.getAllTareas(authentication)

        return ResponseEntity(tareas, HttpStatus.OK)
    }

    @GetMapping("/{username}")
    fun getTareasAdmin(
        authentication: Authentication,
        @PathVariable username : String
    ) : ResponseEntity<List<TareaDTO>> {

        if (username.isBlank()){
            throw InvalidInputException("El nombre de usuario es obligatorio")
        }

        val tareas = tareaService.getTareasAdmin(username,authentication)

        return ResponseEntity(tareas, HttpStatus.OK)
    }

    @PostMapping("/add")
    fun adminAddTarea(
        authentication: Authentication,
        @RequestBody tareaAdminAddDTO : TareaAdminAddDTO
    ) : ResponseEntity<TareaDTO>?{

        println("Hols")
        if (tareaAdminAddDTO.usuario.isBlank()){
            tareaAdminAddDTO.usuario=authentication.name
        }

        val tarea = tareaService.insetAdminTarea(tareaAdminAddDTO,authentication)

        println(tarea)

        return ResponseEntity(tarea, HttpStatus.CREATED)
    }

    @PutMapping("/{tareaName}")
    fun updateTareaAdmin(
        authentication: Authentication,
        @PathVariable tareaName : String,
        @RequestBody tareaAdminAddDTO: TareaAdminAddDTO
    ) : ResponseEntity<TareaDTO>{

        if (tareaName.isBlank()){
            throw InvalidInputException("El nombre de la tarea es obligatorio")
        }

        if (tareaAdminAddDTO.usuario.isBlank()){
            tareaAdminAddDTO.usuario=authentication.name
        }

        val tarea = tareaService.updateTareaAdmin(tareaName,tareaAdminAddDTO,authentication)

        return ResponseEntity(tarea, HttpStatus.OK)
    }

    @PutMapping("/complete/{tareaName}")
    fun completeTareaAdmin(
        authentication: Authentication,
        @PathVariable tareaName : String
    ) : ResponseEntity<TareaDTO>{
        if (tareaName.isBlank()){
            throw InvalidInputException("El nombre de la tarea es obligatorio")
        }

        val tarea = tareaService.completeTareaAdmin(tareaName,authentication)

        return  ResponseEntity(tarea, HttpStatus.OK)
    }

    @PutMapping("/uncomplete/{tareaName}")
    fun uncompleteTareaAdmin(
        authentication: Authentication,
        @PathVariable tareaName : String
    ) : ResponseEntity<TareaDTO>{
        if (tareaName.isBlank()){
            throw InvalidInputException("El nombre de la tarea es obligatorio")
        }

        val tarea = tareaService.uncompleteTareaAdmin(tareaName,authentication)

        return  ResponseEntity(tarea, HttpStatus.OK)
    }

    @DeleteMapping("/{name}")
    fun deleteTareaAdmin(
        authentication: Authentication,
        @PathVariable name : String
    ) : ResponseEntity<TareaDTO>{

        if (name.isBlank()){
            throw InvalidInputException("El nombre de la tarea es obligatorio")
        }

        val tarea = tareaService.deleteTarea(name, authentication)

        return  ResponseEntity(tarea, HttpStatus.OK)

    }
}