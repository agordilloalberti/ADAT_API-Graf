package ADAT_API_GRAF.controller

import ADAT_API_GRAF.dto.TareaAddDTO
import ADAT_API_GRAF.dto.TareaDTO
import ADAT_API_GRAF.error.exception.InvalidInputException
import ADAT_API_GRAF.service.TareaService
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/Tareas")
class TareaController {

    @Autowired
    private lateinit var tareaService: TareaService

    @PostMapping("/insert")
    fun addTarea(
        authentication: Authentication,
        @RequestBody tareaAddDTO: TareaAddDTO
    ) : ResponseEntity<TareaDTO>{

        val tarea = tareaService.insertTarea(tareaAddDTO,authentication)

        return ResponseEntity(tarea, HttpStatus.CREATED)
    }

    @GetMapping("/get")
    fun getTareas(authentication : Authentication) : ResponseEntity<List<TareaDTO>> {

        print(authentication)

        val tareas = tareaService.getTareas(authentication)

        return ResponseEntity(tareas, HttpStatus.OK)
    }

    @PutMapping("/{name}")
    fun updateTarea(
        authentication: Authentication,
        @PathVariable name : String,
        @RequestBody tareaAddDTO: TareaAddDTO
    ) : ResponseEntity<TareaDTO>{

        val tarea = tareaService.updateTarea(name,tareaAddDTO,authentication)

        return ResponseEntity(tarea, HttpStatus.OK)
    }

    @PutMapping("/complete/{name}")
    fun completeTarea(
        authentication: Authentication,
        @PathVariable name : String
    ) : ResponseEntity<TareaDTO>{

        if (name.isBlank()){
            throw InvalidInputException("El nombre de la tarea es obligatorio")
        }

        val tarea = tareaService.completeTarea(name,authentication)

        return  ResponseEntity(tarea, HttpStatus.OK)
    }

    @DeleteMapping("/{name}")
    fun deleteTarea(
        authentication: Authentication,
        @PathVariable name : String
    ) : ResponseEntity<TareaDTO>{

        if (name.isBlank()){
            throw InvalidInputException("El nombre de la tarea es obligatorio")
        }

        val tarea = tareaService.deleteTarea(name,authentication)

        return  ResponseEntity(tarea, HttpStatus.OK)

    }

}