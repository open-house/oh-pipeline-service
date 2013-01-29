package sk.openhouse.automation.pipelineservice.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import sk.openhouse.automation.pipelinedomain.domain.request.StateRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildPhaseResponse;
import sk.openhouse.automation.pipelineservice.service.BuildPhaseService;

/**
 * 
 * @author pete
 */
@Component
@Path(ResourceUtil.BUILD_PHASE_PATH)
public class BuildPhaseResource {

    private final BuildPhaseService buildPhaseService;

    public BuildPhaseResource(BuildPhaseService buildPhaseService) {
        this.buildPhaseService = buildPhaseService;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public BuildPhaseResponse getBuildPhase(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber,
            @PathParam(ResourceUtil.BUILD_PARAM) int buildNumber,
            @PathParam(ResourceUtil.PHASE_PARAM) String phaseName) throws JAXBException {

        return buildPhaseService.getBuildPhase(projectName, versionNumber, buildNumber, phaseName);
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void addBuildState(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber,
            @PathParam(ResourceUtil.BUILD_PARAM) int buildNumber,
            @PathParam(ResourceUtil.PHASE_PARAM) String phaseName,
            StateRequest stateRequest) {

        buildPhaseService.addState(projectName, versionNumber, buildNumber, phaseName, stateRequest);
    }
}
