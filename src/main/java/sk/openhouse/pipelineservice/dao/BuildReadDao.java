package sk.openhouse.pipelineservice.dao;

import sk.openhouse.pipelineservice.domain.response.BuildDetailsResponse;

/**
 * Read DAO for builds
 * 
 * @author pete
 */
public interface BuildReadDao {

    /**
     * @param projectName
     * @param versionNumber
     * @param buildNumber
     * @return specific build for selected product and version
     */
    BuildDetailsResponse getBuild(String projectName, String versionNumber, int buildNumber);
}
