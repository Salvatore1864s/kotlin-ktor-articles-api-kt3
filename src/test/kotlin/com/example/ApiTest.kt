
package com.example

import com.example.config.DatabaseFactory
import com.example.data.ArticlesTable
import com.example.data.UsersTable
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ApiTest {

    private fun testApp(
        testBlock: suspend ApplicationTestBuilder.() -> Unit
    ) = testApplication {
        application {
            DatabaseFactory.init()

            transaction {
                SchemaUtils.create(ArticlesTable, UsersTable)
            }

            configureSerialization()
            configureMonitoring()
            configureSecurity()
            configureRouting()
        }

        testBlock()
    }

    @Test
    fun `GET articles returns OK`() = testApp {
        val response = client.get("/articles")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `POST articles without token returns Unauthorized`() = testApp {
        val response = client.post("/articles") {
            contentType(ContentType.Application.Json)
            setBody("""{"title":"Test","content":"Test content"}""")
        }

        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun `GET missing route returns Not Found`() = testApp {
        val response = client.get("/not-existing")
        assertEquals(HttpStatusCode.NotFound, response.status)
    }

    @Test
    fun `GET article with invalid id returns Bad Request`() = testApp {
        val response = client.get("/articles/abc")
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }
}
