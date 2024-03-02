# Bazuj na oficjalnym obrazie Keycloak
FROM quay.io/keycloak/keycloak:23.0.3

# Kopiuj zbudowany artefakt JAR z katalogu target do katalogu deployments Keycloak
COPY ./target/keycloak-custom-listener.jar /opt/keycloak/providers/custom-event-listener.jar
