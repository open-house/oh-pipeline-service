package sk.openhouse.automation.pipelineservice.dao;

import org.springframework.dao.DuplicateKeyException;

import sk.openhouse.automation.pipelinedomain.domain.request.PhaseRequest;

/**
 * Write DAO for phase(s). Phase is linked to a project and its version.
 * 
 * @author pete
 */
public interface PhaseWriteDao {

    /**
     * Adds new project phase
     * 
     * @param projectName name of the project
     * @param versionNumber version number of the project
     * @param phaseRequest
     * @throws DuplicateKeyException if the phase name already exists
     */
    void addPhase(String projectName, String versionNumber, PhaseRequest phaseRequest) throws DuplicateKeyException;

    /**
     * Updates existing phase
     * 
     * @param projectName name of the project
     * @param versionNumber version number of the project
     * @param phaseName name of the phase that will be updated
     * @param buildRequest new data to update the phase
     */
    void updatePhase(String projectName, String versionNumber, String phaseName, PhaseRequest phaseRequest);

    /**
     * Deletes project phase
     * 
     * @param projectName name of the project
     * @param versionNumber version number of the project
     * @param phaseName name of the to be deleted
     */
    void deletePhase(String projectName, String versionNumber, String phaseName);
}
