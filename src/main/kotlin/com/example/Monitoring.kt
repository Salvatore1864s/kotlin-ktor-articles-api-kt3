package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.slf4j.event.Level

fun Application.configureMonitoring() {
    install(CallLogging) {
        level = Level.INFO
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.application.environment.log.error(
                "Ошибка при обработке запроса",
                cause
            )

            if (!call.response.isCommitted) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("message" to "Внутренняя ошибка сервера")
                )
            }
        }

        status(HttpStatusCode.NotFound) { call, _ ->
            call.respond(
                HttpStatusCode.NotFound,
                mapOf("message" to "Маршрут не найден")
            )
        }
    }
}
