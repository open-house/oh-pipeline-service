package sk.openhouse.automation.pipelineservice.dao;

import sk.openhouse.automation.pipelineservice.domain.response.PhaseResponse;
import sk.openhouse.automation.pipelineservice.domain.response.PhasesResponse;

/**
 * Read dao for phase(s)
 * 
 * @author pete
 */
public interface PhaseReadDao {

    /**
     * Returns a phase that has been added as the first one (sorted by timestamp)
     * 
     * @param projectName
     * @param versionNumber
     * @return
     */
    PhaseResponse getFirstPhase(String projectName, String versionNumber);

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
