package sk.openhouse.automation.pipelineservice.util;

import java.net.URI;

import javax.ws.rs.core.MultivaluedMap;

/**
 * Common HTTP utils
 * 
 * @author pete
 */
public interface HttpUtil {

    /**
     * Sends HTTP request to specified URL
     * 
     * @param requestUri
     * @param params query parameters to be sent
     * @return true if request was successful and returned code is less than 300
     */
    boolean sendRequest(URI requestUri, MultivaluedMap<String, String> params);
}
