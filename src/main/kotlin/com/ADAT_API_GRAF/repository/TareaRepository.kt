package com.AGS.ADAT_API.Graf.repository

import com.AGS.ADAT_API.Graf.model.Tarea
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface TareaRepository : MongoRepository<Tarea, String> {

    fun findByName(name: String) : Optional<Tarea>
}