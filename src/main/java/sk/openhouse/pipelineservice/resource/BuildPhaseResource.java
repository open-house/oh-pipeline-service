package sk.openhouse.pipelineservice.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import sk.openhouse.pipelineservice.service.BuildPhaseService;
import sk.openhouse.pipelineservice.util.XmlUtil;

@Component
@Path(ResourceUtil.BUILD_PHASE_PATH)
public class BuildPhaseResource {

    private BuildPhaseService buildPhaseService;
    private XmlUtil xmlUtil;

    public BuildPhaseResource(BuildPhaseService buildPhaseService, XmlUtil xmlUtil) {
        this.buildPhaseService = buildPhaseService;
        this.xmlUtil = xmlUtil;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getBuildPhase(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber,
            @PathParam(ResourceUtil.BUILD_PARAM) int buildNumber,
            @PathParam(ResourceUtil.PHASE_PARAM) String phase) throws JAXBException {

        // TODO
        return null;
    }
}
