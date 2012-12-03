package sk.openhouse.pipelineservice.service.exception;

/**
 * Exception indicating that the requested is incorrect (this is mainly if the request body is malformed)
 * 
 * @author pete
 */
@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException {

    /**
     * Constructs new exception with the supplied message
     * 
     * @param message
     */
    public BadRequestException(String message) {
        super(message);
    }
}
