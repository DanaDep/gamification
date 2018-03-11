package com.dep.gamification.util;

public abstract class PathHelper {
    private static String host = "http://localhost";
    private static String port = "8080";
    private static String earnedPointsRequestPath = "/earnedpointsrequest";
    private static String spentPointsRequestPath = "/spentpointsrequest";
    private static String responsePath = "/response";

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        PathHelper.host = host;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        PathHelper.port = port;
    }

    public static String getEarnedPointsRequestPath() {
        return earnedPointsRequestPath;
    }

    public static void setEarnedPointsRequestPath(String earnedPointsRequestPath) {
        PathHelper.earnedPointsRequestPath = earnedPointsRequestPath;
    }

    public static String getSpentPointsRequestPath() {
        return spentPointsRequestPath;
    }

    public static void setSpentPointsRequestPath(String spentPointsRequestPath) {
        PathHelper.spentPointsRequestPath = spentPointsRequestPath;
    }

    public static String getResponsePath() {
        return responsePath;
    }

    public static void setResponsePath(String responsePath) {
        PathHelper.responsePath = responsePath;
    }
}
