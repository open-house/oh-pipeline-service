package sk.openhouse.automation.pipelineservice.resource.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import sk.openhouse.automation.pipelineservice.service.exception.ConflictException;

@Component
@Provider
public class ConflictMapper implements ExceptionMapper<ConflictException> {

    @Override
    public Response toResponse(ConflictException e) {

        return Response.status(Response.Status.CONFLICT)
                .entity(e.getMessage())
                .build();
    }
}
