package sk.openhouse.pipelineservice.resource;

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

import sk.openhouse.pipelineservice.domain.request.BuildRequest;
import sk.openhouse.pipelineservice.domain.response.BuildDetailsResponse;
import sk.openhouse.pipelineservice.service.BuildService;
import sk.openhouse.pipelineservice.util.XmlUtil;

@Component
@Path("/projects/{project: [a-zA-Z0-9-_]+}/versions/{version: [0-9\\.]+}/{build: [0-9]+}")
public class BuildResource {

    private BuildService buildService;
    private XmlUtil xmlUtil;

    public BuildResource(BuildService buildService, XmlUtil xmlUtil) {
        this.buildService = buildService;
        this.xmlUtil = xmlUtil;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getBuild(@PathParam("project") String projectName, 
            @PathParam("version") String versionNumber,
            @PathParam("build") int buildNumber) throws JAXBException {

        BuildDetailsResponse buildDetails = buildService.getBuild(projectName, versionNumber, buildNumber);
        return xmlUtil.marshall(BuildDetailsResponse.class, buildDetails);
    }

    @PUT
    public void addBuild(@PathParam("project") String projectName, 
            @PathParam("version") String versionNumber,
            @PathParam("build") int buildNumber) {

        BuildRequest buildRequest = new BuildRequest();
        buildRequest.setNumber(buildNumber);
        buildService.addBuild(projectName, versionNumber, buildRequest);
    }

    @POST
    public void updateBuild(@PathParam("project") String projectName, 
            @PathParam("version") String versionNumber,
            @PathParam("build") int buildNumber,
            BuildRequest build) {

        buildService.updateBuild(projectName, versionNumber, buildNumber, build);
    }

    @DELETE
    public void deleteBuild(@PathParam("project") String projectName, 
            @PathParam("version") String versionNumber,
            @PathParam("build") int buildNumber) {

        buildService.deleteBuild(projectName, versionNumber, buildNumber);
    }
}
