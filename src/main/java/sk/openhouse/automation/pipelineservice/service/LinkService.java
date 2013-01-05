package sk.openhouse.automation.pipelineservice.service;

import sk.openhouse.automation.pipelinedomain.domain.response.LinkResponse;

/**
 * Service for generating resources
 * 
 * @author pete
 */
public interface LinkService {

    /**
     * @return URI for all projects
     */
    String getProjectsUriString();

    /**
     * @return URI template for (adding) a single project
     */
    String getProjectUriTemplate();

    /**
     * @param projectName for which the URI will be constructed
     * @return URI for the specified project
     */
    String getProjectUriString(String projectName);

    /**
     * @param projectName for which the versions URI will be constructed
     * @return URI for the specified project versions
     */
    String getVersionstUriString(String projectName);

    /**
     * @param projectName for which the phases URI will be constructed
     * @return URI for the specified project phases
     */
    String getPhasesUriString(String projectName);

    /**
     * Constructs and returns link response with method set to GET
     * 
     * @param uriString
     * @param description
     * @return
     * @throws IllegalArgumentException if the supplied uri string is invalid
     */
    LinkResponse getLink(String uriString, String description) throws IllegalArgumentException;

    /**
     * Constructs and returns link response
     * 
     * @param uriString
     * @param description
     * @param method GET, PUT, POST etc.
     * @return
     * @throws IllegalArgumentException if the supplied uri string is invalid
     */
    LinkResponse getLink(String uriString, String description, String method) throws IllegalArgumentException;

    /**
     * Constructs and returns link response
     * 
     * @param uriString
     * @param description
     * @param method GET, PUT, POST etc.
     * @param relativeSchemaURI schema location
     * @return
     * @throws IllegalArgumentException if the supplied uri (or schema uri) string is invalid
     */
    LinkResponse getLink(String uriString, String description, String method, String schemaURIString)
            throws IllegalArgumentException;
}
