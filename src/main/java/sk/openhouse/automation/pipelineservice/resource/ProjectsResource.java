package sk.openhouse.automation.pipelineservice.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import sk.openhouse.automation.pipelinedomain.domain.response.ProjectsResponse;
import sk.openhouse.automation.pipelineservice.service.ProjectService;
import sk.openhouse.automation.pipelineservice.util.XmlUtil;

/**
 * 
 * @author pete
 */
@Component
@Path(ResourceUtil.PROJECTS_PATH)
public class ProjectsResource {

    private final ProjectService projectService;
    private final XmlUtil xmlUtil;

    public ProjectsResource(ProjectService projectService, XmlUtil xmlUtil) {
        this.projectService = projectService;
        this.xmlUtil = xmlUtil;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getProjects() throws JAXBException {
        return xmlUtil.marshall(ProjectsResponse.class, projectService.getProjects());
    }
}
