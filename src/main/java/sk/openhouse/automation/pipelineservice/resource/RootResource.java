package sk.openhouse.automation.pipelineservice.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import sk.openhouse.automation.pipelinedomain.domain.response.LinksResponse;
import sk.openhouse.automation.pipelineservice.service.RootResourceService;
import sk.openhouse.automation.pipelineservice.util.XmlUtil;

/**
 *
 * @author pete
 */
@Component
@Path(ResourceUtil.ROOT_PATH)
public class RootResource {

    private final XmlUtil xmlUtil;
    private final RootResourceService rootResourceService;

    public RootResource(RootResourceService rootResourceService, XmlUtil xmlUtil) {
        this.rootResourceService = rootResourceService;
        this.xmlUtil = xmlUtil;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getResources() throws JAXBException {
        return xmlUtil.marshall(LinksResponse.class, rootResourceService.getRootLinks());
    }
}
