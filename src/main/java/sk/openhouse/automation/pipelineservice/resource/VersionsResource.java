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
import sk.openhouse.automation.pipelineservice.util.XmlUtil;

@Component
@Path(ResourceUtil.VERSIONS_PATH)
public class VersionsResource {

    private VersionService versionService;
    private XmlUtil xmlUtil;

    public VersionsResource(VersionService versionService, XmlUtil xmlUtil) {
        this.versionService = versionService;
        this.xmlUtil = xmlUtil;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getVersions(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName) throws JAXBException {

        VersionsResponse versionsResponse = versionService.getVersions(projectName);
        return xmlUtil.marshall(VersionsResponse.class, versionsResponse);
    }
}
