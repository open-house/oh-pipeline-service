package sk.openhouse.automation.pipelineservice.dao;

import sk.openhouse.automation.pipelinedomain.domain.response.BuildResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildsResponse;

/**
 * Read DAO for builds
 * 
 * @author pete
 */
public interface BuildReadDao {

    /**
     * @param projectName
     * @param versionNumber
     * @return
     */
    BuildsResponse getBuilds(String projectName, String versionNumber);

    /**
     * @param projectName
     * @param versionNumber
     * @param limit max returned rows
     * @return
     */
    BuildsResponse getBuilds(String projectName, String versionNumber, int limit);

    /**
     * @param projectName
     * @param versionNumber
     * @param buildNumber
     * @return specific build for selected product and version or null if the build cannot be found
     */
    BuildResponse getBuild(String projectName, String versionNumber, int buildNumber);
}
