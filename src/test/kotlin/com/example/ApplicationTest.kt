package com.example

import com.example.utils.removeImage
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {

        }
        client.get("/").apply {
            val result = removeImage("files/", "png8f40aa74-f10b-44ff-a292-cbdafba99414.png")
            assertEquals(result, true)
        }
    }
}