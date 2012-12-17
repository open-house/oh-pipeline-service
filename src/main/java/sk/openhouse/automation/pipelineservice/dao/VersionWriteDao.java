package sk.openhouse.automation.pipelineservice.dao;

import sk.openhouse.automation.pipelineservice.domain.request.VersionRequest;

/**
 * Write DAO for project version write access
 * 
 * @author pete
 */
public interface VersionWriteDao {

    /**
     * Adds new or overrides existing (if this version already exists) project version
     * 
     * @param projectName
     * @param versionRequest
     */
    void addVersion(String projectName, VersionRequest versionRequest);

    /**
     * Updates existing project version number
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
