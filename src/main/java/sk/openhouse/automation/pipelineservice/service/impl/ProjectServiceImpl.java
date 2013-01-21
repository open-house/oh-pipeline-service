package sk.openhouse.automation.pipelineservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import sk.openhouse.automation.pipelineservice.dao.ProjectReadDao;
import sk.openhouse.automation.pipelineservice.dao.ProjectWriteDao;
import sk.openhouse.automation.pipelinedomain.domain.request.ProjectRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.LinkResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.LinksResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.ProjectResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.ProjectsResponse;
import sk.openhouse.automation.pipelineservice.resource.ResourceUtil;
import sk.openhouse.automation.pipelineservice.service.LinkService;
import sk.openhouse.automation.pipelineservice.service.ProjectService;
import sk.openhouse.automation.pipelineservice.service.exception.ConflictException;
import sk.openhouse.automation.pipelineservice.service.exception.NotFoundException;

/**
 * 
 * @author pete
 */
public class ProjectServiceImpl implements ProjectService {

    private final LinkService linkService;
    private final ProjectReadDao projectReadDao;
    private final ProjectWriteDao projectWriteDao;

    public ProjectServiceImpl(LinkService linkService, ProjectReadDao projectReadDao, ProjectWriteDao projectWriteDao) {

        this.linkService = linkService;
        this.projectReadDao = projectReadDao;
        this.projectWriteDao = projectWriteDao;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectResponse getProject(String projectName) {

        ProjectResponse projectResponse = projectReadDao.getProject(projectName);
        if (null == projectResponse) {
            throw new NotFoundException(String.format("Project %s cannot be found.", projectName));
        }

        projectResponse.setLinks(getProjectLinks(projectName));
        return projectResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addProject(ProjectRequest project) {

        try {
            projectWriteDao.addProject(project);
        } catch (DuplicateKeyException e) {
            throw new ConflictException(String.format("Project %s already exists.", project.getName()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateProject(String projectName, ProjectRequest project) {
        projectWriteDao.updateProject(projectName, project);
    }

    /**
     * {@inheritDoc}
     */
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
        links.add(linkService.getLink(linkService.getVersionsUriString(projectName), "project versions"));

        /* POST - update */
        String projectSchemaLocation = String.format("%s/%s", linkService.getSchemaRequestUriString(), ResourceUtil.PROJECT_PARAM);
        links.add(linkService.getLink(projectUri, "updates existing project", "POST", projectSchemaLocation));
        /* DELETE */
        links.add(linkService.getLink(projectUri, "deletes project", "DELETE"));

        LinksResponse linksResponse = new LinksResponse();
        linksResponse.setLinks(links);
        return linksResponse;
    }
}
