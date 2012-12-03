package sk.openhouse.pipelineservice.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import sk.openhouse.pipelineservice.domain.request.VersionRequest;
import sk.openhouse.pipelineservice.domain.response.VersionResponse;
import sk.openhouse.pipelineservice.service.VersionService;
import sk.openhouse.pipelineservice.util.XmlUtil;

@Component
@Path("/projects/{project: [a-zA-Z0-9-_]+}/versions/{version: [0-9\\.]+}")
public class VersionResource {

    private VersionService versionService;
    private XmlUtil xmlUtil;

    public VersionResource(VersionService versionService, XmlUtil xmlUtil) {
        this.versionService = versionService;
        this.xmlUtil = xmlUtil;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getVersion(@PathParam("project") String projectName, 
            @PathParam("version") String versionNumber) throws JAXBException {

        VersionResponse versionResponse = versionService.getVersion(projectName, versionNumber);
        return xmlUtil.marshall(VersionResponse.class, versionResponse);
    }

    @PUT
    public void addVersion(@PathParam("project") String projectName, @PathParam("version") String versionNumber) {

        VersionRequest versionRequest = new VersionRequest();
        versionRequest.setVersionNumber(versionNumber);
        versionService.addVersion(projectName, versionRequest);
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void updateVersion(@PathParam("project") String projectName, 
            @PathParam("version") String versionNumber, 
            VersionRequest versionRequest) {

        versionService.updateVersion(projectName, versionNumber, versionRequest);
    }

    @DELETE
    public void deleteVersion(@PathParam("project") String projectName, @PathParam("version") String versionNumber) {
        versionService.deleteVersion(projectName, versionNumber);
    }
}
