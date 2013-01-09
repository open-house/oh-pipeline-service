package sk.openhouse.automation.pipelineservice.dao;

import sk.openhouse.automation.pipelinedomain.domain.response.BuildResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildsResponse;

/**
 * Read DAO for project builds. Builds are linked to project and its version.
 * 
 * @author pete
 */
public interface BuildReadDao {

    /**
     * @param projectName name of the project
     * @param versionNumber version number of the project
     * @return all builds for the specified project version
     */
    BuildsResponse getBuilds(String projectName, String versionNumber);

    /**
     * @param projectName name of the project
     * @param versionNumber version number of the project
     * @param limit max returned rows
     * @return specified number of builds for the project version
     */
    BuildsResponse getBuilds(String projectName, String versionNumber, int limit);

    /**
     * @param projectName name of the project
     * @param versionNumber version number of the project
     * @param buildNumber build number
     * @return specific build for selected product and version or null if the build cannot be found
     */
    BuildResponse getBuild(String projectName, String versionNumber, int buildNumber);
}
