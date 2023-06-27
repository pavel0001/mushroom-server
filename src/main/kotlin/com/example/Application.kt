package com.example

import com.example.routing.mushroom.configureMushroomRouting
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.request.*
import org.jetbrains.exposed.sql.Database
import org.slf4j.event.Level

fun main() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/mushroom",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "pz40tks"
    )
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    /* intercept(ApplicationCallPipeline.Fallback) {
         print("debugtag ${call.request}")
         if (call.isHandled) return@intercept
         val status = call.response.status() ?: HttpStatusCode.NotFound
         call.respond(status)
     }*/
    install(CallLogging) {
        level = Level.TRACE
        filter { call -> call.request.path().startsWith("/") }
    }
    install(ContentNegotiation) {
        json()
    }
    install(DefaultHeaders)
    install(CORS) {
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        allowHeader(HttpHeaders.ContentType)
    }
    configureMushroomRouting ()
}
