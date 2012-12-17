package sk.openhouse.automation.pipelineservice.service;

import sk.openhouse.automation.pipelineservice.domain.request.VersionRequest;
import sk.openhouse.automation.pipelineservice.domain.response.VersionResponse;
import sk.openhouse.automation.pipelineservice.domain.response.VersionsResponse;
import sk.openhouse.automation.pipelineservice.service.exception.NotFoundException;

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
     * @throws NotFoundException if the version cannot be found
     */
    VersionResponse getVersion(String projectName, String versionNumber) throws NotFoundException;

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
