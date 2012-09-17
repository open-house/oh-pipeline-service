package sk.openhouse.pipelineservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import sk.openhouse.pipelineservice.dao.VersionReadDao;
import sk.openhouse.pipelineservice.domain.response.ResourceResponse;
import sk.openhouse.pipelineservice.domain.response.ResourcesResponse;
import sk.openhouse.pipelineservice.domain.response.VersionDetailsResponse;
import sk.openhouse.pipelineservice.service.VersionService;
import sk.openhouse.pipelineservice.util.HttpUtil;

public class VersionServiceImpl implements VersionService {

    private HttpUtil httpUtil;
    private VersionReadDao versionReadDao;

    public VersionServiceImpl(HttpUtil httpUtil, VersionReadDao versionReadDao) {
        this.httpUtil = httpUtil;
        this.versionReadDao = versionReadDao;
    }

    @Override
    public VersionDetailsResponse getVersion(String projectName, String versionNumber) {

        VersionDetailsResponse versionDetailsResponse = versionReadDao.getVersion(projectName, versionNumber);
        if (null != versionDetailsResponse) {
            versionDetailsResponse.getVersion().setResources(getVersionDetailsResources(projectName, versionNumber));
        }

        return versionDetailsResponse;
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
}
