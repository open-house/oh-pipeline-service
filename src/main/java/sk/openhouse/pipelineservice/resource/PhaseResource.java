package sk.openhouse.pipelineservice.resource;

import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import sk.openhouse.pipelineservice.service.PhaseService;
import sk.openhouse.pipelineservice.service.VersionService;
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

    // TODO
}
