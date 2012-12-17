package sk.openhouse.automation.pipelineservice.service.exception;

/**
 * Exception indicating that the requested conflicts with some rule already established.
 * 
 * @author pete
 */
@SuppressWarnings("serial")
public class ConflictException extends RuntimeException {

    /**
     * Constructs new exception with the supplied message
     * 
     * @param message
     */
    public ConflictException(String message) {
        super(message);
    }
}
