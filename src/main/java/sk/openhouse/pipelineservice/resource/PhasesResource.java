package sk.openhouse.pipelineservice.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import sk.openhouse.pipelineservice.domain.response.PhasesResponse;
import sk.openhouse.pipelineservice.service.PhaseService;
import sk.openhouse.pipelineservice.util.XmlUtil;

@Component
@Path("/projects/{project: [a-zA-Z0-9-_]+}/versions/{version: [0-9\\.]+}/phases")
public class PhasesResource {

    private PhaseService phaseService;
    private XmlUtil xmlUtil;

    public PhasesResource(PhaseService phaseService, XmlUtil xmlUtil) {
        this.phaseService = phaseService;
        this.xmlUtil = xmlUtil;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getVersions(@PathParam("project") String projectName, 
            @PathParam("version") String versionNumber) throws JAXBException {

        PhasesResponse phasesResponse = phaseService.getPhases(projectName, versionNumber);
        return xmlUtil.marshall(PhasesResponse.class, phasesResponse);
    }
}
