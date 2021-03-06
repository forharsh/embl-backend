package com.embl.backend.util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConstants {
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final int ACCESS_TOKEN_VALIDITY_SECONDS = 2 * 10 * 90;
    public static final String SECRET_KEY = "@S0Me!eNc0dEdKey&";
    public static final String SCOPES = "scopes";
    public static final String EMBL = "OCC";
    public static final String SWAGGER_BASE_PACKAGE = "com.embl.backend.controller";
    public static final String HEADER = "header";
    public static final String API_KEY_NAME = "JWT";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String SWAGGER_API_TITLE = "REST APIs related to Person Entity!!";
    public static final String SWAGGER_API_DESCRIPTION = "Swagger ui lists all apis related to Person Entity";
    public static final String SWAGGER_VERSION = "0.0.1-SNAPSHOT";
    public static final String UNAUTHORIZED = "Unauthorized";
}
