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

import sk.openhouse.automation.pipelinedomain.domain.request.PhaseRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.PhaseResponse;
import sk.openhouse.automation.pipelineservice.service.PhaseService;

/**
 * 
 * @author pete
 */
@Component
@Path(ResourceUtil.PHASE_PATH)
public class PhaseResource {

    private final PhaseService phaseService;

    public PhaseResource(PhaseService phaseService) {
        this.phaseService = phaseService;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public PhaseResponse getPhase(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber,
            @PathParam(ResourceUtil.PHASE_PARAM) String phaseName) throws JAXBException {

        return phaseService.getPhase(projectName, versionNumber, phaseName);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void addPhase(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber,
            @PathParam(ResourceUtil.PHASE_PARAM) String phaseName,
            PhaseRequest phaseRequest) {

        phaseService.addPhase(projectName, versionNumber, phaseName, phaseRequest);
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updatePhase(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber,
            @PathParam(ResourceUtil.PHASE_PARAM) String phaseName,
            PhaseRequest phaseRequest) {

        phaseService.updatePhase(projectName, versionNumber, phaseName, phaseRequest);
    }

    @DELETE
    public void deletePhase(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber,
            @PathParam(ResourceUtil.PHASE_PARAM) String phaseName) {

        phaseService.deletePhase(projectName, versionNumber, phaseName);
    }
}
