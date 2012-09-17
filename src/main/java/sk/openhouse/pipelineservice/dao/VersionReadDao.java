package sk.openhouse.pipelineservice.dao;

import sk.openhouse.pipelineservice.domain.response.VersionDetailsResponse;

/**
 * Read DAO for version
 * 
 * @author pete
 */
public interface VersionReadDao {

    /**
     * @param projectName
     * @param versionNumber
     * @return specific version for the specified project
     */
    VersionDetailsResponse getVersion(String projectName, String versionNumber);
}
