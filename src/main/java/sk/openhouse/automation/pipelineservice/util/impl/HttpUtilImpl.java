package sk.openhouse.automation.pipelineservice.util.impl;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.log4j.Logger;

import sk.openhouse.automation.pipelineservice.util.HttpUtil;

public class HttpUtilImpl implements HttpUtil {

    private static final Logger logger = Logger.getLogger(HttpUtilImpl.class);

    private final HttpClient httpClient;

    /**
     * @param httpClient
     */
    public HttpUtilImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean sendPostRequest(URI requestUri) {

        HttpResponse response = null;
        String logErrorMessage = String.format("Error sending request to %s", requestUri.toString());
        try {
            response = httpClient.execute(new HttpPost(requestUri));
        } catch (ClientProtocolException e) {
            logger.error(logErrorMessage, e);
        } catch (IOException e) {
            logger.error(logErrorMessage, e);
        }

        return (null == response || response.getStatusLine().getStatusCode() != 200) 
                ? false : true;
    }
}
