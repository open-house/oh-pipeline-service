package sk.openhouse.automation.pipelineservice.service.exception;

/**
 * Exception indicating that the requested resource cannot be found
 * 
 * @author pete
 */
@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException {

    /**
     * Constructs new exception with the supplied message
     * 
     * @param message
     */
    public NotFoundException(String message) {
        super(message);
    }
}
