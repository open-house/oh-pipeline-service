package sk.openhouse.pipelineservice.dao;

import java.util.List;

import sk.openhouse.pipelineservice.domain.response.ProjectDetailsResponse;
import sk.openhouse.pipelineservice.domain.response.ProjectResponse;

/**
 * Read DAO for project
 * 
 * @author pete
 */
public interface ProjectReadDao {

    /**
     * @param name unique project name
     * @return project
     */
    ProjectDetailsResponse getProject(String name);

    /**
     * @return list of projects
     */
    List<ProjectResponse> getProjects();
}
