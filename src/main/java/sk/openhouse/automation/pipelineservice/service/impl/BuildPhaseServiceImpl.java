package sk.openhouse.automation.pipelineservice.service.impl;

import java.util.List;

import sk.openhouse.automation.pipelineservice.dao.BuildPhaseReadDao;
import sk.openhouse.automation.pipelineservice.dao.BuildPhaseWriteDao;
import sk.openhouse.automation.pipelineservice.dao.PhaseReadDao;
import sk.openhouse.automation.pipelinedomain.domain.PhaseState;
import sk.openhouse.automation.pipelinedomain.domain.request.StateRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildPhaseResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildPhasesResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.PhaseResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.PhasesResponse;
import sk.openhouse.automation.pipelineservice.service.BuildPhaseService;
import sk.openhouse.automation.pipelineservice.service.exception.ConflictException;
import sk.openhouse.automation.pipelineservice.service.exception.NotFoundException;
import sk.openhouse.automation.pipelineservice.util.HttpUtil;

public class BuildPhaseServiceImpl implements BuildPhaseService {

    private BuildPhaseReadDao buildPhaseReadDao;
    private BuildPhaseWriteDao buildPhaseWriteDao;
    private PhaseReadDao phaseReadDao;
    private HttpUtil httpUtil;

    public BuildPhaseServiceImpl(BuildPhaseReadDao buildPhaseReadDao, BuildPhaseWriteDao buildPhaseWriteDao,
            PhaseReadDao phaseReadDao, HttpUtil httpUtil) {

        this.buildPhaseReadDao = buildPhaseReadDao;
        this.buildPhaseWriteDao = buildPhaseWriteDao;
        this.phaseReadDao = phaseReadDao;
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

        buildPhaseWriteDao.addState(projectName, versionNumber, buildNumber, phaseName, state);

        /* failed state updated to in progress - rerun the phase */
        if (state.equals(PhaseState.IN_PROGRESS)) {
            PhaseResponse phaseResponse = phaseReadDao.getPhase(projectName, versionNumber, phaseName);
            runPhase(projectName, versionNumber, buildNumber, phaseResponse);
            return;
        }

        /* success - move to the next phase */
        if (state.equals(PhaseState.SUCCESS)) {
            runNextPhase(projectName, versionNumber, buildNumber, phaseName);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildPhasesResponse getBuildPhases(String projectName, String versionNumber, int buildNumber) {

        BuildPhasesResponse buildPhasesResponse = buildPhaseReadDao.getBuildPhases(projectName, versionNumber, buildNumber);
        if (null == buildPhasesResponse) {
            throw new NotFoundException(String.format("No phases found for project %s version %s and build %d.", 
                    projectName, versionNumber, buildNumber));
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
        return buildPhaseResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void runPhase(String projectName, String versionNumber, int buildNumber, PhaseResponse phaseResponse) {

        /* call uri for the first phase */
        if (!httpUtil.sendPostRequest(phaseResponse.getUri())) {
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

        PhasesResponse phasesResponse = phaseReadDao.getPhases(projectName, versionNumber);
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
        runPhase(projectName, versionNumber, buildNumber, nextPhase);
    }
}
