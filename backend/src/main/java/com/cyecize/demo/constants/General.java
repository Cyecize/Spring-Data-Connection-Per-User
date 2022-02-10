package com.cyecize.demo.constants;

import com.cyecize.demo.ConnectionPerSessionApplication;

import java.time.format.DateTimeFormatter;

public final class General {
    public static final String AUTHORIZATION_TOKEN = "Authorization-Token";
    public static final String INITIAL_FLYWAY_FILE_NAME = "V1.0__Init.sql";
    public static final String BASE_PACKAGE_NAME = ConnectionPerSessionApplication.class.getPackageName();
    public static final String CURRENT_USER_SESSION_ATTR_NAME = "currentUser";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy, hh:mm a");
}
