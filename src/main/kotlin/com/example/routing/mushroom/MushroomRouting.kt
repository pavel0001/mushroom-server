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
import com.example.routing.mushroom.update.MushroomUpdateReq
import com.example.routing.mushroom.update.MushroomUpdateResp
import com.example.utils.removeImage
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
            try {
                val mushroom = call.receive<MushroomAddReq>()
                if (mushroom.name == null || mushroom.description == null || mushroom.lat == null || mushroom.lon == null) {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        MushroomAddResp(
                            isSuccess = false,
                            error = BaseError.defaultError("name, description, lat and lon should not be null")
                        ),
                    )
                } else {
                    val imageFileName = mushroom.image?.let {
                        if (it.isEmpty()) {
                            null
                        } else {
                            it.saveByteArray("files/")
                        }
                    }

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

        post("/mushroom/update") {
            val mushroom = call.receive<MushroomUpdateReq>()
            try {
                mushroom.image?.let {
                    if (it.isNotBlank()) {
                        val imageFileName = it.saveByteArray("files/")
                        mushroom.updateImage(imageFileName)
                        val existedMushroom = MushroomRepository.getById(mushroom.id).toMushroom()
                        removeImage("files/", existedMushroom.image.orEmpty())
                    }
                }
                try {
                    MushroomRepository.updateMushroom(mushroom)
                    call.respond(
                        HttpStatusCode.OK, MushroomUpdateResp(
                            isSuccess = true
                        )
                    )
                } catch (ex: java.lang.Exception) {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        MushroomUpdateResp(
                            isSuccess = false,
                            error = BaseError.fromException(ex)
                        ),
                    )
                }
            } catch (ex: java.lang.Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    MushroomUpdateResp(
                        isSuccess = false,
                        error = BaseError.fromException(ex)
                    ),
                )
            }
        }

        post("/mushroom/delete") {
            val mushroomDeleteReq = call.receive<MushroomDeleteReq>()
            try {
                val existedMushroom = MushroomRepository.getById(mushroomDeleteReq.id).toMushroom()
                MushroomRepository.removeMushroom(mushroomDeleteReq.id)
                existedMushroom.image.let { mushroomImage ->
                    if (mushroomImage != null && mushroomImage != "placeholder.png") {
                        removeImage("files/", mushroomImage)
                    }
                }
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