package sk.openhouse.automation.pipelineservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import sk.openhouse.automation.pipelineservice.dao.VersionReadDao;
import sk.openhouse.automation.pipelineservice.dao.VersionWriteDao;
import sk.openhouse.automation.pipelinedomain.domain.request.VersionRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.ResourceResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.ResourcesResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.VersionResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.VersionsResponse;
import sk.openhouse.automation.pipelineservice.service.ProjectService;
import sk.openhouse.automation.pipelineservice.service.ResourceService;
import sk.openhouse.automation.pipelineservice.service.VersionService;
import sk.openhouse.automation.pipelineservice.service.exception.NotFoundException;

public class VersionServiceImpl implements VersionService {

    private final ResourceService resourceService;
    private final VersionReadDao versionReadDao;
    private final VersionWriteDao versionWriteDao;
    private final ProjectService projectService;

    public VersionServiceImpl(ResourceService resourceService, VersionReadDao versionReadDao,
            VersionWriteDao versionWriteDao, ProjectService projectService) {

        this.resourceService = resourceService;
        this.versionReadDao = versionReadDao;
        this.versionWriteDao = versionWriteDao;
        this.projectService = projectService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionResponse getVersion(String projectName, String versionNumber) {

        VersionResponse versionResponse = versionReadDao.getVersion(projectName, versionNumber);
        if (null == versionResponse) {
            throw new NotFoundException(String.format("Version %s for project %s cannot be found.",
                    versionNumber, projectName));
        }

        versionResponse.setResources(getVersionDetailsResources(projectName, versionNumber));
        return versionResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionsResponse getVersions(String projectName) {

        VersionsResponse versionsResponse = versionReadDao.getVersions(projectName);
        /* check if project exists - service will throw NotFoundException if it doesn't */
        if (versionsResponse.getVersions().isEmpty()) {
            projectService.getProject(projectName);
        }

        for (VersionResponse versionResponse : versionsResponse.getVersions()) {
            versionResponse.setResources(getVersionResources(projectName, versionResponse.getVersionNumber()));
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
        versionResources.add(resourceService.getResource(resourceService.getVersionURIString(projectName, versionNumber), 
                "Version Details"));

        ResourcesResponse resourcesResponse = new ResourcesResponse();
        resourcesResponse.setResources(versionResources);
        return resourcesResponse;
    }

    /**
     * Returns resources for a specific version
     */
    private ResourcesResponse getVersionDetailsResources(String projectName, String versionNumber) {

        String versionURI = resourceService.getVersionURIString(projectName, versionNumber);
        String phasesURI = resourceService.getPhasesURIString(projectName, versionNumber);

        List<ResourceResponse> versionDetailsResources = new ArrayList<ResourceResponse>();
        /* GET */
        versionDetailsResources.add(resourceService.getResource(resourceService.getVersionsURIString(projectName), "Project versions"));
        /* GET */
        versionDetailsResources.add(resourceService.getResource(resourceService.getBuildsURIString(projectName, versionNumber), 
                "List of all builds for this version."));
        /* GET */
        versionDetailsResources.add(resourceService.getResource(phasesURI, "List of all phases of this project"));
        /* PUT */
        versionDetailsResources.add(resourceService.getResource(versionURI, 
                "Insert new version, or overwride existing version", "PUT"));
        /* POST */
        versionDetailsResources.add(resourceService.getResource(versionURI, 
                "Update existing version", "POST"));
        /* DELETE */
        versionDetailsResources.add(resourceService.getResource(versionURI, 
                "Delete existing version", "DELETE"));

        ResourcesResponse resourcesResponse = new ResourcesResponse();
        resourcesResponse.setResources(versionDetailsResources);
        return resourcesResponse;
    }
}
