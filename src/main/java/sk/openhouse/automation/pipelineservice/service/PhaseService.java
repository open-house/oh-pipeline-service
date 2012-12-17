package sk.openhouse.automation.pipelineservice.service;

import sk.openhouse.automation.pipelineservice.domain.request.PhaseRequest;
import sk.openhouse.automation.pipelineservice.domain.response.PhaseResponse;
import sk.openhouse.automation.pipelineservice.domain.response.PhasesResponse;
import sk.openhouse.automation.pipelineservice.service.exception.NotFoundException;

/**
 * Project Phase(s) Service
 * 
 * @author pete
 */
public interface PhaseService {

    /**
     * @param projectName
     * @param versionNumber
     * @param phaseName
     * @return specific phase
     * @throws NotFoundException if the phase cannot be found
     */
    PhaseResponse getPhase(String projectName, String versionNumber, String phaseName) throws NotFoundException;

    /**
     * @param projectName
     * @param versionNumber
     * @return phases for specified project version
     */
    PhasesResponse getPhases(String projectName, String versionNumber);

    /**
     * Adds new or overrides existing (if this phase already exists) project phase
     * 
     * @param projectName
     * @param versionNumber
     * @param phaseName
     * @param phaseRequest
     */
    void addPhase(String projectName, String versionNumber, String phaseName, PhaseRequest phaseRequest);

    /**
     * Updates existing phase
     * 
     * @param projectName
     * @param versionNumber
     * @param phaseName
     * @param buildRequest
     */
    void updatePhase(String projectName, String versionNumber, String phaseName, PhaseRequest phaseRequest);

    /**
     * Deletes project phase
     * 
     * @param projectName
     * @param versionNumber
     * @param phaseName
     */
    void deletePhase(String projectName, String versionNumber, String phaseName);
}
