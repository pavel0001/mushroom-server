package com.example.routing.mushroom

import com.example.models.BaseError
import com.example.repository.MushroomRepository
import com.example.routing.mushroom.add.MushroomAddReq
import com.example.routing.mushroom.add.MushroomAddResp
import com.example.routing.mushroom.delete.MushroomDeleteReq
import com.example.routing.mushroom.delete.MushroomDeleteResp
import com.example.routing.mushroom.get.MushroomGetReq
import com.example.routing.mushroom.get.MushroomGetResp
import com.example.routing.mushroom.getAll.MushroomGetAllResp
import com.example.utils.saveByteArray
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureMushroomRouting() {

    routing {

        post("/mushroom/add") {
            val mushroom = call.receive<MushroomAddReq>()
            try {
                val imageFileName = mushroom.image.saveByteArray("files/")
                try {
                    MushroomRepository.saveMushroom(mushroom.toMushroom(imageFileName))
                    call.respond(
                        HttpStatusCode.OK, MushroomAddResp(
                            isSuccess = true,
                            fileName = imageFileName
                        )
                    )
                } catch (ex: java.lang.Exception) {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        MushroomAddResp(
                            isSuccess = false,
                            error = BaseError.fromException(ex)
                        ),
                    )
                }
            } catch (ex: java.lang.Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    MushroomAddResp(
                        isSuccess = false,
                        error = BaseError.fromException(ex)
                    ),
                )
            }
        }

        post("/mushroom/delete") {
            val mushroomDeleteReq = call.receive<MushroomDeleteReq>()
            try {
                MushroomRepository.removeMushroom(mushroomDeleteReq.id)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = MushroomDeleteResp(
                        isSuccess = true
                    ),
                )
            } catch (e: Exception) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = MushroomDeleteResp(
                        isSuccess = false,
                        error = BaseError.fromException(e)
                    ),
                )
            }
        }

        post("/mushroom/get") {
            try {
                val id = call.receive<MushroomGetReq>().id
                val result = MushroomRepository.getById(id).toMushroom()
                call.respond(
                    status = HttpStatusCode.OK, message = MushroomGetResp(
                        isSuccess = true,
                        mushroom = result
                    )
                )
            } catch (ex: java.lang.Exception) {
                call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = MushroomGetResp(
                        isSuccess = false,
                        error = BaseError.fromException(ex)
                    ),
                )
            }
        }

        post("/mushroom/getAll") {
            try {
                val result = MushroomRepository.getAll().map { it.toMushroom() }
                call.respond(
                    status = HttpStatusCode.OK,
                    message = MushroomGetAllResp(mushrooms = result)
                )
            } catch (ex: java.lang.Exception) {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = MushroomGetAllResp(error = BaseError.fromException(ex))
                )
            }
        }

        staticFiles("/mushroom/images", File("files")) {
            preCompressed(CompressedFileType.BROTLI, CompressedFileType.GZIP)
            extensions("png", "jpg")
        }
    }
}