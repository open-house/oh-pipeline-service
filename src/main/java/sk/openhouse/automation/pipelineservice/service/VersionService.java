package sk.openhouse.automation.pipelineservice.service;

import sk.openhouse.automation.pipelinedomain.domain.request.VersionRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.VersionResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.VersionsResponse;
import sk.openhouse.automation.pipelineservice.service.exception.ConflictException;
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
     * @throws NotFoundException if the project cannot be found
     */
    VersionsResponse getVersions(String projectName) throws NotFoundException;

    /**
     * Add new (or overrides existing) project version
     * 
     * @param projectName
     * @param versionRequest
     * @throws ConflictException
     */
    void addVersion(String projectName, VersionRequest versionRequest) throws ConflictException;

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
