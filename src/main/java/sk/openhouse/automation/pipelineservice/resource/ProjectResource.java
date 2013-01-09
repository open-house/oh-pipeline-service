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

import sk.openhouse.automation.pipelinedomain.domain.request.ProjectRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.ProjectResponse;
import sk.openhouse.automation.pipelineservice.service.ProjectService;
import sk.openhouse.automation.pipelineservice.util.XmlUtil;

/**
 * 
 * @author pete
 */
@Component
@Path(ResourceUtil.PROJECT_PATH)
public class ProjectResource {

    private final ProjectService projectService;
    private final XmlUtil xmlUtil;

    public ProjectResource(ProjectService projectService, XmlUtil xmlUtil) {
        this.projectService = projectService;
        this.xmlUtil = xmlUtil;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getProject(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName) throws JAXBException {

        ProjectResponse projectResponse = projectService.getProject(projectName);
        return xmlUtil.marshall(ProjectResponse.class, projectResponse);
    }

    @PUT
    public void addProject(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName) {

        ProjectRequest project = new ProjectRequest();
        project.setName(projectName);
        projectService.addProject(project);
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void updateProject(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, ProjectRequest project) {
        projectService.updateProject(projectName, project);
    }

    @DELETE
    public void deleteProject(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName) {
        projectService.deleteProject(projectName);
    }
}
