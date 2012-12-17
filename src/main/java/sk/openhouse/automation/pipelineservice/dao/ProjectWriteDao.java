package sk.openhouse.automation.pipelineservice.dao;

import sk.openhouse.automation.pipelinedomain.domain.request.ProjectRequest;

/**
 * Write DAO for project write access
 * 
 * @author pete
 */
public interface ProjectWriteDao {

    /**
     * @param project adds new project (project name has to be unique)
     */
    void addProject(ProjectRequest project);

    /**
     * Updates existing project
     * 
     * @param projectName name of the project that will be udpated
     * @param project data to be updated
     */
    void updateProject(String projectName, ProjectRequest project);

    /**
     * Deletes specified project
     * 
     * @param projectName
     */
    void deleteProject(String projectName);
}
