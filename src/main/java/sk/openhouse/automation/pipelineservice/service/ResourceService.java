package sk.openhouse.automation.pipelineservice.service;

import sk.openhouse.automation.pipelinedomain.domain.response.ResourceResponse;

/**
 * Service for generating resources
 * 
 * @author pete
 */
public interface ResourceService {

    /**
     * This should be used instead of servlet request, because the service can sit behind
     * proxy, firewall etc.
     * 
     * @return root url without trailing slash
     */
    String getRootURIString();

    /**
     * @return URI for all projects
     */
    String getProjectsURIString();

    /**
     * There is no validation, therefore URI is not valid if the argument(s) is not valid URI part
     * 
     * @param projectName
     * @return URI for a specified project.
     */
    String getProjectURIString(String projectName);

    /**
     * There is no validation, therefore URI is not valid if the argument(s) is not valid URI part
     * 
     * @param projectName
     * @return URI for versions of a specified project
     */
    String getVersionsURIString(String projectName);

    /**
     * There is no validation, therefore URI is not valid if the argument(s) is not valid URI part
     * 
     * @param projectName
     * @param versionNumber
     * @return URI for phases of a specified project
     */
    String getPhasesURIString(String projectName, String versionNumber);

    /**
     * There is no validation, therefore URI is not valid if the argument(s) is not valid URI part
     * 
     * @param projectName
     * @param versionNumber
     * @return URI for a specified project and its version
     */
    String getVersionURIString(String projectName, String versionNumber);

    /**
     * There is no validation, therefore URI is not valid if the argument(s) is not valid URI part
     * 
     * @param projectName
     * @param versionNumber
     * @return URI for builds of a specified project and its version
     */
    String getBuildsURIString(String projectName, String versionNumber);

    /**
     * There is no validation, therefore URI is not valid if the argument(s) is not valid URI part
     * 
     * @param projectName
     * @param versionNumber
     * @param buildNumber
     * @return URI for build of a specified project and its version
     */
    String getBuildURIString(String projectName, String versionNumber, int buildNumber);

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
