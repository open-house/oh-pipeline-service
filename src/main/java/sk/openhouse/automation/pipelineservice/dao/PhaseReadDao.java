package sk.openhouse.automation.pipelineservice.dao;

import sk.openhouse.automation.pipelinedomain.domain.response.PhaseResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.PhasesResponse;

/**
 * Read dao for phase(s). Phase is linked to a project and its version.
 * 
 * @author pete
 */
public interface PhaseReadDao {

    /**
     * Returns a phase that has been added as the first one
     * 
     * @param projectName name of the project
     * @param versionNumber version number of the project
     * @return first phase or null if no phases are set for the specified project and version
     */
    PhaseResponse getFirstPhase(String projectName, String versionNumber);

    /**
     * @param projectName name of the project
     * @param versionNumber version number of the project
     * @param phaseName
     * @return specific phase or null if no phases are set for the specified project and version
     */
    PhaseResponse getPhase(String projectName, String versionNumber, String phaseName);

    /**
     * @param projectName name of the project
     * @param versionNumber version number of the project
     * @return phases for specified project version
     */
    PhasesResponse getPhases(String projectName, String versionNumber);
}
