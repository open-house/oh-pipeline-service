package sk.openhouse.pipelineservice.service;

import sk.openhouse.pipelineservice.domain.request.VersionRequest;
import sk.openhouse.pipelineservice.domain.response.VersionResponse;
import sk.openhouse.pipelineservice.domain.response.VersionsResponse;

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
    VersionResponse getVersion(String projectName, String versionNumber);

    /**
     * @param projectName
     * @return all versions of the specified project
     */
    VersionsResponse getVersions(String projectName);

    /**
     * Add new (or overrides existing) project version
     * 
     * @param projectName
     * @param versionRequest
     */
    void addVersion(String projectName, VersionRequest versionRequest);

    /**
     * Updates existing project version
     * 
     * @param projectName
     * @param versionNumber
     * @param versionRequest
     */
    void updateVersion(String projectName, String versionNumber, VersionRequest versionRequest);

    /**
     * Deletes project version
     * 
     * @param projectName
     * @param versionNumber
     */
    void deleteVersion(String projectName, String versionNumber);
}
