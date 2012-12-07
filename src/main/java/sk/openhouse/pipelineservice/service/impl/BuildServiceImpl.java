package sk.openhouse.pipelineservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import sk.openhouse.pipelineservice.dao.BuildPhaseReadDao;
import sk.openhouse.pipelineservice.dao.BuildPhaseWriteDao;
import sk.openhouse.pipelineservice.dao.BuildReadDao;
import sk.openhouse.pipelineservice.dao.BuildWriteDao;
import sk.openhouse.pipelineservice.domain.request.BuildRequest;
import sk.openhouse.pipelineservice.domain.response.BuildResponse;
import sk.openhouse.pipelineservice.domain.response.BuildsResponse;
import sk.openhouse.pipelineservice.domain.response.ResourceResponse;
import sk.openhouse.pipelineservice.domain.response.ResourcesResponse;
import sk.openhouse.pipelineservice.service.BuildService;
import sk.openhouse.pipelineservice.service.exception.NotFoundException;
import sk.openhouse.pipelineservice.util.HttpUtil;

public class BuildServiceImpl implements BuildService {

    private HttpUtil httpUtil;
    private BuildReadDao buildReadDao;
    private BuildWriteDao buildWriteDao;
    private BuildPhaseReadDao buildPhaseReadDao;
    private BuildPhaseWriteDao buildPhaseWriteDao;

    public BuildServiceImpl(HttpUtil httpUtil, BuildReadDao buildReadDao, BuildWriteDao buildWriteDao,
            BuildPhaseReadDao buildPhaseReadDao, BuildPhaseWriteDao buildPhaseWriteDao) {

        this.httpUtil = httpUtil;
        this.buildReadDao = buildReadDao;
        this.buildWriteDao = buildWriteDao;
        this.buildPhaseReadDao = buildPhaseReadDao;
        this.buildPhaseWriteDao = buildPhaseWriteDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildsResponse getBuilds(String projectName, String versionNumber) {

        BuildsResponse buildsResponse = buildReadDao.getBuilds(projectName, versionNumber);
        for (BuildResponse buildResponse : buildsResponse.getBuilds()) {
            buildResponse.setResources(getBuildResources(projectName, versionNumber, buildResponse.getNumber()));
        }
        return buildsResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildResponse getBuild(String projectName, String versionNumber, int buildNumber) {

        BuildResponse buildResponse = buildReadDao.getBuild(projectName, versionNumber, buildNumber);
        if (null == buildResponse) {
            throw new NotFoundException(String.format("Build %d for project %s and version %s cannot be found.", 
                    buildNumber, projectName, versionNumber));
        }

        buildResponse.setResources(getBuildDetailsResources(projectName, versionNumber, buildNumber));
        return buildResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBuild(String projectName, String versionNumber, BuildRequest buildRequest) {

        // TODO - phase cannot be hardcoded here, but should be automatically done in build phase dao
        buildWriteDao.addBuild(projectName, versionNumber, buildRequest);
        buildPhaseWriteDao.addPhase(projectName, versionNumber, buildRequest.getNumber(), "QA");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateBuild(String projectName, String versionNumber, int buildNumber, BuildRequest buildRequest) {
        buildWriteDao.updateBuild(projectName, versionNumber, buildNumber, buildRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteBuild(String projectName, String versionNumber, int buildNumber) {
        buildWriteDao.deleteBuild(projectName, versionNumber, buildNumber);
    }

    private ResourcesResponse getBuildResources(String projectName, String versionNumber, int buildNumber) {

        List<ResourceResponse> buildResources = new ArrayList<ResourceResponse>();
        /* GET */
        buildResources.add(httpUtil.getResource(
                httpUtil.getBuildURIString(projectName, versionNumber, buildNumber),
                "Build Details"));

        ResourcesResponse resourcesResponse = new ResourcesResponse();
        resourcesResponse.setResources(buildResources);
        return resourcesResponse;
    }

    private ResourcesResponse getBuildDetailsResources(String projectName, String versionNumber, int buildNumber) {

        List<ResourceResponse> buildResources = new ArrayList<ResourceResponse>();
        String buildURIString = httpUtil.getBuildURIString(projectName, versionNumber, buildNumber);

        /* GET */
        buildResources.add(httpUtil.getResource(
                httpUtil.getBuildsURIString(projectName, versionNumber),
                "List of all builds for project and its version"));
        /* PUT */
        buildResources.add(httpUtil.getResource(buildURIString, "Insert new, or overwride existing build", "PUT"));
        /* POST */
        buildResources.add(httpUtil.getResource(buildURIString, "Update existing build", "POST"));
        /* DELETE */
        buildResources.add(httpUtil.getResource(buildURIString, "Delete existing build", "DELETE"));

        ResourcesResponse resourcesResponse = new ResourcesResponse();
        resourcesResponse.setResources(buildResources);
        return resourcesResponse;
    }

}
