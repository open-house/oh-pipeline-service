package sk.openhouse.automation.pipelineservice.http.impl;

import sk.openhouse.automation.pipelineservice.http.SSLContextFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Factory for SSLContext enabling user to set protocol and trust manager
 *
 * @author pete
 */
public class SSLContextFactoryImpl implements SSLContextFactory {

    private final SSLContext sslContext;

    public SSLContextFactoryImpl(String protocol, X509TrustManager x509TrustManager)
        throws NoSuchAlgorithmException, KeyManagementException {

        sslContext = SSLContext.getInstance(protocol);
        sslContext.init(null, new X509TrustManager[] {x509TrustManager}, null);
    }

    public SSLContext getSSLContext() {
        return sslContext;
    }
}
