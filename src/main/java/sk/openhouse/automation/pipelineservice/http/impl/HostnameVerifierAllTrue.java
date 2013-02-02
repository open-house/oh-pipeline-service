package sk.openhouse.automation.pipelineservice.http.impl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Verifies all hosts successfully. verify method returns always true
 *
 * @author pete
 */
public class HostnameVerifierAllTrue implements HostnameVerifier {

    /**
     * @return always true
     */
    @Override
    public boolean verify(String s, SSLSession sslSession) {
        return true;
    }
}
