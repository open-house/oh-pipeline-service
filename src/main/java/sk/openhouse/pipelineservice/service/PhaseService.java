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
     * @param phaseName
     * @return specific phase
     */
    PhaseResponse getPhase(String projectName, String phaseName);

    /**
     * @param projectName
     * @return phases for specified project
     */
    PhasesResponse getPhases(String projectName);
}
