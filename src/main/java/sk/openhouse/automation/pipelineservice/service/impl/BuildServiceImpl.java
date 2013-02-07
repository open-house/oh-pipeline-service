package sk.openhouse.automation.pipelineservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;

import sk.openhouse.automation.pipelinedomain.domain.PhaseState;
import sk.openhouse.automation.pipelinedomain.domain.request.StateRequest;
import sk.openhouse.automation.pipelineservice.dao.BuildReadDao;
import sk.openhouse.automation.pipelineservice.dao.BuildWriteDao;
import sk.openhouse.automation.pipelinedomain.domain.request.BuildRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildsResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.LinkResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.LinksResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.PhaseResponse;
import sk.openhouse.automation.pipelineservice.resource.ResourceUtil;
import sk.openhouse.automation.pipelineservice.service.BuildPhaseService;
import sk.openhouse.automation.pipelineservice.service.BuildService;
import sk.openhouse.automation.pipelineservice.service.LinkService;
import sk.openhouse.automation.pipelineservice.service.PhaseService;
import sk.openhouse.automation.pipelineservice.service.VersionService;
import sk.openhouse.automation.pipelineservice.service.exception.ConflictException;
import sk.openhouse.automation.pipelineservice.service.exception.NotFoundException;

/**
 * 
 * @author pete
 */
public class BuildServiceImpl implements BuildService {

    private static final Logger logger = Logger.getLogger(BuildServiceImpl.class);

    private final LinkService linkService;
    private final BuildReadDao buildReadDao;
    private final BuildWriteDao buildWriteDao;
    private final VersionService versionService;
    private final PhaseService phaseService;
    private final BuildPhaseService buildPhaseService;

    public BuildServiceImpl(LinkService linkService, BuildReadDao buildReadDao, BuildWriteDao buildWriteDao,
            VersionService versionService, PhaseService phaseService, BuildPhaseService buildPhaseService) {

        this.linkService = linkService;
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

        buildResponse.setLinks(getBuildLinks(projectName, versionNumber, buildResponse));
        return buildResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBuild(String projectName, String versionNumber, BuildRequest buildRequest) {

        try {
            buildWriteDao.addBuild(projectName, versionNumber, buildRequest);
        } catch (DuplicateKeyException e) {
            throw new ConflictException(String.format("Build %d for the project %s and version %s already exists.",
                    buildRequest.getNumber(), projectName, versionNumber));
        }

        /* run first phase */
        PhaseResponse phaseResponse;
        try {
            phaseResponse = phaseService.getFirstPhase(projectName, versionNumber);
        } catch(NotFoundException e) {
            logger.debug(String.format("Cannot find any phase for project %s and version %s",
                    projectName, versionNumber));
            return;
        }

        int buildNumber = buildRequest.getNumber();
        String phaseName = phaseResponse.getName();
        StateRequest stateRequest = new StateRequest();
        stateRequest.setName(PhaseState.IN_PROGRESS);

        if (phaseResponse.isAuto()) {
            buildPhaseService.addState(projectName, versionNumber, buildNumber, phaseName, stateRequest);
        }
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
     * Validates if builds for specified project and version exist and sets links
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

        buildsResponse.setHref(linkService.getBuildUriTemplate(projectName, versionNumber));
        buildsResponse.setMethod("PUT");
        buildsResponse.setDescription("adds new build");

        for (BuildResponse build : buildsResponse.getBuilds()) {
            build.setLinks(getBuildsLinks(projectName, versionNumber, build));
        }
    }

    private LinksResponse getBuildsLinks(String projectName, String versionNumber, BuildResponse build) {

        String buildUri = linkService.getBuildUriString(projectName, versionNumber, build.getNumber());
        List<LinkResponse> links = new ArrayList<LinkResponse>();

        /* GET - specific project */
        links.add(linkService.getLink(buildUri, "build details"));

        LinksResponse linksResponse = new LinksResponse();
        linksResponse.setLinks(links);
        return linksResponse;
    }

    private LinksResponse getBuildLinks(String projectName, String versionNumber, BuildResponse build) {

        String buildUri = linkService.getBuildUriString(projectName, versionNumber, build.getNumber());
        List<LinkResponse> links = new ArrayList<LinkResponse>();
        /* phases */
        links.add(linkService.getLink(linkService.getBuildPhasesUriString(projectName, versionNumber, build.getNumber()), 
                "build phases and their states"));

        /* POST - update */
        String buildSchemaLocation = String.format("%s/%s", linkService.getSchemaRequestUriString(), ResourceUtil.BUILD_PARAM);
        links.add(linkService.getLink(buildUri, "updates existing bulid", "POST", buildSchemaLocation));
        /* DELETE */
        links.add(linkService.getLink(buildUri, "deletes build", "DELETE"));

        LinksResponse linksResponse = new LinksResponse();
        linksResponse.setLinks(links);
        return linksResponse;
    }
}
