package sk.openhouse.pipelineservice.dao;


import sk.openhouse.pipelineservice.domain.response.ProjectResponse;
import sk.openhouse.pipelineservice.domain.response.ProjectsResponse;

/**
 * Read DAO for project
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
     * @return all projects (with no resources set)
     */
    ProjectsResponse getProjects();
}
