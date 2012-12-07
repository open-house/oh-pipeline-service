package sk.openhouse.pipelineservice.service.impl;

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

    @Override
    public void addState(String projectName, String versionNumber, int buildNumber, String phaseName, StateRequest stateRequest) {

        PhaseState lastPhaseState = buildPhaseReadDao.getLastState(projectName, versionNumber, buildNumber, phaseName);
        if (null == lastPhaseState) {
            throw new NotFoundException(String.format("No state found for project %s version %s build %d and phase %s",
                    projectName, versionNumber, buildNumber, phaseName));
        }

        /* already updated */
        PhaseState state = stateRequest.getName();
        if (state.equals(lastPhaseState)) {
            return;
        }

        buildPhaseWriteDao.addState(projectName, versionNumber, buildNumber, phaseName, stateRequest);
    }

    @Override
    public BuildPhasesResponse getBuildPhases(String projectName, String versionNumber, int buildNumber) {
        return buildPhaseReadDao.getBuildPhases(projectName, versionNumber, buildNumber);
    }

    @Override
    public BuildPhaseResponse getBuildPhase(String projectName, String versionNumber, int buildNumber, String phaseName) {
        return buildPhaseReadDao.getBuildPhase(projectName, versionNumber, buildNumber, phaseName);
    }
}
