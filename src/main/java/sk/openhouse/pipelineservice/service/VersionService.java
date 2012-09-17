package sk.openhouse.pipelineservice.service;

import sk.openhouse.pipelineservice.domain.response.VersionDetailsResponse;

/**
 * Service for project version(s)
 * 
 * @author pete
 */
public interface VersionService {

    /**
     * @param projectName
     * @param versionNumber
     * @return specific version for the specified project
     */
    VersionDetailsResponse getVersion(String projectName, String versionNumber);
}
