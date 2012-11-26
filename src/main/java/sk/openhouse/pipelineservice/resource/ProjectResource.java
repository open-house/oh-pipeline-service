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

import com.sun.jersey.api.NotFoundException;

import sk.openhouse.pipelineservice.domain.request.ProjectRequest;
import sk.openhouse.pipelineservice.domain.response.ProjectResponse;
import sk.openhouse.pipelineservice.service.ProjectService;
import sk.openhouse.pipelineservice.util.XmlUtil;

@Component
@Path("/projects/{project: [a-zA-Z0-9-_]+}")
public class ProjectResource {

    private ProjectService projectService;
    private XmlUtil xmlUtil;

    public ProjectResource(ProjectService projectService, XmlUtil xmlUtil) {
        this.projectService = projectService;
        this.xmlUtil = xmlUtil;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getProject(@PathParam("project") String projectName) throws JAXBException {

        ProjectResponse projectResponse = projectService.getProject(projectName);
        // TODO throw custom exception in the service and map it, so it doesn't have to be caught here
        if (null == projectResponse) {
            throw new NotFoundException();
        }
        return xmlUtil.marshall(ProjectResponse.class, projectResponse);
    }

    @PUT
    public void addProject(@PathParam("project") String projectName) {

        ProjectRequest project = new ProjectRequest();
        project.setName(projectName);
        projectService.addProject(project);
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void updateProject(@PathParam("project") String projectName, ProjectRequest project) {
        projectService.updateProject(projectName, project);
    }

    @DELETE
    public void deleteProject(@PathParam("project") String projectName) {
        projectService.deleteProject(projectName);
    }
}
