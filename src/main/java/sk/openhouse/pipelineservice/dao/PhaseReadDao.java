package sk.openhouse.pipelineservice.dao;

import sk.openhouse.pipelineservice.domain.response.PhaseResponse;
import sk.openhouse.pipelineservice.domain.response.PhasesResponse;

/**
 * Read dao for phase(s)
 * 
 * @author pete
 */
public interface PhaseReadDao {

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
