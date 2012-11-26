package sk.openhouse.pipelineservice.dao;

import sk.openhouse.pipelineservice.domain.response.BuildResponse;
import sk.openhouse.pipelineservice.domain.response.BuildsResponse;

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
     * @param buildNumber
     * @return specific build for selected product and version or null if the build cannot be found
     */
    BuildResponse getBuild(String projectName, String versionNumber, int buildNumber);
}
