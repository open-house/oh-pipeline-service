package sk.openhouse.automation.pipelineservice.dao;

import org.springframework.dao.DuplicateKeyException;

import sk.openhouse.automation.pipelinedomain.domain.request.BuildRequest;

/**
 * Write DAO for project builds. Builds are linked to project and its version.
 * 
 * @author pete
 */
public interface BuildWriteDao {

    /**
     * Adds new build
     * 
     * @param projectName name of the project
     * @param versionNumber version number of the project
     * @param buildRequest
     * @throws DuplicateKeyException if the build number already exists
     */
    void addBuild(String projectName, String versionNumber, BuildRequest buildRequest) throws DuplicateKeyException;

    /**
     * Updates existing project build number
     * 
     * @param projectName name of the project
     * @param versionNumber version number of the project
     * @param buildNumber build number
     * @param buildRequest
     */
    void updateBuild(String projectName, String versionNumber, int buildNumber, BuildRequest buildRequest);

    /**
     * Deletes project build
     * 
     * @param projectName name of the project
     * @param versionNumber version number of the project
     * @param buildNumber build number
     */
    void deleteBuild(String projectName, String versionNumber, int buildNumber);
}
