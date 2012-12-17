package sk.openhouse.automation.pipelineservice.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import sk.openhouse.automation.pipelineservice.domain.response.BuildPhasesResponse;
import sk.openhouse.automation.pipelineservice.service.BuildPhaseService;
import sk.openhouse.automation.pipelineservice.util.XmlUtil;

@Component
@Path(ResourceUtil.BUILD_PHASES_PATH)
public class BuildPhasesResource {

    private BuildPhaseService buildPhaseService;
    private XmlUtil xmlUtil;

    public BuildPhasesResource(BuildPhaseService buildPhaseService, XmlUtil xmlUtil) {
        this.buildPhaseService = buildPhaseService;
        this.xmlUtil = xmlUtil;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getBuildPhase(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber,
            @PathParam(ResourceUtil.BUILD_PARAM) int buildNumber) throws JAXBException {

        BuildPhasesResponse buildPhasesResponse = buildPhaseService.getBuildPhases(
                projectName, versionNumber, buildNumber);
        return xmlUtil.marshall(BuildPhasesResponse.class, buildPhasesResponse);
    }
}
