package sk.openhouse.automation.pipelineservice.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import sk.openhouse.automation.pipelinedomain.domain.response.PhasesResponse;
import sk.openhouse.automation.pipelineservice.service.PhaseService;

/**
 *
 * @author pete
 */
@Component
@Path(ResourceUtil.PHASES_PATH)
public class PhasesResource {

    private final PhaseService phaseService;

    public PhasesResource(PhaseService phaseService) {
        this.phaseService = phaseService;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public PhasesResponse getVersions(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber) throws JAXBException {

        return phaseService.getPhases(projectName, versionNumber);
    }
}
