package sk.openhouse.automation.pipelineservice.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import sk.openhouse.automation.pipelinedomain.domain.response.LinksResponse;
import sk.openhouse.automation.pipelineservice.service.SchemaService;

/**
 *
 * @author pete
 */
@Component
@Path(ResourceUtil.SCHEMA_PATH)
public class SchemaResource {

    private final SchemaService schemaService;

    public SchemaResource(SchemaService schemaService) {
        this.schemaService = schemaService;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public LinksResponse getSchemaRequest() {
        return schemaService.getSchemaLinks();
    }
}
