package it.tasgroup.rest.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.inject.Named;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;


@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ObjectMapperResolver implements ContextResolver<ObjectMapper>
{
    private final ObjectMapper mapper;

    public ObjectMapperResolver()
    {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,true);
        mapper.configure(SerializationFeature.INDENT_OUTPUT,true);
    }

    @Override
    public ObjectMapper getContext(Class<?> type)
    {
        return mapper;
    }
}
