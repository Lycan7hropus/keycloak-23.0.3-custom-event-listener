package com.identicum.keycloak

import org.keycloak.events.Event
import org.keycloak.events.admin.AdminEvent
import org.keycloak.http.HttpResponse


internal interface HttpClientInterface {
    fun sendEvent(event: Event?): HttpResponse?
    fun sendAdminEvent(event: AdminEvent?): HttpResponse?
    fun close()
}
