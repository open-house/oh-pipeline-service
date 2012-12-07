package sk.openhouse.pipelineservice.service;

import sk.openhouse.pipelineservice.domain.request.StateRequest;
import sk.openhouse.pipelineservice.domain.response.BuildPhaseResponse;
import sk.openhouse.pipelineservice.domain.response.BuildPhasesResponse;

/**
 * Service for build phases
 * 
 * @author pete
 */
public interface BuildPhaseService {

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

    /**
     * Adds state for specified build and phase. State has to be different than the last state, otherwise
     * state is not added.
     * 
     * @param projectName
     * @param versionNumber
     * @param buildNumber
     * @param phaseName
     * @param stateRequest
     */
    void addState(String projectName, String versionNumber, int buildNumber, String phaseName, StateRequest stateRequest);
}
