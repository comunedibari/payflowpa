package it.tasgroup.rest.utils.exceptions;

import java.io.Serializable;

public class ErrorMessage implements Serializable {
    private String message;
    private String debugInfo;

    public ErrorMessage() {
    }

    public ErrorMessage(String message) {
        this.message = message;
    }

    public ErrorMessage(String message, String debugInfo) {
        this.message = message;
        this.debugInfo = debugInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }
}
