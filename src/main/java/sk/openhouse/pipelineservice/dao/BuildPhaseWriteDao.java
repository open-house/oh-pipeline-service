package sk.openhouse.pipelineservice.dao;

import sk.openhouse.pipelineservice.domain.request.StateRequest;

/**
 * Write DAO for for build phases
 * 
 * @author pete
 */
public interface BuildPhaseWriteDao {

    /**
     * Adds new build phase with state set to IN_PROGRESS.
     * 
     * @param projectName
     * @param versionNumber
     * @param buildNumber
     * @param phaseName
     */
    void addPhase(String projectName, String versionNumber, int buildNumber, String phaseName);

    /**
     * Adds new state to the specified phase.
     * 
     * @param projectName
     * @param versionNumber
     * @param buildNumber
     * @param phaseName
     * @param stateRequest
     */
    void addState(String projectName, String versionNumber, int buildNumber, String phaseName, StateRequest stateRequest);
}
