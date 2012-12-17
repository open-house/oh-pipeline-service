package sk.openhouse.automation.pipelineservice.domain;

/**
 * State of the build phase
 * 
 * @author pete
 */
public enum PhaseState {

    /** Phase is in progress */
    IN_PROGRESS,

    /** Phase has finished successfully, build will move to the next phase if any */
    SUCCESS,

    /** Phase has failed, build will terminate */
    FAIL
}
