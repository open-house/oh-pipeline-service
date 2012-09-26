package sk.openhouse.pipelineservice.dao;

import sk.openhouse.pipelineservice.domain.request.BuildRequest;

/**
 * Write DAO for project build write access
 * 
 * @author pete
 */
public interface BuildWriteDao {

    /**
     * Adds new or overrides existing (if this build already exists) project build
     * 
     * @param projectName
     * @param versionNumber
     * @param buildRequest
     */
    void addBuild(String projectName, String versionNumber, BuildRequest buildRequest);

    /**
     * Updates existing project build number
     * 
     * @param projectName
     * @param versionNumber
     * @param buildNumber
     * @param buildRequest
     */
    void updateBuild(String projectName, String versionNumber, int buildNumber, BuildRequest buildRequest);

    /**
     * Deletes project build
     * 
     * @param projectName
     * @param versionNumber
     * @param buildNumber
     */
    void deleteBuild(String projectName, String versionNumber, int buildNumber);
}
