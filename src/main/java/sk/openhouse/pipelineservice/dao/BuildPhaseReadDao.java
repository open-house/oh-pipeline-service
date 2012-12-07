package sk.openhouse.pipelineservice.dao;

import sk.openhouse.pipelineservice.domain.PhaseState;

/**
 * Read DAO for build phases
 * 
 * @author pete
 */
public interface BuildPhaseReadDao {

    /**
     * @param projectName
     * @param versionNumber
     * @param buildNumber
     * @param phaseName
     * @return last state (ordering is done by timestamp) of the specified phase
     */
    PhaseState getLastState(String projectName, String versionNumber, int buildNumber, String phaseName);
}
