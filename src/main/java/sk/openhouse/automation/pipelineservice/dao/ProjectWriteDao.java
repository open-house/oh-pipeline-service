package sk.openhouse.automation.pipelineservice.dao;

import org.springframework.dao.DuplicateKeyException;

import sk.openhouse.automation.pipelinedomain.domain.request.ProjectRequest;

/**
 * Write DAO for project(s).
 * 
 * @author pete
 */
public interface ProjectWriteDao {

    /**
     * @param project to be added (project name has to be unique)
     * @throws DuplicateKeyException if the project already exists
     */
    void addProject(ProjectRequest project) throws DuplicateKeyException;

    /**
     * Updates existing project
     * 
     * @param projectName name of the project to be updated
     * @param project data to be updated
     */
    void updateProject(String projectName, ProjectRequest project);

    /**
     * Deletes specified project
     * 
     * @param projectName name of the project to be deleted
     */
    void deleteProject(String projectName);
}
