package com.identicum.keycloak


import com.identicum.keycloak.CustomEventListenerProviderFactory.Constants.API_CONNECTION_REQUEST_TIMEOUT_DEFAULT
import com.identicum.keycloak.CustomEventListenerProviderFactory.Constants.API_CONNECT_TIMEOUT_DEFAULT
import com.identicum.keycloak.CustomEventListenerProviderFactory.Constants.API_MAX_CONNECTIONS_DEFAULT
import com.identicum.keycloak.CustomEventListenerProviderFactory.Constants.API_SOCKET_TIMEOUT_DEFAULT
import org.apache.http.impl.client.HttpClients
import org.jboss.logging.Logger
import org.keycloak.Config
import org.keycloak.events.EventListenerProvider
import org.keycloak.events.EventListenerProviderFactory
import org.keycloak.models.KeycloakSession
import org.keycloak.models.KeycloakSessionFactory

public open class CustomEventListenerProviderFactory : EventListenerProviderFactory {
    companion object {
        @JvmStatic
        private val logger: Logger = Logger.getLogger(CustomEventListenerProviderFactory::class.java)
    }
    private lateinit var eventNotifier: EventNotifier
    private val id: String = "custom-event-listener"

    override fun create(keycloakSession: KeycloakSession?): EventListenerProvider {
        return CustomEventListenerProvider(keycloakSession, eventNotifier)
    }
    public object Constants {
        const val API_MAX_CONNECTIONS_DEFAULT: Int = 10
        const val API_CONNECTION_REQUEST_TIMEOUT_DEFAULT: Long = 2000
        const val API_CONNECT_TIMEOUT_DEFAULT: Long = 2000
        const val API_SOCKET_TIMEOUT_DEFAULT: Long = 5000
    }


    public override fun init(config: Config.Scope) {

        val endpoint: String = config.get("apiEndpoint").also { endpoint ->
            logger.info("Your endpoint is $endpoint")
        } ?: throw IllegalArgumentException("Endpoint is null, please check your configuration")
        val adminEndpoint: String = config.get("apiAdminEndpoint").also { adminEndpoint ->
            logger.info("Your admin endpoint is $adminEndpoint")
        } ?: endpoint

        logger.info("Keep your secrets!")

        val secretKey:String? =  config.get("secretKey").also {
            logger.info("Your secretKey endpoint is $it")
        }




        val maxConnections: Int = config.getInt("apiMaxConnections", API_MAX_CONNECTIONS_DEFAULT)
        val connectionRequestTimeout: Long =
            config.getLong("apiConnectionRequestTimeout", API_CONNECTION_REQUEST_TIMEOUT_DEFAULT)
        val connectTimeout: Long = config.getLong("apiConnectTimeout", API_CONNECT_TIMEOUT_DEFAULT)
        val socketTimeout: Long = config.getLong("apiSocketTimeout", API_SOCKET_TIMEOUT_DEFAULT)

        val client = HttpClients.custom().build()

        eventNotifier = EventNotifier(endpoint = endpoint, secretKey = secretKey, adminEndpoint = adminEndpoint, client = client)
    }

    public override fun postInit(p0: KeycloakSessionFactory?) {

    }

    public override fun close() {
    }

    public override fun getId(): String {
        return id
    }

}