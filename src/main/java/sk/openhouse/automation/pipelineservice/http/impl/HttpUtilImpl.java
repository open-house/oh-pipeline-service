package sk.openhouse.automation.pipelineservice.http.impl;

import java.net.URI;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import sk.openhouse.automation.pipelineservice.http.HttpUtil;

/**
 * 
 * @author pete
 */
public class HttpUtilImpl implements HttpUtil {

    private static final Logger logger = Logger.getLogger(HttpUtilImpl.class);

    private final Client client;

    /**
     * @param client
     */
    public HttpUtilImpl(Client client) {
        this.client = client;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean sendRequest(URI requestUri, MultivaluedMap<String, String> params) {

        WebResource webResource = getWebResource(requestUri, params);
        return sendRequest(webResource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean sendRequest(URI requestUri, MultivaluedMap<String, String> params, String username, byte[] password) {

        logger.debug("Sending request with HTTP Basic Auth filter.");
        WebResource webResource = getWebResource(requestUri, params);
        webResource.addFilter(new HTTPBasicAuthFilter(username, password));
        return sendRequest(webResource);
    }

    /**
     * returns new web resource for specified uri and with specified arguments
     */
    private WebResource getWebResource(URI requestUri, MultivaluedMap<String, String> params) {

        logger.debug(String.format("Creating request to %s with arguments %s", 
                requestUri.toString(), params.toString()));
        return client.resource(requestUri).queryParams(params);
    }

    /**
     * sends request, returns true if successful, false otherwise
     */
    private boolean sendRequest(WebResource webResource) {

        ClientResponse clientResponse;
        try {
            clientResponse = webResource.get(ClientResponse.class);
        } catch(ClientHandlerException e) {
            logger.error("Client failed to process request/response.");
            return false;
        }

        if (clientResponse.getStatus() >= 300) {
            logger.error(String.format("Request was not successful. Returne response code is %d",
                    clientResponse.getStatus()));
            return false;
        }
        return true;
    }
}
