package ADAT_API_GRAF.repository

import ADAT_API_GRAF.model.Tarea
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface TareaRepository : MongoRepository<Tarea, String> {

    fun findByName(name: String) : Optional<Tarea>
}