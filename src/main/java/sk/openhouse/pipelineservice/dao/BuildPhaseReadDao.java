package sk.openhouse.pipelineservice.dao;

import sk.openhouse.pipelineservice.domain.PhaseState;
import sk.openhouse.pipelineservice.domain.response.BuildPhaseResponse;
import sk.openhouse.pipelineservice.domain.response.BuildPhasesResponse;

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

    /**
     * @param projectName
     * @param versionNumber
     * @param buildNumber
     * @return build phases
     */
    BuildPhasesResponse getBuildPhases(String projectName, String versionNumber, int buildNumber);

    /**
     * @param projectName
     * @param versionNumber
     * @param buildNumber
     * @param phaseName
     * @return specific build phase
     */
    BuildPhaseResponse getBuildPhase(String projectName, String versionNumber, int buildNumber, String phaseName);
}
