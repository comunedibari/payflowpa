package it.tasgroup.rest.utils.exceptions;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    private static final Logger LOGGER = LogManager.getLogger(NotFoundExceptionMapper.class);

    @Override
    public Response toResponse(NotFoundException notFoundExc) {
        Response.ResponseBuilder responseBuilder = RestExceptionUtils.builderWithCORS();
        responseBuilder.status(Response.Status.NOT_FOUND);
        return responseBuilder.build();
    }
}
