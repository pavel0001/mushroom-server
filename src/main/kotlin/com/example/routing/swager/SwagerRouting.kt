package com.example.routing.swager

import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Application.configureSwagerRouting() {
    routing {
        swaggerUI(path = "swager", swaggerFile = "src/main/resources/openapi/documentation.yaml")
    }
}