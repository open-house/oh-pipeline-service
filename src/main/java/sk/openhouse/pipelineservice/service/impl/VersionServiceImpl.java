package sk.openhouse.pipelineservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import sk.openhouse.pipelineservice.dao.VersionReadDao;
import sk.openhouse.pipelineservice.dao.VersionWriteDao;
import sk.openhouse.pipelineservice.domain.request.VersionRequest;
import sk.openhouse.pipelineservice.domain.response.ResourceResponse;
import sk.openhouse.pipelineservice.domain.response.ResourcesResponse;
import sk.openhouse.pipelineservice.domain.response.VersionResponse;
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
    public VersionResponse getVersion(String projectName, String versionNumber) {

        VersionResponse versionResponse = versionReadDao.getVersion(projectName, versionNumber);
        if (null != versionResponse) {
            versionResponse.setResources(getVersionDetailsResources(projectName, versionNumber));
        }

        return versionResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionsResponse getVersions(String projectName) {

        VersionsResponse versionsResponse = versionReadDao.getVersions(projectName);
        for (VersionResponse versionResponse : versionsResponse.getVersions()) {
            versionResponse.setResources(getVersionResources(projectName, versionResponse.getNumber()));
        }

        return versionsResponse;
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

    private ResourcesResponse getVersionResources(String projectName, String versionNumber) {

        List<ResourceResponse> versionResources = new ArrayList<ResourceResponse>();
        /* GET */
        versionResources.add(httpUtil.getResource(httpUtil.getVersionURIString(projectName, versionNumber), 
                "Version Details"));

        ResourcesResponse resourcesResponse = new ResourcesResponse();
        resourcesResponse.setResources(versionResources);
        return resourcesResponse;
    }

    /**
     * Returns resources for a specific version
     */
    private ResourcesResponse getVersionDetailsResources(String projectName, String versionNumber) {

        String versionURI = httpUtil.getVersionURIString(projectName, versionNumber);

        List<ResourceResponse> versionDetailsResources = new ArrayList<ResourceResponse>();
        /* GET */
        versionDetailsResources.add(httpUtil.getResource(httpUtil.getVersionsURIString(projectName), "Project versions"));
        /* GET */
        versionDetailsResources.add(httpUtil.getResource(httpUtil.getBuildsURIString(projectName, versionNumber), 
                "List of all builds for this version."));
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
}
