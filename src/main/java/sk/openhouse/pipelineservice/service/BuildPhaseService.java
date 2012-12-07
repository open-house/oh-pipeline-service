package sk.openhouse.pipelineservice.service;

import sk.openhouse.pipelineservice.domain.PhaseState;

/**
 * Service for build phases
 * 
 * @author pete
 */
public interface BuildPhaseService {

    /**
     * Adds state for specified build and phase. State has to be different than the last state, otherwise
     * state is not added.
     * 
     * @param projectName
     * @param versionNumber
     * @param buildNumber
     * @param phaseName
     * @param state
     */
    void addState(String projectName, String versionNumber, int buildNumber, String phaseName, PhaseState state);
}
