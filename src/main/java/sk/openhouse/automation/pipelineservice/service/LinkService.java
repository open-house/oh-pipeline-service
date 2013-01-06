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
    String getVersionsUriString(String projectName);

    /**
     * @param projectName
     * @return URI template for (adding) a project version
     */
    String getVersionUriTemplate(String projectName);

    /**
     * @param projectName
     * @param versionNumber
     * @return URI to specified project and version
     */
    String getVersionUriString(String projectName, String versionNumber);

    /**
     * @param projectName
     * @param versionNumber
     * @return URI for the specified project version
     */
    String getPhasesUriString(String projectName, String versionNumber);

    /**
     * 
     * @param projectName
     * @param versionNumber
     * @param phaseName
     * @return URI for the specified project phase
     */
    String getPhaseUriString(String projectName, String versionNumber, String phaseName);

    /**
     * @param projectName
     * @param versionNumber
     * @return URI template for a phase
     */
    String getPhaseUriTemplate(String projectName, String versionNumber);

    /**
     * @param projectName
     * @param versionNumber
     * @return URI for the specified project version
     */
    String getBuildsUriString(String projectName, String versionNumber);

    /**
     * @param projectName
     * @param versionNumber
     * @param buildNumber
     * @return URI for the specified project build
     */
    String getBuildUriString(String projectName, String versionNumber, int buildNumber);

    /**
     * @param projectName
     * @param versionNumber
     * @return URI template to add new build
     */
    String getBuildUriTemplate(String projectName, String versionNumber);

    /**
     * @param projectName
     * @param versionNumber
     * @param buildNumber
     * @return URI for the specified build phases
     */
    String getBuildPhasesUriString(String projectName, String versionNumber, int buildNumber);

    /**
     * @param projectName
     * @param versionNumber
     * @param buildNumber
     * @param phaseName
     * @return URI for the specific build phase
     */
    String getBuildPhaseUriString(String projectName, String versionNumber, int buildNumber, String phaseName);

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
