package sk.openhouse.automation.pipelineservice.dao;


import sk.openhouse.automation.pipelinedomain.domain.response.ProjectResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.ProjectsResponse;

/**
 * Read DAO for project(s).
 * 
 * @author pete
 */
public interface ProjectReadDao {

    /**
     * @param name unique project name
     * @return project or null if the project cannot be found
     */
    ProjectResponse getProject(String name);

    /**
     * @return all projects
     */
    ProjectsResponse getProjects();
}
