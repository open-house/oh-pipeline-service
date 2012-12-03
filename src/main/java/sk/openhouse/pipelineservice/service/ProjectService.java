package sk.openhouse.pipelineservice.service;

import sk.openhouse.pipelineservice.domain.request.ProjectRequest;
import sk.openhouse.pipelineservice.domain.response.ProjectResponse;
import sk.openhouse.pipelineservice.domain.response.ProjectsResponse;
import sk.openhouse.pipelineservice.service.exception.NotFoundException;

/**
 * Service for project(s)
 * 
 * @author pete
 */
public interface ProjectService {

    /**
     * @return all projects persisted in database
     */
    ProjectsResponse getProjects();

    /**
     * @param name unique product name
     * @return projetct details
     * @throws NotFoundException if the project cannot be found
     */
    ProjectResponse getProject(String name) throws NotFoundException;

    /**
     * @param project adds new project, project name has to be unique
     */
    void addProject(ProjectRequest project);

    /**
     * Updates existing project
     * 
     * @param projectName name of the project that will be updated
     * @param project data to be updated
     */
    void updateProject(String projectName, ProjectRequest project);

    /**
     * Deletes existing project
     * 
     * @param projectName
     */
    void deleteProject(String projectName);
}
