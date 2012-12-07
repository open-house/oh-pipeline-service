package sk.openhouse.pipelineservice.resource;

import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import sk.openhouse.pipelineservice.service.BuildPhaseService;
import sk.openhouse.pipelineservice.util.XmlUtil;

@Component
@Path(ResourceUtil.BUILD_PHASES_PATH)
public class BuildPhasesResource {

    private BuildPhaseService buildPhaseService;
    private XmlUtil xmlUtil;

    public BuildPhasesResource(BuildPhaseService buildPhaseService, XmlUtil xmlUtil) {
        this.buildPhaseService = buildPhaseService;
        this.xmlUtil = xmlUtil;
    }
}
