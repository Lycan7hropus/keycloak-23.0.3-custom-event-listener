//package com.identicum.keycloak
//
//import com.google.gson.Gson
//import com.identicum.http.HttpTools
//import org.apache.http.HttpEntity
//import org.apache.http.client.methods.HttpPost
//import org.apache.http.entity.ByteArrayEntity
//import org.apache.http.impl.client.CloseableHttpClient
//import org.apache.http.protocol.HTTP
//import org.jboss.logging.Logger
//import org.keycloak.events.Event
//import org.keycloak.events.admin.AdminEvent
//
//open  public class EventNotifier(
//    public val endpoint: String,
//    public val adminEndpoint: String,
//    private val client: CloseableHttpClient,
//    private val gson: Gson = Gson()
//) {
//
//
//    companion object {
//        @JvmStatic
//        private val logger: Logger = Logger.getLogger(EventNotifier::class.java)
//    }
//
//    fun sendEvent(event: Event) {
//        sendEventGeneric(endpoint, event).apply {
//            logger.infov("${event.type} event sent to $endpoint")
//        }
//    }
//
//    fun sendAdminEvent(event: AdminEvent) {
//        sendEventGeneric(adminEndpoint, event).apply {
//            logger.infov("Admin event sent to $adminEndpoint")
//        }
//    }
//
//
//    private fun sendEventGeneric(url: String, event: Any) {
//        val eventData = gson.toJson(event)
//        try {
//            val httpEntity: HttpEntity = ByteArrayEntity(eventData.toString().toByteArray())
//            val httpPost = HttpPost(url)
//            httpPost.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE)
//            httpPost.setHeader("Content-Type", "application/json")
//            httpPost.entity = httpEntity
//
//            val response = HttpTools.executeCall(client, httpPost)
//            HttpTools.stopOnError(response)
//            logger.infov("Response: ", response.getResponseAsJsonObject())
//            return
//        } catch (e: Exception) {
//            logger.error("Error while sending event: ${e.message}", e)
//            throw e
//        }
//    }
//    public fun close() {
//        client.close()
//    }
//}