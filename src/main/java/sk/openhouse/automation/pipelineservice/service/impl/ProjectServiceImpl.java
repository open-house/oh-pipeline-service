package sk.openhouse.automation.pipelineservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import sk.openhouse.automation.pipelineservice.dao.ProjectReadDao;
import sk.openhouse.automation.pipelineservice.dao.ProjectWriteDao;
import sk.openhouse.automation.pipelinedomain.domain.request.ProjectRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.LinkResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.LinksResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.ProjectResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.ProjectsResponse;
import sk.openhouse.automation.pipelineservice.service.LinkService;
import sk.openhouse.automation.pipelineservice.service.ProjectService;
import sk.openhouse.automation.pipelineservice.service.exception.NotFoundException;

public class ProjectServiceImpl implements ProjectService {

    private final LinkService linkService;
    private final ProjectReadDao projectReadDao;
    private final ProjectWriteDao projectWriteDao;

    public ProjectServiceImpl(LinkService linkService, ProjectReadDao projectReadDao, ProjectWriteDao projectWriteDao) {

        this.linkService = linkService;
        this.projectReadDao = projectReadDao;
        this.projectWriteDao = projectWriteDao;
    }

    @Override
    public ProjectsResponse getProjects() {

        ProjectsResponse projectsResponse = projectReadDao.getProjects();
        projectsResponse.setHref(linkService.getProjectUriTemplate());
        projectsResponse.setMethod("PUT");
        projectsResponse.setDescription("adds new project");

        for (ProjectResponse project : projectsResponse.getProjects()) {
            project.setLinks(getProjectsLinks(project));
        }
        return projectsResponse;
    }

    @Override
    public ProjectResponse getProject(String projectName) {

        ProjectResponse projectResponse = projectReadDao.getProject(projectName);
        if (null == projectResponse) {
            throw new NotFoundException(String.format("Project %s cannot be found.", projectName));
        }

        projectResponse.setLinks(getProjectLinks(projectName));
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
     * Returns links for a project that is returned as part of the list of projects
     * 
     * @param project
     * @return links that can be added to a project
     */
    private LinksResponse getProjectsLinks(ProjectResponse project) {

        String projectUri = linkService.getProjectUriString(project.getName());
        List<LinkResponse> links = new ArrayList<LinkResponse>();

        /* GET - specific project */
        links.add(linkService.getLink(projectUri, "project details"));

        LinksResponse linksResponse = new LinksResponse();
        linksResponse.setLinks(links);
        return linksResponse;
    }

    private LinksResponse getProjectLinks(String projectName) {

        String projectUri = linkService.getProjectUriString(projectName);
        List<LinkResponse> links = new ArrayList<LinkResponse>();
        /* versions */
        links.add(linkService.getLink(linkService.getVersionstUriString(projectName), "project versions"));

        /* POST - update */
        links.add(linkService.getLink(projectUri, "updates existing project", "POST", "TODO"));
        /* DELETE */
        links.add(linkService.getLink(projectUri, "deletes project", "DELETE"));

        LinksResponse linksResponse = new LinksResponse();
        linksResponse.setLinks(links);
        return linksResponse;
    }
}
