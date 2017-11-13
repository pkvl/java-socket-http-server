package com.anroypaul.javasockethttpserver.helpers;

public class ParseHelper {
    public static boolean isStringInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String[] removeSpaces(String part) {
        return part.split(" ");
    }

    public static String[] removeSpaces(String[] parts, int index) {
        return parts[index].split(" ");
    }

    public static String extractIndex(String[] parts, int index) {
        return parts[index];
    }
}
