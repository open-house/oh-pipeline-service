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
import sk.openhouse.automation.pipelineservice.util.XmlUtil;

@Component
@Path(ResourceUtil.PHASES_PATH)
public class PhasesResource {

    private PhaseService phaseService;
    private XmlUtil xmlUtil;

    public PhasesResource(PhaseService phaseService, XmlUtil xmlUtil) {
        this.phaseService = phaseService;
        this.xmlUtil = xmlUtil;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getVersions(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber) throws JAXBException {

        PhasesResponse phasesResponse = phaseService.getPhases(projectName, versionNumber);
        return xmlUtil.marshall(PhasesResponse.class, phasesResponse);
    }
}
