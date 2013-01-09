package sk.openhouse.automation.pipelineservice.dao;

import sk.openhouse.automation.pipelinedomain.domain.PhaseState;

/**
 * Write DAO for for build phases. Build phase is a phase that the build has already been through.
 * 
 * @author pete
 */
public interface BuildPhaseWriteDao {

    /**
     * Adds new build phase with a new IN_PROGRESS state.
     * 
     * @param projectName name of the project
     * @param versionNumber version number of the project
     * @param buildNumber build number
     * @param phaseName phase name
     */
    void addPhase(String projectName, String versionNumber, int buildNumber, String phaseName);

    /**
     * Adds new state to the specified phase.
     * 
     * @param projectName name of the project
     * @param versionNumber version number of the project
     * @param buildNumber build number
     * @param phaseName phase name
     * @param phaseState phase state to be added to the specified phase
     */
    void addState(String projectName, String versionNumber, int buildNumber, String phaseName, PhaseState phaseState);
}
