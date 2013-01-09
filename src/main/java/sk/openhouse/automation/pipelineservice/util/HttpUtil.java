package sk.openhouse.automation.pipelineservice.util;

import java.net.URI;

/**
 * Common HTTP utils
 * 
 * @author pete
 */
public interface HttpUtil {

    /**
     * Sends Post request to specified url
     * 
     * @param requestUri
     * @return true if request was successfull and returned 200 response code
     */
    boolean sendPostRequest(URI requestUri);
}
