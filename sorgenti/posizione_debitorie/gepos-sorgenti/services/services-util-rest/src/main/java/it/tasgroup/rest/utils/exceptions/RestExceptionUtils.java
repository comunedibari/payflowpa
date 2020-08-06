package it.tasgroup.rest.utils.exceptions;

import it.tasgroup.rest.utils.MessageInfo;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.ws.rs.core.Response;

/**
 *
 */
public class RestExceptionUtils {
    public static Response.ResponseBuilder builderWithCORS() {
        Response.ResponseBuilder responseBuilder = Response.serverError();
        responseBuilder.header("Access-Control-Allow-Origin", "*");
        responseBuilder.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, auth-token");
        responseBuilder.header("Access-Control-Allow-Credentials", "true");
        responseBuilder.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        responseBuilder.header("Access-Control-Max-Age", "1209600");
        return responseBuilder;
    }

    public static MessageInfo getErrorMessage(Throwable t, boolean debugInfo) {
        String message = t.getMessage();
        MessageInfo messageInfo = new MessageInfo(message == null ? t.getClass().toString() : message);
        if (debugInfo) {
            messageInfo.setDebugInfo(ExceptionUtils.getFullStackTrace(t));
        }
        return messageInfo;
    }

}
