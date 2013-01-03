package sk.openhouse.automation.pipelineservice.service.impl;

import sk.openhouse.automation.pipelineservice.dao.PhaseReadDao;
import sk.openhouse.automation.pipelineservice.dao.PhaseWriteDao;
import sk.openhouse.automation.pipelinedomain.domain.request.PhaseRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.PhaseResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.PhasesResponse;
import sk.openhouse.automation.pipelineservice.service.PhaseService;
import sk.openhouse.automation.pipelineservice.service.VersionService;
import sk.openhouse.automation.pipelineservice.service.exception.BadRequestException;
import sk.openhouse.automation.pipelineservice.service.exception.NotFoundException;

public class PhaseServiceImpl implements PhaseService {

    private final PhaseReadDao phaseReadDao;
    private final PhaseWriteDao phaseWriteDao;
    private final VersionService versionService;

    public PhaseServiceImpl(PhaseReadDao phaseReadDao, PhaseWriteDao phaseWriteDao, VersionService versionService) {

        this.phaseReadDao = phaseReadDao;
        this.phaseWriteDao = phaseWriteDao;
        this.versionService = versionService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PhaseResponse getFirstPhase(String projectName, String versionNumber) {

        PhaseResponse phase = phaseReadDao.getFirstPhase(projectName, versionNumber);
        if (null == phase) {
            throw new NotFoundException(String.format("No phases found for project %s and version %s.",
                    projectName, versionNumber));
        }

        return phase;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PhaseResponse getPhase(String projectName, String versionNumber, String phaseName) {

        PhaseResponse phase = phaseReadDao.getPhase(projectName, versionNumber, phaseName);
        if (null == phase) {
            throw new NotFoundException(String.format("Phase %s for project %s and version %s cannot be found.",
                    phaseName, projectName, versionNumber));
        }

        return phase;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PhasesResponse getPhases(String projectName, String versionNumber) {

        PhasesResponse phasesResponse = phaseReadDao.getPhases(projectName, versionNumber);
        if (phasesResponse.getPhases().isEmpty()) {
            /* check if version exists - service will throw NotFoundException if it doesn't */
            versionService.getVersion(projectName, versionNumber);
        }
        return phasesResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPhase(String projectName, String versionNumber, String phaseName, PhaseRequest phaseRequest) {

        if (null == phaseRequest.getName()) {
            phaseRequest.setName(phaseName);
        }

        String phaseRequestName = phaseRequest.getName();
        if (!phaseRequestName.equals(phaseName)) {
            String messageTemplate = "Phase name in the request (%s) does not match the one in the url (%s)."
                    + " Either remove name element from the request, or use the same name as in the url.";
            throw new BadRequestException(String.format(messageTemplate, phaseRequestName, phaseName));
        }
        phaseWriteDao.addPhase(projectName, versionNumber, phaseRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePhase(String projectName, String versionNumber, String phaseName, PhaseRequest phaseRequest) {

        if (null == phaseRequest.getName()) {
            phaseRequest.setName(phaseName);
        }
        phaseWriteDao.updatePhase(projectName, versionNumber, phaseName, phaseRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePhase(String projectName, String versionNumber, String phaseName) {
        phaseWriteDao.deletePhase(projectName, versionNumber, phaseName);
    }
}
