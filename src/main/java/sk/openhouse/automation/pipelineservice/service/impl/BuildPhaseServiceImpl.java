package sk.openhouse.automation.pipelineservice.service.impl;

import java.net.URI;
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
import sk.openhouse.automation.pipelineservice.http.HttpUtil;

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

        PhaseState state = stateRequest.getName();
        PhaseResponse phaseResponse = phaseService.getPhase(projectName, versionNumber, phaseName);
        PhaseState lastPhaseState = buildPhaseReadDao.getLastState(projectName, versionNumber, buildNumber, phaseName);

        /* new build phase - the first state can only be IN_PROGRESS */
        if (null == lastPhaseState) {
            if (state.equals(PhaseState.IN_PROGRESS)) {
                runNewPhase(projectName, versionNumber, buildNumber, phaseResponse);
                return;
            }
            throw new ConflictException(String.format(
                    "No state found for project %s version %s build %d and phase %s. The first phase state can only be %s.",
                    projectName, versionNumber, buildNumber, phaseName, PhaseState.IN_PROGRESS));
        }

        /* build phase has already been successfully completed */
        if (lastPhaseState.equals(PhaseState.SUCCESS)) {
            throw new ConflictException(String.format("Phase %s has already been successfully completed.", phaseName));
        }

        /* already updated */
        if (state.equals(lastPhaseState)) {
            throw new ConflictException(String.format("Build phase is already in %s state.", state));
        }

        buildPhaseWriteDao.addState(projectName, versionNumber, buildNumber, phaseName, state);
        if (state.equals(PhaseState.IN_PROGRESS)) {
            sendPhaseRequest(projectName, versionNumber, buildNumber, phaseResponse);
        } else if (state.equals(PhaseState.SUCCESS)) {
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

        URI uri = phaseResponse.getUri();
        boolean success = (null == phaseResponse.getUsername()) 
                ? httpUtil.sendRequest(uri, params)
                : httpUtil.sendRequest(uri, params, phaseResponse.getUsername(), phaseResponse.getPassword());

        if (!success) {
            buildPhaseWriteDao.addState(projectName, versionNumber, buildNumber, 
                    phaseResponse.getName(), PhaseState.FAIL);
        }
    }

    /**
     * Moves next phase (the one after the supplied one) to IN_PROGRESS, and calls its URI.
     * 
     * @param phaseName current phase name
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
        if (nextPhase.isAuto()) {
            runNewPhase(projectName, versionNumber, buildNumber, nextPhase);
        }
    }

    /**
     * Runs specified phase. Phase has to be new, meaning that it does not have any states.
     */
    private void runNewPhase(String projectName, String versionNumber, int buildNumber, PhaseResponse phaseResponse) {

        buildPhaseWriteDao.addPhase(projectName, versionNumber, buildNumber, phaseResponse.getName());
        /* state already set to IN_PROGRESS, no need to run 'runPhase' */
        sendPhaseRequest(projectName, versionNumber, buildNumber, phaseResponse);
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
