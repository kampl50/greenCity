package com.greencity.middleware.config;

public class ConfigConstants {

    public static final String DATE_PATTERN = "dd/MM/yyyy_HH:mm";
    public static final String WEB_SOCKET_URL = "/greenCityWebSocket";
    public static final String DESTINATION_PREFIX = "/app";
    public static final String BROKER_URL = "/";
    public static final String WEB_SOCKET_MEASUREMENT_URL = "/measurements/";
    public static final String WEB_SOCKET_DEVICES_URL = "/devices";
    public static final String ALL_ORIGINS = "*";
    public static final String DEVICE_IP_ADDRESS = "192.168.0.199";
    public static final int TIME_TO_REMOVE_DEVICE = 1;
    public static final int DEVICE_CHECKING_INTERVAL = 1000;

    private ConfigConstants() {
    }
}
