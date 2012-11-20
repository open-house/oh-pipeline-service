package sk.openhouse.pipelineservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import sk.openhouse.pipelineservice.dao.VersionReadDao;
import sk.openhouse.pipelineservice.dao.VersionWriteDao;
import sk.openhouse.pipelineservice.domain.request.VersionRequest;
import sk.openhouse.pipelineservice.domain.response.BuildResponse;
import sk.openhouse.pipelineservice.domain.response.ResourceResponse;
import sk.openhouse.pipelineservice.domain.response.ResourcesResponse;
import sk.openhouse.pipelineservice.domain.response.VersionDetailsResponse;
import sk.openhouse.pipelineservice.domain.response.VersionsResponse;
import sk.openhouse.pipelineservice.service.VersionService;
import sk.openhouse.pipelineservice.util.HttpUtil;

public class VersionServiceImpl implements VersionService {

    private HttpUtil httpUtil;
    private VersionReadDao versionReadDao;
    private VersionWriteDao versionWriteDao;

    public VersionServiceImpl(HttpUtil httpUtil, VersionReadDao versionReadDao, VersionWriteDao versionWriteDao) {
        this.httpUtil = httpUtil;
        this.versionReadDao = versionReadDao;
        this.versionWriteDao = versionWriteDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionDetailsResponse getVersion(String projectName, String versionNumber) {

        VersionDetailsResponse versionDetailsResponse = versionReadDao.getVersion(projectName, versionNumber);
        if (null != versionDetailsResponse) {
            versionDetailsResponse.getVersion().setResources(getVersionDetailsResources(projectName, versionNumber));

            for (BuildResponse build : versionDetailsResponse.getBuilds().getBuilds()) {
                build.setResources(getBuildResources(projectName, versionNumber, build.getNumber()));
            }
        }

        return versionDetailsResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionsResponse getVersions(String projectName) {
        return versionReadDao.getVersions(projectName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addVersion(String projectName, VersionRequest versionRequest) {
        versionWriteDao.addVersion(projectName, versionRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateVersion(String projectName, String versionNumber, VersionRequest versionRequest) {
        versionWriteDao.updateVersion(projectName, versionNumber, versionRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteVersion(String projectName, String versionNumber) {
        versionWriteDao.deleteVersion(projectName, versionNumber);
    }

    /**
     * Returns resources for a specific version
     */
    private ResourcesResponse getVersionDetailsResources(String projectName, String versionNumber) {

        String versionURI = httpUtil.getVersionRelativeURI(projectName, versionNumber);

        List<ResourceResponse> versionDetailsResources = new ArrayList<ResourceResponse>();
        /* GET */
        versionDetailsResources.add(httpUtil.getResource(httpUtil.getProjectRelativeURI(projectName), "Project details"));
        /* PUT */
        versionDetailsResources.add(httpUtil.getResource(versionURI, 
                "Insert new version, or overwride existing version", "PUT"));
        /* POST */
        versionDetailsResources.add(httpUtil.getResource(versionURI, 
                "Update existing version", "POST"));
        /* DELETE */
        versionDetailsResources.add(httpUtil.getResource(versionURI, 
                "Delete existing version", "DELETE"));

        ResourcesResponse resourcesResponse = new ResourcesResponse();
        resourcesResponse.setResources(versionDetailsResources);
        return resourcesResponse;
    }

    private ResourcesResponse getBuildResources(String projectName, String versionNumber, int buildNumber) {

        List<ResourceResponse> buildResources = new ArrayList<ResourceResponse>();
        /* GET */
        buildResources.add(httpUtil.getResource(httpUtil.getBuildRelativeURI(projectName, versionNumber, buildNumber), 
                "Version Details"));

        ResourcesResponse resourcesResponse = new ResourcesResponse();
        resourcesResponse.setResources(buildResources);
        return resourcesResponse;
    }
}
