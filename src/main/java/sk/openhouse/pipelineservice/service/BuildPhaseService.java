package sk.openhouse.pipelineservice.service;

import sk.openhouse.pipelineservice.domain.request.StateRequest;
import sk.openhouse.pipelineservice.domain.response.BuildPhaseResponse;
import sk.openhouse.pipelineservice.domain.response.BuildPhasesResponse;
import sk.openhouse.pipelineservice.service.exception.ConflictException;

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
     * state is not added. Only if the last state is IN_PROGRESS or FAIL update can be made.
     * In case of IN_PROGRESS being the last state, next state can be SUCCESS or FAIL.
     * In case of FAIL being the last state, next state can only be SUCCESS (then the phase will be repeated).
     * SUCCESS state cannot be failed, because the next phase is called automatically, so the FAIL would not have any
     * effect.
     * 
     * @param projectName
     * @param versionNumber
     * @param buildNumber
     * @param phaseName
     * @param stateRequest
     * @throws ConflictException if the state cannot be updated because of the rules
     */
    void addState(String projectName, String versionNumber, int buildNumber, String phaseName, StateRequest stateRequest)
            throws ConflictException;
}
