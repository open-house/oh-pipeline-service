package sk.openhouse.automation.pipelineservice.resource.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import sk.openhouse.automation.pipelineservice.service.exception.BadRequestException;

/**
 * Maps BadRequestException to HTTP bad request response
 * 
 * @author pete
 */
@Component
@Provider
public class BadRequestMapper implements ExceptionMapper<BadRequestException> {

    @Override
    public Response toResponse(BadRequestException e) {

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(e.getMessage())
                .build();
    }
}
