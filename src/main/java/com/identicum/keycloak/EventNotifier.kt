package com.identicum.keycloak

import com.google.gson.Gson
import com.identicum.http.HttpTools
import org.apache.commons.codec.binary.Hex
import org.apache.http.HttpEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ByteArrayEntity
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.protocol.HTTP
import org.jboss.logging.Logger
import org.keycloak.events.Event
import org.keycloak.events.admin.AdminEvent
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

public open class EventNotifier(
    public val endpoint: String,
    val secretKey: String,
    public val adminEndpoint: String,
    private val client: CloseableHttpClient,
    private val gson: Gson = Gson()
) {


    companion object {
        @JvmStatic
        private val logger: Logger = Logger.getLogger(EventNotifier::class.java)
    }

    fun sendEvent(event: Event) {
        sendEventGeneric(endpoint, event).apply {
            logger.infov("${event.type} event sent to $endpoint")
        }
    }

    fun sendAdminEvent(event: AdminEvent) {
        sendEventGeneric(adminEndpoint, event).apply {
            logger.infov("Admin event sent to $adminEndpoint")
        }
    }


    private fun sendEventGeneric(url: String, event: Any) {
        val eventData = gson.toJson(event)
        try {
            val httpEntity: HttpEntity = ByteArrayEntity(eventData.toString().toByteArray())
            val httpPost = HttpPost(url)
            httpPost.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE)
            httpPost.setHeader("Content-Type", "application/json")


            val hmacSignature = generateHmacSignature(eventData, secretKey)
            httpPost.setHeader("X-HMAC-Signature", hmacSignature)

            httpPost.entity = httpEntity

            val response = HttpTools.executeCall(client, httpPost)
            HttpTools.stopOnError(response)
            logger.infov("Response: ", response.responseAsJsonObject)
            return
        } catch (e: Exception) {
            logger.error("Error while sending event: ${e.message}", e)
            throw e
        }
    }

    private fun generateHmacSignature(data: String, secretKey: String): String {
        val algorithm = "HmacSHA256"
        val secretKeySpec = SecretKeySpec(secretKey.toByteArray(), algorithm)
        val mac = Mac.getInstance(algorithm).apply {
            init(secretKeySpec)
        }
        val hmacBytes = mac.doFinal(data.toByteArray())
        return Hex.encodeHexString(hmacBytes)
    }

    public fun close() {
        client.close()
    }
}