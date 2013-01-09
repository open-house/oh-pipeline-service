package sk.openhouse.automation.pipelineservice.dao;

import sk.openhouse.automation.pipelinedomain.domain.PhaseState;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildPhaseResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildPhasesResponse;

/**
 * Read DAO for build phases. Build phase is a phase that the build has already been through.
 * 
 * @author pete
 */
public interface BuildPhaseReadDao {

    /**
     * @param projectName name of the project
     * @param versionNumber version number of the project
     * @param buildNumber build number
     * @param phaseName phase name
     * @return last state of the specified phase
     */
    PhaseState getLastState(String projectName, String versionNumber, int buildNumber, String phaseName);

    /**
     * @param projectName name of the project
     * @param versionNumber version number of the project
     * @param buildNumber build number
     * @return build phases that are completed, failed or in progress
     */
    BuildPhasesResponse getBuildPhases(String projectName, String versionNumber, int buildNumber);

    /**
     * @param projectName name of the project
     * @param versionNumber version number of the project
     * @param buildNumber build number
     * @param phaseName phase name
     * @return specific build phase
     */
    BuildPhaseResponse getBuildPhase(String projectName, String versionNumber, int buildNumber, String phaseName);
}
