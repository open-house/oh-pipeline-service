package sk.openhouse.automation.pipelineservice.dao;

import org.springframework.dao.DuplicateKeyException;

import sk.openhouse.automation.pipelinedomain.domain.request.VersionRequest;

/**
 * Write DAO for project version write access
 * 
 * @author pete
 */
public interface VersionWriteDao {

    /**
     * Adds new project version
     * 
     * @param projectName
     * @param versionRequest
     * @throws DuplicateKeyException if the build number already exists
     */
    void addVersion(String projectName, VersionRequest versionRequest) throws DuplicateKeyException;

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
