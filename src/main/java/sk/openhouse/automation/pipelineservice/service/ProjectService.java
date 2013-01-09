package sk.openhouse.automation.pipelineservice.service;

import sk.openhouse.automation.pipelinedomain.domain.request.ProjectRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.ProjectResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.ProjectsResponse;
import sk.openhouse.automation.pipelineservice.service.exception.ConflictException;
import sk.openhouse.automation.pipelineservice.service.exception.NotFoundException;

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
     * @return project details
     * @throws NotFoundException if the project cannot be found
     */
    ProjectResponse getProject(String name) throws NotFoundException;

    /**
     * @param project adds new project, project name has to be unique
     * @throws ConflictException if the project already exists
     */
    void addProject(ProjectRequest project) throws ConflictException;

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
