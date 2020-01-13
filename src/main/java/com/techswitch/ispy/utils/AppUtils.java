package com.techswitch.ispy.utils;


import java.sql.Timestamp;

public class AppUtils {
    private static Timestamp timestamp;

    public static void log(String methodName, String logMessage) {
        timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("[" + timestamp.toString() + " - METHOD: " + methodName + "] " + logMessage);
    }

    public static void logCatchException(String methodName, String errorMessage, String exceptionMessage) {
        timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("[" + timestamp.toString() + " - METHOD: " + methodName + "] " + errorMessage);
        System.out.println("[" + timestamp.toString() + " - METHOD: " + methodName + "] " + "[EXCEPTION] - " + exceptionMessage);
    }
}
