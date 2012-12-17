package sk.openhouse.automation.pipelineservice.resource;

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

import sk.openhouse.automation.pipelineservice.domain.request.BuildRequest;
import sk.openhouse.automation.pipelineservice.domain.response.BuildResponse;
import sk.openhouse.automation.pipelineservice.service.BuildService;
import sk.openhouse.automation.pipelineservice.util.XmlUtil;

@Component
@Path(ResourceUtil.BUILD_PATH)
public class BuildResource {

    private BuildService buildService;
    private XmlUtil xmlUtil;

    public BuildResource(BuildService buildService, XmlUtil xmlUtil) {
        this.buildService = buildService;
        this.xmlUtil = xmlUtil;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getBuild(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber,
            @PathParam(ResourceUtil.BUILD_PARAM) int buildNumber) throws JAXBException {

        BuildResponse buildResponse = buildService.getBuild(projectName, versionNumber, buildNumber);
        return xmlUtil.marshall(BuildResponse.class, buildResponse);
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
