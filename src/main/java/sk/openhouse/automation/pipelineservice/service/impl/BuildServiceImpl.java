package sk.openhouse.automation.pipelineservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import sk.openhouse.automation.pipelineservice.dao.BuildReadDao;
import sk.openhouse.automation.pipelineservice.dao.BuildWriteDao;
import sk.openhouse.automation.pipelinedomain.domain.request.BuildRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildsResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.PhaseResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.ResourceResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.ResourcesResponse;
import sk.openhouse.automation.pipelineservice.service.BuildPhaseService;
import sk.openhouse.automation.pipelineservice.service.BuildService;
import sk.openhouse.automation.pipelineservice.service.PhaseService;
import sk.openhouse.automation.pipelineservice.service.ResourceService;
import sk.openhouse.automation.pipelineservice.service.VersionService;
import sk.openhouse.automation.pipelineservice.service.exception.NotFoundException;

public class BuildServiceImpl implements BuildService {

    private final ResourceService resourceService;
    private final BuildReadDao buildReadDao;
    private final BuildWriteDao buildWriteDao;
    private final VersionService versionService;
    private final PhaseService phaseService;
    private final BuildPhaseService buildPhaseService;

    public BuildServiceImpl(ResourceService resourceService, BuildReadDao buildReadDao, BuildWriteDao buildWriteDao,
            VersionService versionService, PhaseService phaseService, BuildPhaseService buildPhaseService) {

        this.resourceService = resourceService;
        this.buildReadDao = buildReadDao;
        this.buildWriteDao = buildWriteDao;
        this.versionService = versionService;
        this.phaseService = phaseService;
        this.buildPhaseService = buildPhaseService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildsResponse getBuilds(String projectName, String versionNumber) {

        BuildsResponse buildsResponse = buildReadDao.getBuilds(projectName, versionNumber);
        setResources(buildsResponse, projectName, versionNumber);
        return buildsResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildsResponse getBuilds(String projectName, String versionNumber, int limit) {

        BuildsResponse buildsResponse = buildReadDao.getBuilds(projectName, versionNumber, limit);
        setResources(buildsResponse, projectName, versionNumber);
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

        buildWriteDao.addBuild(projectName, versionNumber, buildRequest);

        /* run first phase */
        PhaseResponse phaseResponse;
        try {
            phaseResponse = phaseService.getFirstPhase(projectName, versionNumber);
        } catch(NotFoundException e) {
            /* no phases found */
            return;
        }

        buildPhaseService.runPhase(projectName, versionNumber, buildRequest.getNumber(), phaseResponse);
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

    /**
     * Sets resources on supplied builds and checks if project and version exists if builds are empty
     * 
     * @param buildsResponse
     * @param projectName
     * @param versionNumber
     */
    private void setResources(BuildsResponse buildsResponse, String projectName, String versionNumber) {

        if (buildsResponse.getBuilds().isEmpty()) {
            /* check if version exists - service will throw NotFoundException if it doesn't */
            versionService.getVersion(projectName, versionNumber);
        }

        for (BuildResponse buildResponse : buildsResponse.getBuilds()) {
            buildResponse.setResources(getBuildResources(projectName, versionNumber, buildResponse.getNumber()));
        }
    }

    private ResourcesResponse getBuildResources(String projectName, String versionNumber, int buildNumber) {

        List<ResourceResponse> buildResources = new ArrayList<ResourceResponse>();
        /* GET */
        buildResources.add(resourceService.getResource(
                resourceService.getBuildURIString(projectName, versionNumber, buildNumber),
                "Build Details"));

        ResourcesResponse resourcesResponse = new ResourcesResponse();
        resourcesResponse.setResources(buildResources);
        return resourcesResponse;
    }

    private ResourcesResponse getBuildDetailsResources(String projectName, String versionNumber, int buildNumber) {

        List<ResourceResponse> buildResources = new ArrayList<ResourceResponse>();
        String buildURIString = resourceService.getBuildURIString(projectName, versionNumber, buildNumber);

        /* GET */
        buildResources.add(resourceService.getResource(
                resourceService.getBuildsURIString(projectName, versionNumber),
                "List of all builds for project and its version"));
        /* PUT */
        buildResources.add(resourceService.getResource(buildURIString, "Insert new, or overwride existing build", "PUT"));
        /* POST */
        buildResources.add(resourceService.getResource(buildURIString, "Update existing build", "POST"));
        /* DELETE */
        buildResources.add(resourceService.getResource(buildURIString, "Delete existing build", "DELETE"));

        ResourcesResponse resourcesResponse = new ResourcesResponse();
        resourcesResponse.setResources(buildResources);
        return resourcesResponse;
    }

}
