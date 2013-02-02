package sk.openhouse.automation.pipelineservice.http.impl;

import sk.openhouse.automation.pipelineservice.http.SSLContextFactory;

import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

import javax.net.ssl.HostnameVerifier;

/**
 * Client config with HTTPS properties set to supplied SSL context and hostname verifier
 *
 * @author pete
 */
public class ClientConfigHttps extends DefaultClientConfig {

    public ClientConfigHttps(SSLContextFactory sslContextFactory, HostnameVerifier hostnameVerifier) {

        super();
        getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES,
                new HTTPSProperties(hostnameVerifier, sslContextFactory.getSSLContext()));
    }
}
