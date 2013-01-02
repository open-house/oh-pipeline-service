package sk.openhouse.automation.pipelineservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import sk.openhouse.automation.pipelineservice.dao.ProjectReadDao;
import sk.openhouse.automation.pipelineservice.dao.ProjectWriteDao;
import sk.openhouse.automation.pipelinedomain.domain.request.ProjectRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.ProjectResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.ProjectsResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.ResourceResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.ResourcesResponse;
import sk.openhouse.automation.pipelineservice.service.ProjectService;
import sk.openhouse.automation.pipelineservice.service.ResourceService;
import sk.openhouse.automation.pipelineservice.service.exception.NotFoundException;

public class ProjectServiceImpl implements ProjectService {

    private final ResourceService resourceService;
    private final ProjectReadDao projectReadDao;
    private final ProjectWriteDao projectWriteDao;

    public ProjectServiceImpl(ResourceService resourceService, ProjectReadDao projectReadDao, ProjectWriteDao projectWriteDao) {
        this.resourceService = resourceService;
        this.projectReadDao = projectReadDao;
        this.projectWriteDao = projectWriteDao;
    }

    @Override
    public ProjectsResponse getProjects() {

        ProjectsResponse projectsResponse = projectReadDao.getProjects();

        for (ProjectResponse project : projectsResponse.getProjects()) {
            String projectName = project.getName();
            project.setResources(getProjectResources(projectName));
        }

        return projectsResponse;
    }

    @Override
    public ProjectResponse getProject(String projectName) {

        ProjectResponse projectResponse = projectReadDao.getProject(projectName);
        if (null == projectResponse) {
            throw new NotFoundException(String.format("Project %s cannot be found.", projectName));
        }

        projectResponse.setResources(getProjectDetailsResources(projectName));
        return projectResponse;
    }

    @Override
    public void addProject(ProjectRequest project) {
        projectWriteDao.addProject(project);
    }

    @Override
    public void updateProject(String projectName, ProjectRequest project) {
        projectWriteDao.updateProject(projectName, project);
    }

    @Override
    public void deleteProject(String projectName) {
        projectWriteDao.deleteProject(projectName);
    }

    /**
     * Returns resources for a specific project
     */
    private ResourcesResponse getProjectDetailsResources(String projectName) {

        String projectURI = resourceService.getProjectURIString(projectName);
        String versionsURI = resourceService.getVersionsURIString(projectName);

        List<ResourceResponse> projectDetailsResources = new ArrayList<ResourceResponse>();
        /* GET */
        projectDetailsResources.add(resourceService.getResource(resourceService.getProjectsURIString(), "List of all projects"));
        /* GET */
        projectDetailsResources.add(resourceService.getResource(versionsURI, "List of all versions of this project"));
        /* PUT */
        projectDetailsResources.add(resourceService.getResource(projectURI, 
                "Insert new project, or overwride existing project", "PUT"));
        /* POST */
        projectDetailsResources.add(resourceService.getResource(projectURI, 
                "Update existing project", "POST"));
        /* DELETE */
        projectDetailsResources.add(resourceService.getResource(projectURI, 
                "Delete existing project", "DELETE"));

        ResourcesResponse resourcesResponse = new ResourcesResponse();
        resourcesResponse.setResources(projectDetailsResources);
        return resourcesResponse;
    }

    /**
     * Returns resources for a project
     */
    private ResourcesResponse getProjectResources(String projectName) {

        List<ResourceResponse> projectResources = new ArrayList<ResourceResponse>();
        /* GET */
        projectResources.add(resourceService.getResource(resourceService.getProjectURIString(projectName), "Project Details"));

        ResourcesResponse resourcesResponse = new ResourcesResponse();
        resourcesResponse.setResources(projectResources);
        return resourcesResponse;
    }
}
