package sk.openhouse.automation.pipelineservice.service;

import sk.openhouse.automation.pipelinedomain.domain.response.ResourceResponse;

/**
 * Service for generating resources
 * 
 * @author pete
 */
public interface ResourceService {

    /**
     * @return URI for all projects
     */
    String getProjectsURIString();

    /**
     * Constructs and returns resource response with method set to GET
     * 
     * @param uriString
     * @param description
     * @return
     * @throws IllegalArgumentException if the supplied uri string is invalid
     */
    ResourceResponse getResource(String uriString, String description) throws IllegalArgumentException;

    /**
     * Constructs and returns resource response
     * 
     * @param uriString
     * @param description
     * @param method GET, PUT, POST etc.
     * @return
     * @throws IllegalArgumentException if the supplied uri string is invalid
     */
    ResourceResponse getResource(String uriString, String description, String method) throws IllegalArgumentException;

    /**
     * Constructs and returns resource response
     * 
     * @param uriString
     * @param description
     * @param method GET, PUT, POST etc.
     * @param relativeSchemaURI schema location
     * @return
     * @throws IllegalArgumentException if the supplied uri (or schema uri) string is invalid
     */
    ResourceResponse getResource(String uriString, String description, String method, String schemaURIString)
            throws IllegalArgumentException;
}
