package sk.openhouse.automation.pipelineservice.http.impl;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * X509 Trust Manager that accepts all certificates
 *
 * @author pete
 */
public class X509TrustManagerAllCerts implements X509TrustManager {

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] certs, String authType){}

    @Override
    public void checkServerTrusted(X509Certificate[] certs, String authType){}
}
