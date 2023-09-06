package com.example

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ApplicationTest {

    @Test
    fun testHello() = testApplication {
        application {

        }
        client.get("mushroom/getAll").apply {
            assertEquals(HttpStatusCode.NotFound, this.status)
            assertTrue(true)
        }
        assertTrue(true)
    }
}