package sk.openhouse.pipelineservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import sk.openhouse.pipelineservice.dao.ProjectReadDao;
import sk.openhouse.pipelineservice.dao.impl.ProjectWriteDaoImpl;
import sk.openhouse.pipelineservice.domain.request.ProjectRequest;
import sk.openhouse.pipelineservice.domain.response.ProjectDetailsResponse;
import sk.openhouse.pipelineservice.domain.response.ProjectResponse;
import sk.openhouse.pipelineservice.domain.response.ProjectsResponse;
import sk.openhouse.pipelineservice.domain.response.ResourceResponse;
import sk.openhouse.pipelineservice.domain.response.ResourcesResponse;
import sk.openhouse.pipelineservice.domain.response.VersionResponse;
import sk.openhouse.pipelineservice.service.ProjectService;
import sk.openhouse.pipelineservice.util.HttpUtil;

public class ProjectServiceImpl implements ProjectService {

    private HttpUtil httpUtil;
    private ProjectReadDao projectReadDao;
    private ProjectWriteDaoImpl projectWriteDao;

    public ProjectServiceImpl(HttpUtil httpUtil, ProjectReadDao projectReadDao, ProjectWriteDaoImpl projectWriteDao) {
        this.httpUtil = httpUtil;
        this.projectReadDao = projectReadDao;
        this.projectWriteDao = projectWriteDao;
    }

    @Override
    public ProjectsResponse getProjects() {

        List<ProjectResponse> projects = projectReadDao.getProjects();
        ProjectsResponse projectsResponse = new ProjectsResponse();

        if (null != projects) {
            projectsResponse.setProjects(projects);
        }

        for (ProjectResponse project : projects) {
            String projectName = project.getName();
            project.setResources(getProjectResources(projectName));
        }

        return projectsResponse;
    }

    @Override
    public ProjectDetailsResponse getProject(String name) {

        ProjectDetailsResponse projectDetailsResponse = projectReadDao.getProject(name);
        if (null != projectDetailsResponse) {
            projectDetailsResponse.getProject().setResources(getProjectDetailsResources(name));

            for (VersionResponse version : projectDetailsResponse.getVersions().getVersions()) {
                version.setResources(getVersionResources(name, version.getNumber()));
            }
        }

        return projectDetailsResponse;
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

        String projectURI = httpUtil.getProjectRelativeURI(projectName);

        List<ResourceResponse> projectDetailsResources = new ArrayList<ResourceResponse>();
        /* GET */
        projectDetailsResources.add(httpUtil.getResource(httpUtil.getProjectsRelativeURI(), "List of all projects"));
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
        projectResources.add(httpUtil.getResource(httpUtil.getProjectRelativeURI(projectName), "Project Details"));

        ResourcesResponse resourcesResponse = new ResourcesResponse();
        resourcesResponse.setResources(projectResources);
        return resourcesResponse;
    }

    private ResourcesResponse getVersionResources(String projectName, String versionNumber) {

        List<ResourceResponse> projectResources = new ArrayList<ResourceResponse>();
        /* GET */
        projectResources.add(httpUtil.getResource(httpUtil.getVersionRelativeURI(projectName, versionNumber), "Version Details"));

        ResourcesResponse resourcesResponse = new ResourcesResponse();
        resourcesResponse.setResources(projectResources);
        return resourcesResponse;
    }

}
