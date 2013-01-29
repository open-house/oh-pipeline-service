package sk.openhouse.automation.pipelineservice.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import sk.openhouse.automation.pipelinedomain.domain.response.ProjectsResponse;
import sk.openhouse.automation.pipelineservice.service.ProjectService;

/**
 *
 * @author pete
 */
@Component
@Path(ResourceUtil.PROJECTS_PATH)
public class ProjectsResource {

    private final ProjectService projectService;

    public ProjectsResource(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ProjectsResponse getProjects() throws JAXBException {
        return projectService.getProjects();
    }
}
