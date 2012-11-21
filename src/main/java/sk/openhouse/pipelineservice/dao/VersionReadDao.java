package sk.openhouse.pipelineservice.dao;

import sk.openhouse.pipelineservice.domain.response.VersionResponse;
import sk.openhouse.pipelineservice.domain.response.VersionsResponse;

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
    VersionResponse getVersion(String projectName, String versionNumber);

    /**
     * 
     * @param projectName
     * @return all versions of the specified project
     */
    VersionsResponse getVersions(String projectName);
}
