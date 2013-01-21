package sk.openhouse.automation.pipelineservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.core.util.MultivaluedMapImpl;

import sk.openhouse.automation.pipelineservice.dao.BuildPhaseReadDao;
import sk.openhouse.automation.pipelineservice.dao.BuildPhaseWriteDao;
import sk.openhouse.automation.pipelineservice.dao.BuildReadDao;
import sk.openhouse.automation.pipelinedomain.domain.PhaseState;
import sk.openhouse.automation.pipelinedomain.domain.request.StateRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildPhaseResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildPhasesResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.LinkResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.LinksResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.PhaseResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.PhasesResponse;
import sk.openhouse.automation.pipelineservice.resource.ResourceUtil;
import sk.openhouse.automation.pipelineservice.service.BuildPhaseService;
import sk.openhouse.automation.pipelineservice.service.LinkService;
import sk.openhouse.automation.pipelineservice.service.PhaseService;
import sk.openhouse.automation.pipelineservice.service.exception.ConflictException;
import sk.openhouse.automation.pipelineservice.service.exception.NotFoundException;
import sk.openhouse.automation.pipelineservice.util.HttpUtil;

/**
 * 
 * @author pete
 */
public class BuildPhaseServiceImpl implements BuildPhaseService {

    private final LinkService linkService;
    private final BuildPhaseReadDao buildPhaseReadDao;
    private final BuildPhaseWriteDao buildPhaseWriteDao;
    private final BuildReadDao buildReadDao;
    private final PhaseService phaseService;
    private final HttpUtil httpUtil;

