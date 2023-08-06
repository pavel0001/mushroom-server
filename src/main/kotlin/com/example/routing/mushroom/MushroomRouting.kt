package com.example.routing.mushroom

import com.example.models.GetByIdReq
import com.example.models.mushroomAdd.MushroomAddReq
import com.example.models.mushroomAdd.MushroomAddResp
import com.example.repository.MushroomRepository
import com.example.utils.saveByteArray
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import java.io.File

fun Application.configureMushroomRouting() {

    routing {
        post("/") {
            call.respondText("Hello World!")
        }

        post("/mushroom/add") {
            val mushroom = call.receive<MushroomAddReq>()
            try {
                val imageFileName = mushroom.image.saveByteArray("files/")
                try {
                    MushroomRepository.saveMushroom(mushroom.toMushroom(imageFileName))
                    call.respond(HttpStatusCode.OK, MushroomAddResp(message = "success $imageFileName"))
                } catch (ex: java.lang.Exception) {
                    call.respond(HttpStatusCode.InternalServerError, MushroomAddResp(message = "database error"))
                }
            } catch (_: java.lang.Exception) {
                call.respond(HttpStatusCode.InternalServerError, MushroomAddResp(message = "image parsing error"))
            }
        }

        post("/mushroom/delete") {
            val mushroomId = call.receive<Long>()
            try {
                MushroomRepository.removeMushroom(mushroomId)
                call.respond(status = HttpStatusCode.OK, message = "success")
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

        staticFiles("/mushroom/images", File("files")) {
            preCompressed(CompressedFileType.BROTLI, CompressedFileType.GZIP)
            extensions("png", "jpg")
        }
    }
}