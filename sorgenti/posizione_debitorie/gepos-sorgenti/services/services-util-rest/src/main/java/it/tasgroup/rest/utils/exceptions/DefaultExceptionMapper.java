package it.tasgroup.rest.utils.exceptions;


import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static it.tasgroup.rest.utils.exceptions.RestExceptionUtils.builderWithCORS;


@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {
    final static Logger logger = LogManager.getLogger(DefaultExceptionMapper.class);

    @Override
    public Response toResponse(Throwable t) {
        Response.ResponseBuilder responseBuilder = builderWithCORS();
        if (t.getCause() instanceof WebApplicationException) {
            WebApplicationException webex = (WebApplicationException) t.getCause();
            return responseBuilder.status(webex.getResponse().getStatus()).build();
        }

        logger.error("Mapping exception : ", t);

        ErrorMessage errorMessage = new ErrorMessage("An error occured on server, see attached debug informations for details : " + t.getMessage(), ExceptionUtils.getFullStackTrace(t));
        int internalServerError = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        responseBuilder.status(internalServerError).entity(errorMessage);
        return responseBuilder.build();
    }


}
