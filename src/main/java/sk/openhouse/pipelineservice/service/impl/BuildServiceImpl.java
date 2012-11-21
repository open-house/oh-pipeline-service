package sk.openhouse.pipelineservice.service.impl;

import sk.openhouse.pipelineservice.dao.BuildReadDao;
import sk.openhouse.pipelineservice.dao.BuildWriteDao;
import sk.openhouse.pipelineservice.domain.request.BuildRequest;
import sk.openhouse.pipelineservice.domain.response.BuildResponse;
import sk.openhouse.pipelineservice.domain.response.BuildsResponse;
import sk.openhouse.pipelineservice.domain.response.ResourcesResponse;
import sk.openhouse.pipelineservice.service.BuildService;
import sk.openhouse.pipelineservice.util.HttpUtil;

public class BuildServiceImpl implements BuildService {

    private HttpUtil httpUtil;
    private BuildReadDao buildReadDao;
    private BuildWriteDao buildWriteDao;

    public BuildServiceImpl(HttpUtil httpUtil, BuildReadDao buildReadDao, BuildWriteDao buildWriteDao) {
        this.httpUtil = httpUtil;
        this.buildReadDao = buildReadDao;
        this.buildWriteDao = buildWriteDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildsResponse getBuilds(String projectName, String versionNumber) {
        return buildReadDao.getBuilds(projectName, versionNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildResponse getBuild(String projectName, String versionNumber, int buildNumber) {

        BuildResponse buildResponse = buildReadDao.getBuild(projectName, versionNumber, buildNumber);
        if (null != buildResponse) {
            buildResponse.setResources(getBuildDetailsResources(projectName, versionNumber, buildNumber));
        }

        return buildResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBuild(String projectName, String versionNumber, BuildRequest buildRequest) {
        buildWriteDao.addBuild(projectName, versionNumber, buildRequest);
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

    private ResourcesResponse getBuildDetailsResources(String projectName, String versionNumber, int buildNumber) {

        // TODO
        return null;
    }

}
