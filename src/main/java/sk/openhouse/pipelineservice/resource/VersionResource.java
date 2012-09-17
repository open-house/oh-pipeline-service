package sk.openhouse.pipelineservice.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import sk.openhouse.pipelineservice.domain.response.VersionDetailsResponse;
import sk.openhouse.pipelineservice.service.VersionService;
import sk.openhouse.pipelineservice.util.XmlUtil;

@Component
@Path("/projects/{project: [a-zA-Z0-9-_]+}/{version: [0-9\\.]+}")
public class VersionResource {

    private VersionService versionService;
    private XmlUtil xmlUtil;

    public VersionResource(VersionService versionService, XmlUtil xmlUtil) {
        this.versionService = versionService;
        this.xmlUtil = xmlUtil;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getProject(@PathParam("project") String projectName, 
            @PathParam("version") String versionNumber) throws JAXBException {

        VersionDetailsResponse versionDetailsResponse = versionService.getVersion(projectName, versionNumber);
        return xmlUtil.marshall(VersionDetailsResponse.class, versionDetailsResponse);
    }
}
