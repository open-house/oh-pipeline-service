package sk.openhouse.automation.pipelineservice.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import sk.openhouse.automation.pipelinedomain.domain.response.LinksResponse;
import sk.openhouse.automation.pipelineservice.service.RootResourceService;

/**
 *
 * @author pete
 */
@Component
@Path(ResourceUtil.ROOT_PATH)
public class RootResource {

    private final RootResourceService rootResourceService;

    public RootResource(RootResourceService rootResourceService) {
        this.rootResourceService = rootResourceService;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public LinksResponse getResources() throws JAXBException {
        return rootResourceService.getRootLinks();
    }
}
