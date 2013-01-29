package sk.openhouse.automation.pipelineservice.resource;

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

import sk.openhouse.automation.pipelinedomain.domain.request.VersionRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.VersionResponse;
import sk.openhouse.automation.pipelineservice.service.VersionService;

/**
 * 
 * @author pete
 */
@Component
@Path(ResourceUtil.VERSION_PATH)
public class VersionResource {

    private final VersionService versionService;

    public VersionResource(VersionService versionService) {
        this.versionService = versionService;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public VersionResponse getVersion(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber) throws JAXBException {

        return versionService.getVersion(projectName, versionNumber);
    }

    @PUT
    public void addVersion(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber) {

        VersionRequest versionRequest = new VersionRequest();
        versionRequest.setVersionNumber(versionNumber);
        versionService.addVersion(projectName, versionRequest);
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void updateVersion(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber, 
            VersionRequest versionRequest) {

        versionService.updateVersion(projectName, versionNumber, versionRequest);
    }

    @DELETE
    public void deleteVersion(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber) {

        versionService.deleteVersion(projectName, versionNumber);
    }
}
