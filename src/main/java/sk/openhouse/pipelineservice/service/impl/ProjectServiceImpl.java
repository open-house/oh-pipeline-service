package sk.openhouse.pipelineservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import sk.openhouse.pipelineservice.dao.ProjectReadDao;
import sk.openhouse.pipelineservice.dao.ProjectWriteDao;
import sk.openhouse.pipelineservice.domain.request.ProjectRequest;
import sk.openhouse.pipelineservice.domain.response.ProjectResponse;
import sk.openhouse.pipelineservice.domain.response.ProjectsResponse;
import sk.openhouse.pipelineservice.domain.response.ResourceResponse;
import sk.openhouse.pipelineservice.domain.response.ResourcesResponse;
import sk.openhouse.pipelineservice.service.ProjectService;
import sk.openhouse.pipelineservice.util.HttpUtil;

public class ProjectServiceImpl implements ProjectService {

    private HttpUtil httpUtil;
    private ProjectReadDao projectReadDao;
    private ProjectWriteDao projectWriteDao;

    public ProjectServiceImpl(HttpUtil httpUtil, ProjectReadDao projectReadDao, ProjectWriteDao projectWriteDao) {
        this.httpUtil = httpUtil;
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
    public ProjectResponse getProject(String name) {

        ProjectResponse projectResponse = projectReadDao.getProject(name);
        if (null != projectResponse) {
            projectResponse.setResources(getProjectDetailsResources(name));
        }

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

        String projectURI = httpUtil.getProjectURIString(projectName);
        String versionsURI = httpUtil.getVersionsURIString(projectName);

        List<ResourceResponse> projectDetailsResources = new ArrayList<ResourceResponse>();
        /* GET */
        projectDetailsResources.add(httpUtil.getResource(httpUtil.getProjectsURIString(), "List of all projects"));
        /* GET */
        projectDetailsResources.add(httpUtil.getResource(versionsURI, "List of all versions of this project"));
        /* PUT */
        projectDetailsResources.add(httpUtil.getResource(projectURI, 
                "Insert new project, or overwride existing project", "PUT"));
        /* POST */
        projectDetailsResources.add(httpUtil.getResource(projectURI, 
                "Update existing project", "POST"));
        /* DELETE */
        projectDetailsResources.add(httpUtil.getResource(projectURI, 
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
        projectResources.add(httpUtil.getResource(httpUtil.getProjectURIString(projectName), "Project Details"));

        ResourcesResponse resourcesResponse = new ResourcesResponse();
        resourcesResponse.setResources(projectResources);
        return resourcesResponse;
    }
}
