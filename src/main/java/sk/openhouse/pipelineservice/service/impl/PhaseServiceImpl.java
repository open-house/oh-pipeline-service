package sk.openhouse.pipelineservice.service.impl;

import sk.openhouse.pipelineservice.dao.PhaseReadDao;
import sk.openhouse.pipelineservice.domain.response.PhaseResponse;
import sk.openhouse.pipelineservice.domain.response.PhasesResponse;
import sk.openhouse.pipelineservice.service.PhaseService;

// TODO - add 'add, update and delete' methods
public class PhaseServiceImpl implements PhaseService {

    private PhaseReadDao phaseReadDao;

    public PhaseServiceImpl(PhaseReadDao phaseReadDao) {
        this.phaseReadDao = phaseReadDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PhaseResponse getPhase(String projectName, String phaseName) {
        return phaseReadDao.getPhase(projectName, phaseName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PhasesResponse getPhases(String projectName) {
        return phaseReadDao.getPhases(projectName);
    }
}
