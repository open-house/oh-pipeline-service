package sk.openhouse.automation.pipelineservice.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import sk.openhouse.automation.pipelinedomain.domain.response.BuildPhasesResponse;
import sk.openhouse.automation.pipelineservice.service.BuildPhaseService;

/**
 * 
 * @author pete
 */
@Component
@Path(ResourceUtil.BUILD_PHASES_PATH)
public class BuildPhasesResource {

    private final BuildPhaseService buildPhaseService;

    public BuildPhasesResource(BuildPhaseService buildPhaseService) {
        this.buildPhaseService = buildPhaseService;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public BuildPhasesResponse getBuildPhase(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber,
            @PathParam(ResourceUtil.BUILD_PARAM) int buildNumber) throws JAXBException {

        return buildPhaseService.getBuildPhases(projectName, versionNumber, buildNumber);
    }
}
