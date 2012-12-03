package sk.openhouse.pipelineservice.resource;

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

import sk.openhouse.pipelineservice.domain.request.PhaseRequest;
import sk.openhouse.pipelineservice.domain.response.PhaseResponse;
import sk.openhouse.pipelineservice.service.PhaseService;
import sk.openhouse.pipelineservice.util.XmlUtil;

@Component
@Path("/projects/{project: [a-zA-Z0-9-_]+}/versions/{version: [0-9\\.]+}/phases/{phase: [a-zA-Z0-9-_]+}")
public class PhaseResource {

    private PhaseService phaseService;
    private XmlUtil xmlUtil;

    public PhaseResource(PhaseService phaseService, XmlUtil xmlUtil) {
        this.phaseService = phaseService;
        this.xmlUtil = xmlUtil;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getPhase(@PathParam("project") String projectName, 
            @PathParam("version") String versionNumber,
            @PathParam("phase") String phaseName) throws JAXBException {

        PhaseResponse phaseResponse = phaseService.getPhase(projectName, versionNumber, phaseName);
        return xmlUtil.marshall(PhaseResponse.class, phaseResponse);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void addPhase(@PathParam("project") String projectName, 
            @PathParam("version") String versionNumber,
            @PathParam("phase") String phaseName,
            PhaseRequest phaseRequest) {

        phaseService.addPhase(projectName, versionNumber, phaseName, phaseRequest);
    }

    @POST
    public void updatePhase(@PathParam("project") String projectName, 
            @PathParam("version") String versionNumber,
            @PathParam("phase") String phaseName,
            PhaseRequest phaseRequest) {

        phaseService.updatePhase(projectName, versionNumber, phaseName, phaseRequest);
    }

    @DELETE
    public void deletePhase(@PathParam("project") String projectName, 
            @PathParam("version") String versionNumber,
            @PathParam("phase") String phaseName) {

        phaseService.deletePhase(projectName, versionNumber, phaseName);
    }
}
