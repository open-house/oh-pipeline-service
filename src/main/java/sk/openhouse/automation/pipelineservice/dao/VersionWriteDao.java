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
     * @param projectName name of the project
     * @param versionRequest version to be added
     * @throws DuplicateKeyException if the version (number) already exists
     */
    void addVersion(String projectName, VersionRequest versionRequest) throws DuplicateKeyException;

    /**
     * Updates existing project version number
     * 
     * @param projectName name of the project
     * @param versionNumber version number of the project to be updated
     * @param versionRequest new data to replace the existing ones
     */
    void updateVersion(String projectName, String versionNumber, VersionRequest versionRequest);

    /**
     * Deletes project version
     * 
     * @param projectName name of the project
     * @param versionNumber version number of the project to be updated
     */
    void deleteVersion(String projectName, String versionNumber);
}
