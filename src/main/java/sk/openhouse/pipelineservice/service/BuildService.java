package sk.openhouse.pipelineservice.service;

import sk.openhouse.pipelineservice.domain.request.BuildRequest;
import sk.openhouse.pipelineservice.domain.response.BuildResponse;
import sk.openhouse.pipelineservice.domain.response.BuildsResponse;

/**
 * Service for build(s)
 * 
 * @author pete
 */
public interface BuildService {

    /**
     * Returns all builds for specified project and version
     * 
     * @param projectName
     * @param versionNumber
     * @return
     */
    BuildsResponse getBuilds(String projectName, String versionNumber);

    /**
     * Returns specific build for product and version
     * 
     * @param projectName
     * @param versionNumber
     * @param buildNumber
     * @return
     */
    BuildResponse getBuild(String projectName, String versionNumber, int buildNumber);


    /**
     * Add new (or overrides existing) project build
     * 
     * @param projectName
     * @param versionNumber
     * @param buildRequest
     */
    void addBuild(String projectName, String versionNumber, BuildRequest buildRequest);

    /**
     * Updates existing project build
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
