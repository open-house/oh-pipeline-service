package sk.openhouse.automation.pipelineservice.http;

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

    /**
     * Sends HTTP request with HTTP Basic authentication to specified URL
     * 
     * @param requestUri
     * @param params query parameters to be sent
     * @param username used for HTTP Basic authentication
     * @param password used for HTTP Basic authentication
     * @return true if request was successful and returned code is less than 300
     */
    boolean sendRequest(URI requestUri, MultivaluedMap<String, String> params, String username, byte[] password);
}
