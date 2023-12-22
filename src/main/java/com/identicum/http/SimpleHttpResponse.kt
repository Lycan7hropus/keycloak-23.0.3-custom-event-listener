package com.identicum.http

import jakarta.json.Json
import jakarta.json.JsonArray
import jakarta.json.JsonObject
import java.io.StringReader


class SimpleHttpResponse {
    private val status = 0
    private val response: String? = null

    val isSuccess: Boolean
        get() = status == 200

    val responseAsJsonObject: JsonObject?
        get() {
            if (this.response != null) {
                val reader = Json.createReader(StringReader(response))
                return reader.readObject()
            }
            return null
        }

    val responseAsJsonArray: JsonArray?
        get() {
            if (this.response != null) {
                val reader = Json.createReader(StringReader(response))
                return reader.readArray()
            }
            return null
        }
}
