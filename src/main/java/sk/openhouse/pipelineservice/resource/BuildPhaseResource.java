package sk.openhouse.pipelineservice.resource;

import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import sk.openhouse.pipelineservice.service.BuildPhaseService;

@Component
@Path("/projects/{project: [a-zA-Z0-9-_]+}/versions/{version: [0-9\\.]+}/builds/{build: [0-9]+}")
public class BuildPhaseResource {

    private BuildPhaseService buildPhaseService;

    public BuildPhaseResource(BuildPhaseService buildPhaseService) {
        this.buildPhaseService = buildPhaseService;
    }

}
