package com.rakutten.utils.api;

import java.util.List;

public class ApiError {
    private int code;
    private String message;
    private List<String> causes;
    private String stacktrace;

    public ApiError() {
    }

    public ApiError(int code, String message) {
        this(code, message, null);
    }

    public ApiError(int code, String message, List<String> causes) {
        this(code, message, causes, null);
    }

    public ApiError(int code, String message, List<String> causes, String stacktrace) {
        this.code = code;
        this.message = message;
        this.causes = causes;
        this.stacktrace = stacktrace;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getCauses() {
        return this.causes;
    }

    public void setCauses(List<String> causes) {
        this.causes = causes;
    }

    public String getStacktrace() {
        return this.stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ApiError -- code=[");
        builder.append(this.code);
        builder.append("], message=[");
        builder.append(this.message);
        builder.append("], causes=[");
        builder.append(this.causes);
        builder.append("], stacktrace=[");
        builder.append(this.stacktrace);
        builder.append("], tracking=[");
        builder.append("]");
        return builder.toString();
    }
}