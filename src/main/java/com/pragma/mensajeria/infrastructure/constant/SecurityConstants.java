package com.pragma.mensajeria.infrastructure.constant;

public final class SecurityConstants {

    private SecurityConstants() {
        throw new AssertionError("Cannot instantiate SecurityConstants");
    }

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String JWT_HEADER = "Authorization";
    public static final String BEARER_AUTH_SCHEME = "bearer";
    public static final String JWT_TOKEN_TYPE = "JWT";

    public static final String ROLE_PREFIX = "ROLE_";

    public static final String TWILIO_ACCOUNT_SID_KEY = "twilio.account-sid";
    public static final String TWILIO_AUTH_TOKEN_KEY = "twilio.auth-token";
    public static final String TWILIO_MESSAGING_SERVICE_SID_KEY = "twilio.messaging-service-sid";
}
