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
import sk.openhouse.automation.pipelineservice.util.XmlUtil;

@Component
@Path(ResourceUtil.PHASE_PATH)
public class PhaseResource {

    private PhaseService phaseService;
    private XmlUtil xmlUtil;

    public PhaseResource(PhaseService phaseService, XmlUtil xmlUtil) {
        this.phaseService = phaseService;
        this.xmlUtil = xmlUtil;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getPhase(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber,
            @PathParam(ResourceUtil.PHASE_PARAM) String phaseName) throws JAXBException {

        PhaseResponse phaseResponse = phaseService.getPhase(projectName, versionNumber, phaseName);
        return xmlUtil.marshall(PhaseResponse.class, phaseResponse);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void addPhase(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber,
            @PathParam(ResourceUtil.PHASE_PARAM) String phaseName,
            PhaseRequest phaseRequest) {

        phaseService.addPhase(projectName, versionNumber, phaseName, phaseRequest);
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
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
