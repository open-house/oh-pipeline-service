package sk.openhouse.automation.pipelineservice.dao;

import sk.openhouse.automation.pipelinedomain.domain.response.VersionResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.VersionsResponse;

/**
 * Read DAO for version(s).
 * 
 * @author pete
 */
public interface VersionReadDao {

    /**
     * @param projectName name of the project
     * @param versionNumber version number of the project
     * @return specific version for the specified project or null if the version cannot be found
     */
    VersionResponse getVersion(String projectName, String versionNumber);

    /**
     * 
     * @param projectName name of the project
     * @return all versions of the specified project
     */
    VersionsResponse getVersions(String projectName);
}
