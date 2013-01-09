package sk.openhouse.automation.pipelineservice.service;

import sk.openhouse.automation.pipelinedomain.domain.request.BuildRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildsResponse;
import sk.openhouse.automation.pipelineservice.service.exception.ConflictException;
import sk.openhouse.automation.pipelineservice.service.exception.NotFoundException;

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
     * @throws NotFoundException if the versionNumber or the projectName cannot be found
     */
    BuildsResponse getBuilds(String projectName, String versionNumber) throws NotFoundException;

    /**
     * Returns all builds for specified project and version
     * 
     * @param projectName
     * @param versionNumber
     * @param limit max returned builds
     * @return
     * @throws NotFoundException if the versionNumber or the projectName cannot be found
     */
    BuildsResponse getBuilds(String projectName, String versionNumber, int limit) throws NotFoundException;

    /**
     * Returns specific build for product and version
     * 
     * @param projectName
     * @param versionNumber
     * @param buildNumber
     * @return
     * @throws NotFoundException if the build cannot be found
     */
    BuildResponse getBuild(String projectName, String versionNumber, int buildNumber) throws NotFoundException;


    /**
     * Adds new build
     * 
     * @param projectName
     * @param versionNumber
     * @param buildRequest
     * @throws ConflictException if the build already exists
     */
    void addBuild(String projectName, String versionNumber, BuildRequest buildRequest) throws ConflictException;

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
