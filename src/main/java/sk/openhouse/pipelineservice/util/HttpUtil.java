package sk.openhouse.pipelineservice.util;

import java.net.URI;

import sk.openhouse.pipelineservice.domain.response.ResourceResponse;

/**
 * Common http utils
 * 
 * @author pete
 */
public interface HttpUtil {

    /**
     * This should be used instead of servlet request, because the service can sit behind
     * proxy, firewall etc.
     * 
     * @return root url without trailing slash
     */
    String getRootURI();

    /**
     * Constructs absolute URI from supplied relative URI string
     * 
     * @param relativeURI
     * @return absolute URI
     * @throws IllegalArgumentException if the relative URI is invalid
     */
    URI getAbsoluteURI(String relativeURI);

    /**
     * Returns relative URI for all projects
     */
    String getProjectsRelativeURI();

    /**
     * Returns relative URI for a specified project
     */
    String getProjectRelativeURI(String projectName);

    /**
     * Returns relative URI for a specified project version
     */
    String getVersionRelativeURI(String projectName, String versionNumber);

    /**
     * Constructs and returns resource response with method set to GET
     * 
     * @param relativeURI
     * @param description
     * @return
     */
    ResourceResponse getResource(String relativeURI, String description);

    /**
     * Constructs and returns resource response
     * 
     * @param relativeURI
     * @param description
     * @param method GET, PUT, POST etc.
     * @return
     */
    ResourceResponse getResource(String relativeURI, String description, String method);

    /**
     * Constructs and returns resource response
     * 
     * @param relativeURI
     * @param description
     * @param method GET, PUT, POST etc.
     * @param relativeSchemaURI schema location
     * @return
     */
    ResourceResponse getResource(String relativeURI, String description, String method, String relativeSchemaURI);
}
