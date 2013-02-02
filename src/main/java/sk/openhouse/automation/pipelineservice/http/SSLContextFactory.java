package sk.openhouse.automation.pipelineservice.http;

import javax.net.ssl.SSLContext;

/**
 *
 * @author  pete
 */
public interface SSLContextFactory {

    /**
     * @return initialized SSLContext
     */
    public SSLContext getSSLContext();
}
