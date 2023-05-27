package com.example.routing.mushroom

import com.example.models.GetByIdReq
import com.example.models.Mushroom
import com.example.repository.MushroomRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement

fun Application.configureMushroomRouting() {

    routing {
        post("/") {
            call.respondText("Hello World!")
        }

        post("/mushroom/add") {
            val mushroom = call.receive<Mushroom>()
            val result = MushroomRepository.saveMushroom(mushroom)
            call.respond(result)
        }

        post("/mushroom/delete") {
            val mushroomId = call.receive<Long>()
            try {
                val result = MushroomRepository.removeMushroom(mushroomId)
                call.respond(result)
            } catch (e: Exception) {
                call.run { respond(status = HttpStatusCode.BadRequest, message = "Mushroom delete error!") }
            }
        }

        post("/mushroom/get") {
            val id = call.receive<GetByIdReq>().id
            val result = MushroomRepository.getById(id)
            val json = Json.encodeToString(result)
            call.respondText(json, contentType = ContentType.Application.Json)
        }

        post("/mushroom/getAll") {
            val result = MushroomRepository.getAll()
            val rootObject = buildJsonObject {
                put("mushrooms", Json.encodeToJsonElement(result))
            }
            call.respond(rootObject)
        }
    }
}