    public BuildPhaseServiceImpl(LinkService linkService, BuildPhaseReadDao buildPhaseReadDao, 
            BuildPhaseWriteDao buildPhaseWriteDao, BuildReadDao buildReadDao, PhaseService phaseService, 
            HttpUtil httpUtil) {

        this.linkService = linkService;
        this.buildPhaseReadDao = buildPhaseReadDao;
        this.buildPhaseWriteDao = buildPhaseWriteDao;
        this.buildReadDao = buildReadDao;
        this.phaseService = phaseService;
        this.httpUtil = httpUtil;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addState(String projectName, String versionNumber, int buildNumber, String phaseName, StateRequest stateRequest) {

        PhaseState lastPhaseState = buildPhaseReadDao.getLastState(projectName, versionNumber, buildNumber, phaseName);
        if (null == lastPhaseState) {
            throw new NotFoundException(String.format("No state found for project %s version %s build %d and phase %s",
                    projectName, versionNumber, buildNumber, phaseName));
        }

        /* build phase has already been successfully completed */
        if (lastPhaseState.equals(PhaseState.SUCCESS)) {
            throw new ConflictException(String.format("Phase %s has already been successfully completed.", phaseName));
        }

        /* already updated */
        PhaseState state = stateRequest.getName();
        if (state.equals(lastPhaseState)) {
            throw new ConflictException(String.format("Build phase is already in %s state.", state));
        }

        /* failed state updated to in progress - rerun the phase */
        if (state.equals(PhaseState.IN_PROGRESS)) {
            PhaseResponse phaseResponse = phaseService.getPhase(projectName, versionNumber, phaseName);
            runPhase(projectName, versionNumber, buildNumber, phaseResponse);
            return;
        }

        /* success - move to the next phase */
        if (state.equals(PhaseState.SUCCESS)) {
            buildPhaseWriteDao.addState(projectName, versionNumber, buildNumber, phaseName, state);
            runNextPhase(projectName, versionNumber, buildNumber, phaseName);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildPhasesResponse getBuildPhases(String projectName, String versionNumber, int buildNumber) {

        BuildPhasesResponse buildPhasesResponse = buildPhaseReadDao.getBuildPhases(projectName, versionNumber, buildNumber);
        if (buildPhasesResponse.getBuildPhases().isEmpty()) {
            /* check if phases exist - service will throw NotFoundException if it doesn't */
            if(null == buildReadDao.getBuild(projectName, versionNumber, buildNumber)) {
                throw new NotFoundException(String.format("Build %d for project %s and version %s cannot be found",
                        buildNumber, projectName, versionNumber));
            }
        }

        for (BuildPhaseResponse buildPhase : buildPhasesResponse.getBuildPhases()) {
            buildPhase.setLinks(getBuildPhasesLinks(projectName, versionNumber, buildNumber, buildPhase.getName()));
        }
        return buildPhasesResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildPhaseResponse getBuildPhase(String projectName, String versionNumber, int buildNumber, String phaseName) {

        BuildPhaseResponse buildPhaseResponse = buildPhaseReadDao.getBuildPhase(projectName, versionNumber, buildNumber, phaseName);
        if (null == buildPhaseResponse) {
            throw new NotFoundException(String.format("No phase %s found for project %s version %s and build %d", 
                    phaseName, projectName, versionNumber, buildNumber));
        }

        buildPhaseResponse.setLinks(getBuildPhaseLinks(projectName, versionNumber, buildNumber, phaseName));
        return buildPhaseResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void runPhase(String projectName, String versionNumber, int buildNumber, PhaseResponse phaseResponse) {

        /* set to IN_PROGRESS */
        buildPhaseWriteDao.addState(projectName, versionNumber, buildNumber, 
                phaseResponse.getName(), PhaseState.IN_PROGRESS);

        sendPhaseRequest(projectName, versionNumber, buildNumber, phaseResponse);
    }

    /**
     * Sends request to URI specified for the phase, if the request fails, FAIL state will be added to that phase
     * 
     * @param projectName name of the project for which the phase should run
     * @param versionNumber version number of the project for which the phase should run
     * @param buildNumber build number of the project for which the phase should run
     * @param phaseResponse specific phase that holds the URI
     */
    private void sendPhaseRequest(String projectName, String versionNumber, int buildNumber, PhaseResponse phaseResponse) {

        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.putSingle("projectName", projectName);
        params.putSingle("versionNumber", versionNumber);
        params.putSingle("buildNumber", Integer.toString(buildNumber));
        params.putSingle("phaseName", phaseResponse.getName());

        if (!httpUtil.sendRequest(phaseResponse.getUri(), params)) {
            buildPhaseWriteDao.addState(projectName, versionNumber, buildNumber, 
                    phaseResponse.getName(), PhaseState.FAIL);
        }
    }

    /**
     * Moves next phase (the one after the supplied one) to IN_PROGRESS, and calls its URI.
     * 
     * @param phaseName
     */
    private void runNextPhase(String projectName, String versionNumber, int buildNumber, String phaseName) {

        PhasesResponse phasesResponse = phaseService.getPhases(projectName, versionNumber);
        List<PhaseResponse> phaseResponses = phasesResponse.getPhases();

        PhaseResponse currentPhase = new PhaseResponse();
        currentPhase.setName(phaseName);

        int index = phaseResponses.indexOf(currentPhase) + 1;
        int lastIndex = phaseResponses.size() - 1;

        /* last phase - nothing to do */
        if (index > lastIndex) {
            return;
        }

        PhaseResponse nextPhase = phaseResponses.get(index);
        buildPhaseWriteDao.addPhase(projectName, versionNumber, buildNumber, nextPhase.getName());

        /* state already set to IN_PROGRESS, no need to run 'runPhase' */
        sendPhaseRequest(projectName, versionNumber, buildNumber, nextPhase);
    }

    private LinksResponse getBuildPhasesLinks(String projectName, String versionNumber, int buildNumber, String phaseName) {

        String buildPhaseUri = linkService.getBuildPhaseUriString(projectName, versionNumber, buildNumber, phaseName);
        List<LinkResponse> links = new ArrayList<LinkResponse>();

        /* GET - specific project */
        links.add(linkService.getLink(buildPhaseUri, "build phase details"));

        LinksResponse linksResponse = new LinksResponse();
        linksResponse.setLinks(links);
        return linksResponse;
    }

    private LinksResponse getBuildPhaseLinks(String projectName, String versionNumber, int buildNumber, String phaseName) {

        String buildPhaseUri = linkService.getBuildPhaseUriString(projectName, versionNumber, buildNumber, phaseName);
        List<LinkResponse> links = new ArrayList<LinkResponse>();

        /* POST - add new state */
        String stateSchemaLocation = String.format("%s/%s", linkService.getSchemaRequestUriString(), ResourceUtil.STATE_PARAM);
        links.add(linkService.getLink(buildPhaseUri, "add new phase state", "POST", stateSchemaLocation));

        LinksResponse linksResponse = new LinksResponse();
        linksResponse.setLinks(links);
        return linksResponse;
    }
}
