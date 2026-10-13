
package com.example

import com.example.config.DatabaseFactory
import com.example.data.ArticlesTable
import com.example.data.UsersTable
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import kotlin.test.Test
import kotlin.test.assertEquals

class ServerTest {

    @Test
    fun `test root endpoint`() = testApplication {
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

        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
    }
}
