package sk.openhouse.pipelineservice.service.impl;

import sk.openhouse.pipelineservice.dao.BuildReadDao;
import sk.openhouse.pipelineservice.dao.BuildWriteDao;
import sk.openhouse.pipelineservice.domain.request.BuildRequest;
import sk.openhouse.pipelineservice.domain.response.BuildDetailsResponse;
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

    @Override
    public BuildDetailsResponse getBuild(String projectName, String versionNumber, int buildNumber) {

        BuildDetailsResponse buildDetails = buildReadDao.getBuild(projectName, versionNumber, buildNumber);
        if (null != buildDetails) {
            buildDetails.getBuild().setResources(getBuildDetailsResources(projectName, versionNumber, buildNumber));
        }

        return buildDetails;
    }

    @Override
    public void addBuild(String projectName, String versionNumber, BuildRequest buildRequest) {
        buildWriteDao.addBuild(projectName, versionNumber, buildRequest);
    }

    @Override
    public void updateBuild(String projectName, String versionNumber, int buildNumber, BuildRequest buildRequest) {
        buildWriteDao.updateBuild(projectName, versionNumber, buildNumber, buildRequest);
    }

    @Override
    public void deleteBuild(String projectName, String versionNumber, int buildNumber) {
        buildWriteDao.deleteBuild(projectName, versionNumber, buildNumber);
    }

    private ResourcesResponse getBuildDetailsResources(String projectName, String versionNumber, int buildNumber) {

        // TODO
        return null;
    }

}
