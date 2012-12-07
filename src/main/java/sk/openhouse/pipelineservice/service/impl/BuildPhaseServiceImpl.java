package sk.openhouse.pipelineservice.service.impl;

import com.sun.jersey.api.NotFoundException;

import sk.openhouse.pipelineservice.dao.BuildPhaseReadDao;
import sk.openhouse.pipelineservice.dao.BuildPhaseWriteDao;
import sk.openhouse.pipelineservice.domain.PhaseState;
import sk.openhouse.pipelineservice.service.BuildPhaseService;

public class BuildPhaseServiceImpl implements BuildPhaseService {

    private BuildPhaseReadDao buildPhaseReadDao;
    private BuildPhaseWriteDao buildPhaseWriteDao;

    public BuildPhaseServiceImpl(BuildPhaseReadDao buildPhaseReadDao, BuildPhaseWriteDao buildPhaseWriteDao) {

        this.buildPhaseReadDao = buildPhaseReadDao;
        this.buildPhaseWriteDao = buildPhaseWriteDao;
    }

    @Override
    public void addState(String projectName, String versionNumber, int buildNumber, String phaseName, PhaseState state) {

        PhaseState lastPhaseState = buildPhaseReadDao.getLastState(projectName, versionNumber, buildNumber, phaseName);
        if (null == lastPhaseState) {
            throw new NotFoundException(String.format("No state found for project %s version %s build %d and phase %s",
                    projectName, versionNumber, buildNumber, phaseName));
        }

        /* already updated */
        if (state.equals(lastPhaseState)) {
            return;
        }

        buildPhaseWriteDao.addState(projectName, versionNumber, buildNumber, phaseName, lastPhaseState);
    }

}
