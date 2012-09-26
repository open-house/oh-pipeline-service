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
