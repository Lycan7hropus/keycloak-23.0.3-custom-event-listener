//package com.identicum.keycloak;
//
//import com.google.gson.Gson;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.jboss.logging.Logger;
//import org.keycloak.Config;
//import org.keycloak.events.EventListenerProvider;
//import org.keycloak.events.EventListenerProviderFactory;
//import org.keycloak.models.KeycloakSession;
//import org.keycloak.models.KeycloakSessionFactory;
//
//public class CustomEventListenerProviderFactoryJava implements EventListenerProviderFactory {
//
//	private static final Logger logger = Logger.getLogger(CustomEventListenerProviderFactoryJava.class);
//
//	private EventNotifier eventNotifier;
//
//    private static final String id = "custom-event-listener";
//
//	// Constants
//	public static final int API_MAX_CONNECTIONS_DEFAULT = 10;
//	public static final long API_CONNECTION_REQUEST_TIMEOUT_DEFAULT = 2000L;
//	public static final long API_CONNECT_TIMEOUT_DEFAULT = 2000L;
//	public static final long API_SOCKET_TIMEOUT_DEFAULT = 5000L;
//
//	@Override
//	public EventListenerProvider create(KeycloakSession keycloakSession) {
//		return new CustomEventListenerProvider(keycloakSession, eventNotifier);
//	}
//
//	@Override
//	public void init(Config.Scope config) {
//
//        String endpoint = config.get("apiEndpoint");
//		if (endpoint == null) {
//			throw new IllegalArgumentException("Endpoint is null, please check your configuration");
//		}
//		logger.info("Your endpoint is " + endpoint);
//
//		String adminEndpoint = config.get("apiAdminEndpoint");
//		if (adminEndpoint == null) {
//			adminEndpoint = endpoint;
//		}
//		logger.info("Your admin endpoint is " + adminEndpoint);
//
//		int maxConnections = config.getInt("apiMaxConnections", API_MAX_CONNECTIONS_DEFAULT);
//		long connectionRequestTimeout = config.getLong("apiConnectionRequestTimeout", API_CONNECTION_REQUEST_TIMEOUT_DEFAULT);
//		long connectTimeout = config.getLong("apiConnectTimeout", API_CONNECT_TIMEOUT_DEFAULT);
//		long socketTimeout = config.getLong("apiSocketTimeout", API_SOCKET_TIMEOUT_DEFAULT);
//
//
//		CloseableHttpClient client = HttpClients.custom().build();
//
//
//		Gson gson = new Gson(); // Create a new Gson instance
//		this.eventNotifier = new EventNotifier(endpoint, adminEndpoint, client, gson);
//	}
//
//	@Override
//	public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
//		// Implement post-initialization logic if required
//	}
//
//	@Override
//	public void close() {
//		// Implement close logic if required
//	}
//
//	@Override
//	public String getId() {
//		return id;
//	}
//}
