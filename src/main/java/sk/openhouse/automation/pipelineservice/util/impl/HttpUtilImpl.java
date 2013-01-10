package sk.openhouse.automation.pipelineservice.util.impl;

import java.net.URI;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;

import sk.openhouse.automation.pipelineservice.util.HttpUtil;

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

        try {
            client.resource(requestUri).queryParams(params).get(String.class);
        } catch(UniformInterfaceException e) {
            logger.error(String.format("Request to %s with params %s was not successful.",
                    requestUri.toString(), params.toString()));
            return false;
        } catch(ClientHandlerException e) {
            logger.error(String.format("Client failed to process request/response to %s with params %s",
                    requestUri.toString(), params.toString()));
            return false;
        }

        return true;
    }
}
