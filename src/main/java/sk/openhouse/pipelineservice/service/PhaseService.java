package sk.openhouse.pipelineservice.service;

import sk.openhouse.pipelineservice.domain.response.PhaseResponse;
import sk.openhouse.pipelineservice.domain.response.PhasesResponse;

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
     */
    PhaseResponse getPhase(String projectName, String versionNumber, String phaseName);

    /**
     * @param projectName
     * @param versionNumber
     * @return phases for specified project version
     */
    PhasesResponse getPhases(String projectName, String versionNumber);
}
