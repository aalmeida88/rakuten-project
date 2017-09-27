package com.rakutten.utils.api.exception;

import java.util.HashMap;
import java.util.Map;

public class DefaultErrorMessages {

    private static Map<Integer, String> messages = new HashMap<>();

    static {
        messages.put(300, "There are multiple options to choose from");
        messages.put(301, "The requested uri has permanently moved to a new location");
        messages.put(400, "Some of the data required by the service is not present or invalid");
        messages.put(403, "You do not have the necessary permissions to access this method");
        messages.put(404, "The server has not found anything matching the request uri");
        messages.put(410, "The server has not found anything matching the request uri and will never do, stop asking");
        messages.put(405, "The handler does not support this http method");
        messages.put(500, "A generic error has occurred on the server");
        messages.put(501, "The request uri points to a non implemented method");
        messages.put(503, "The server is currently unable to handle the request due to a temporary overloading or maintenance of the server");
    }

    public static String get(Integer code) {
        if (messages.containsKey(code)) {
            return messages.get(code);
        }

        return "An error with code " + code + " has ocurred";
    }

    public static Map<Integer, String> getMessages() {
        return messages;
    }
}
