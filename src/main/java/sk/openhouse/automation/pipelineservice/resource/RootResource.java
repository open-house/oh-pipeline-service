package sk.openhouse.automation.pipelineservice.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.stereotype.Component;

/**
 * Main root resource
 *
 * @author pete
 */
@Component
@Path("")
public class RootResource {
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getResources() {
        return "<test>test</test>";
    }
}
