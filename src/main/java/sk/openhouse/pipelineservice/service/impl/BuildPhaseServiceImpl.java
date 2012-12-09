package sk.openhouse.pipelineservice.service.impl;

import com.sun.jersey.api.ConflictException;
import com.sun.jersey.api.NotFoundException;

import sk.openhouse.pipelineservice.dao.BuildPhaseReadDao;
import sk.openhouse.pipelineservice.dao.BuildPhaseWriteDao;
import sk.openhouse.pipelineservice.domain.PhaseState;
import sk.openhouse.pipelineservice.domain.request.StateRequest;
import sk.openhouse.pipelineservice.domain.response.BuildPhaseResponse;
import sk.openhouse.pipelineservice.domain.response.BuildPhasesResponse;
import sk.openhouse.pipelineservice.service.BuildPhaseService;

public class BuildPhaseServiceImpl implements BuildPhaseService {

    private BuildPhaseReadDao buildPhaseReadDao;
    private BuildPhaseWriteDao buildPhaseWriteDao;

    public BuildPhaseServiceImpl(BuildPhaseReadDao buildPhaseReadDao, BuildPhaseWriteDao buildPhaseWriteDao) {

        this.buildPhaseReadDao = buildPhaseReadDao;
        this.buildPhaseWriteDao = buildPhaseWriteDao;
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

        /* failed build can only by changed to in progress */
        if (lastPhaseState.equals(PhaseState.FAIL) && !state.equals(PhaseState.IN_PROGRESS)) {
            throw new ConflictException("Failed phase can only be moved to in progress.");
        }
        buildPhaseWriteDao.addState(projectName, versionNumber, buildNumber, phaseName, stateRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildPhasesResponse getBuildPhases(String projectName, String versionNumber, int buildNumber) {
        return buildPhaseReadDao.getBuildPhases(projectName, versionNumber, buildNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildPhaseResponse getBuildPhase(String projectName, String versionNumber, int buildNumber, String phaseName) {
        return buildPhaseReadDao.getBuildPhase(projectName, versionNumber, buildNumber, phaseName);
    }
}
