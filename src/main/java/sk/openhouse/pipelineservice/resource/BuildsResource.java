package sk.openhouse.pipelineservice.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import sk.openhouse.pipelineservice.domain.response.BuildsResponse;
import sk.openhouse.pipelineservice.service.BuildService;
import sk.openhouse.pipelineservice.util.XmlUtil;

@Component
@Path("/projects/{project: [a-zA-Z0-9-_]+}/versions/{version: [0-9\\.]+}/builds")
public class BuildsResource {

    private BuildService buildService;
    private XmlUtil xmlUtil;

    public BuildsResource(BuildService buildService, XmlUtil xmlUtil) {
        this.buildService = buildService;
        this.xmlUtil = xmlUtil;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getBuild(@PathParam("project") String projectName, 
            @PathParam("version") String versionNumber) throws JAXBException {

        BuildsResponse builds = buildService.getBuilds(projectName, versionNumber);
        return xmlUtil.marshall(BuildsResponse.class, builds);
    }
}