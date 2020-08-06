package it.tasgroup.idp.rs.util;

import it.tasgroup.iris.dto.exception.I18NException;
import it.tasgroup.iris.dto.exception.InvalidInputException;
import it.tasgroup.rest.utils.MessageInfo;
import it.tasgroup.rest.utils.exceptions.RestExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Arrays;

@Provider
public class I18nExceptionMapper implements ExceptionMapper<I18NException> {
    private static final Logger LOGGER = LogManager.getLogger(I18nExceptionMapper.class);

    @Override
    public Response toResponse(I18NException e) {
        LOGGER.error(e);
        Response.ResponseBuilder responseBuilder = RestExceptionUtils.builderWithCORS();
//        String message = e.getI18NMessageKey();
        String message = e.getMessage();

        // eventually move i a specific mapper
        if (e instanceof InvalidInputException) {
            InvalidInputException invalidInputException = (InvalidInputException) e;
            Object[] i18NMessageParameters = invalidInputException.getI18NMessageParameters();
            String paramString = Arrays.toString(i18NMessageParameters);
            message = message + " : " + paramString;
        }
        MessageInfo messageInfo = new MessageInfo(message);
        messageInfo.setErrorCode(e.getErrorCode().getChiave());
        messageInfo.setErrorDescription(e.getErrorCode().getDescrizione());
        messageInfo.setSeverity(e.getSeverityLevel());
        responseBuilder.status(getHttpStatusCode()).entity(messageInfo); // TODO map appropriate code
        return responseBuilder.build();
    }


    Response.Status getHttpStatusCode() {
        return Response.Status.BAD_REQUEST;
    }
}
