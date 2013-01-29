package sk.openhouse.automation.pipelineservice.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import sk.openhouse.automation.pipelinedomain.domain.response.VersionsResponse;
import sk.openhouse.automation.pipelineservice.service.VersionService;

/**
 *
 * @author pete
 */
@Component
@Path(ResourceUtil.VERSIONS_PATH)
public class VersionsResource {

    private final VersionService versionService;

    public VersionsResource(VersionService versionService) {
        this.versionService = versionService;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public VersionsResponse getVersions(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName) throws JAXBException {
        return versionService.getVersions(projectName);
    }
}
