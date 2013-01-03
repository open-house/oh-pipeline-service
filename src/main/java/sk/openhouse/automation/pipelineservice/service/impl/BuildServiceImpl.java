package sk.openhouse.automation.pipelineservice.service.impl;

import sk.openhouse.automation.pipelineservice.dao.BuildReadDao;
import sk.openhouse.automation.pipelineservice.dao.BuildWriteDao;
import sk.openhouse.automation.pipelinedomain.domain.request.BuildRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildsResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.PhaseResponse;
import sk.openhouse.automation.pipelineservice.service.BuildPhaseService;
import sk.openhouse.automation.pipelineservice.service.BuildService;
import sk.openhouse.automation.pipelineservice.service.PhaseService;
import sk.openhouse.automation.pipelineservice.service.VersionService;
import sk.openhouse.automation.pipelineservice.service.exception.NotFoundException;

public class BuildServiceImpl implements BuildService {

    private final BuildReadDao buildReadDao;
    private final BuildWriteDao buildWriteDao;
    private final VersionService versionService;
    private final PhaseService phaseService;
    private final BuildPhaseService buildPhaseService;

    public BuildServiceImpl(BuildReadDao buildReadDao, BuildWriteDao buildWriteDao,
            VersionService versionService, PhaseService phaseService, BuildPhaseService buildPhaseService) {

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
        validate(buildsResponse, projectName, versionNumber);
        return buildsResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildsResponse getBuilds(String projectName, String versionNumber, int limit) {

        BuildsResponse buildsResponse = buildReadDao.getBuilds(projectName, versionNumber, limit);
        validate(buildsResponse, projectName, versionNumber);
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
     * Validates if builds for specified project and version exist
     * 
     * @param buildsResponse
     * @param projectName
     * @param versionNumber
     */
    private void validate(BuildsResponse buildsResponse, String projectName, String versionNumber) {

        if (buildsResponse.getBuilds().isEmpty()) {
            /* check if version exists - service will throw NotFoundException if it doesn't */
            versionService.getVersion(projectName, versionNumber);
        }
    }
}
