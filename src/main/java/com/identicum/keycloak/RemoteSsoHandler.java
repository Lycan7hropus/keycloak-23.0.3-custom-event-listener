package com.identicum.keycloak;

import com.identicum.http.SimpleHttpResponse;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jboss.logging.Logger;

import static com.identicum.http.HttpTools.executeCall;
import static com.identicum.http.HttpTools.stopOnError;
import static jakarta.json.Json.createArrayBuilder;
import static jakarta.json.Json.createObjectBuilder;
import static org.apache.http.protocol.HTTP.CONN_DIRECTIVE;
import static org.apache.http.protocol.HTTP.CONN_KEEP_ALIVE;
import static org.jboss.logging.Logger.getLogger;


@Getter
@AllArgsConstructor
public class RemoteSsoHandler {

	private static final Logger logger = getLogger(RemoteSsoHandler.class);

	private CloseableHttpClient httpClient;
	private String endpoint;
	
	public JsonObject registerUser(String username, String realm) {

		if(endpoint==null){
			logger.infov("Your endpoint is null! It is now hardcoded to http://mmock:8083/api");
			endpoint = "http://mmock:8083/api";
		}

		logger.infov("!Registering user {0}", username);

		logger.infov("Creating http client with endpoint: {0}", endpoint);
		HttpPost httpPost = new HttpPost(this.endpoint);
		logger.infov("setting up the headers");
		httpPost.setHeader(CONN_DIRECTIVE, CONN_KEEP_ALIVE);
		httpPost.setHeader("Content-Type", "application/json");

		logger.infov("Creating json builder");
		JsonObjectBuilder builder = createObjectBuilder();
		logger.infov("setting up a json object");
		builder.add("user" , createObjectBuilder().add("loginname", createArrayBuilder().add(username).build()).build());

		logger.infov("building a json object");
		JsonObject requestJson = builder.build();
		logger.debugv("Setting create body as: {0}", requestJson.toString());
		HttpEntity httpEntity = new ByteArrayEntity(requestJson.toString().getBytes());
		httpPost.setEntity(httpEntity);

		logger.debugv("Executing call for user {0}", username);
		SimpleHttpResponse response = executeCall(this.httpClient, httpPost);
		logger.debugv("Checking response for user {0}", username);
		stopOnError(response);
		logger.debugv("Returning response for user {0}", username);
		return response.getResponseAsJsonObject();
	}

	public JsonObject sendEventString(String eventData) {
		if (endpoint == null) {
			logger.infov("Your endpoint is null! It is now hardcoded to http://mmock:8083/api");
			endpoint = "http://mmock:8083/api";
		}

		logger.infov("Sending event data to endpoint: {0}", endpoint);
		HttpPost httpPost = new HttpPost(this.endpoint);
		logger.infov("Setting up the headers");
		httpPost.setHeader(CONN_DIRECTIVE, CONN_KEEP_ALIVE);
		httpPost.setHeader("Content-Type", "text/plain");

		logger.infov("Setting event data body");
		HttpEntity httpEntity = new ByteArrayEntity(eventData.getBytes());
		httpPost.setEntity(httpEntity);

		logger.debugv("Executing call with event data: {0}", eventData);
		SimpleHttpResponse response = executeCall(this.httpClient, httpPost);
		logger.debugv("Checking response");
		stopOnError(response);
		logger.debugv("Returning response");
		return response.getResponseAsJsonObject();
	}


}
