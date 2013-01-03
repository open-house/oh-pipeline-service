package sk.openhouse.automation.pipelineservice.service.impl;

import sk.openhouse.automation.pipelineservice.dao.ProjectReadDao;
import sk.openhouse.automation.pipelineservice.dao.ProjectWriteDao;
import sk.openhouse.automation.pipelinedomain.domain.request.ProjectRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.ProjectResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.ProjectsResponse;
import sk.openhouse.automation.pipelineservice.service.ProjectService;
import sk.openhouse.automation.pipelineservice.service.exception.NotFoundException;

public class ProjectServiceImpl implements ProjectService {

    private final ProjectReadDao projectReadDao;
    private final ProjectWriteDao projectWriteDao;

    public ProjectServiceImpl(ProjectReadDao projectReadDao, ProjectWriteDao projectWriteDao) {

        this.projectReadDao = projectReadDao;
        this.projectWriteDao = projectWriteDao;
    }

    @Override
    public ProjectsResponse getProjects() {
        return projectReadDao.getProjects();
    }

    @Override
    public ProjectResponse getProject(String projectName) {

        ProjectResponse projectResponse = projectReadDao.getProject(projectName);
        if (null == projectResponse) {
            throw new NotFoundException(String.format("Project %s cannot be found.", projectName));
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
}
