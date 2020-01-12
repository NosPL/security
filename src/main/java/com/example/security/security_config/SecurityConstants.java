package com.example.security.security_config;

public final class SecurityConstants {

    public static final String LOGIN_URL = "/login";

    // Signing key for HS512 algorithm
    // You can use the page http://www.allkeysgenerator.com/ to generate all kinds of keys
    public static final String AUTHORIZATION_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";

    //JWT token defaults
    public static final String AUTHORIZATION = "Authorization";
    public static final String PASSWORD_CHANGE = "password-change";
    public static final String BEARER_ = "Bearer ";
    public static final String BASIC_ = "Basic ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "secure-api";
    public static final String TOKEN_AUDIENCE = "secure-app";

    public static final int EXPIRATION_TIME = 36000000;

    private SecurityConstants() {
    }
}
