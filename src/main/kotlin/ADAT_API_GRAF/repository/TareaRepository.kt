package ADAT_API_GRAF.repository

import ADAT_API_GRAF.model.Tarea
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TareaRepository : MongoRepository<Tarea, String> {

    fun findByName(name: String) : Optional<Tarea>
}