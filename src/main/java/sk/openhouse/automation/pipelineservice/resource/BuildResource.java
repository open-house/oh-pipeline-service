package sk.openhouse.automation.pipelineservice.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import sk.openhouse.automation.pipelinedomain.domain.request.BuildRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildResponse;
import sk.openhouse.automation.pipelineservice.service.BuildService;

/**
 * 
 * @author pete
 */
@Component
@Path(ResourceUtil.BUILD_PATH)
public class BuildResource {

    private final BuildService buildService;

    public BuildResource(BuildService buildService) {
        this.buildService = buildService;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public BuildResponse getBuild(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber,
            @PathParam(ResourceUtil.BUILD_PARAM) int buildNumber) throws JAXBException {

        return buildService.getBuild(projectName, versionNumber, buildNumber);
    }

    @PUT
    public void addBuild(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber,
            @PathParam(ResourceUtil.BUILD_PARAM) int buildNumber) {

        BuildRequest buildRequest = new BuildRequest();
        buildRequest.setNumber(buildNumber);
        buildService.addBuild(projectName, versionNumber, buildRequest);
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateBuild(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber,
            @PathParam(ResourceUtil.BUILD_PARAM) int buildNumber,
            BuildRequest build) {

        buildService.updateBuild(projectName, versionNumber, buildNumber, build);
    }

    @DELETE
    public void deleteBuild(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber,
            @PathParam(ResourceUtil.BUILD_PARAM) int buildNumber) {

        buildService.deleteBuild(projectName, versionNumber, buildNumber);
    }
}
