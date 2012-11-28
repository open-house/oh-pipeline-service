package sk.openhouse.pipelineservice.util.impl;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

import sk.openhouse.pipelineservice.domain.response.LinkResponse;
import sk.openhouse.pipelineservice.domain.response.ResourceResponse;
import sk.openhouse.pipelineservice.util.HttpUtil;

public class HttpUtilImpl implements HttpUtil {

    private static final Logger logger = Logger.getLogger(HttpUtilImpl.class);

    private static final String PROJECTS_URI_PART = "projects";
    private static final String VERSIONS_URI_PART = "versions";
    private static final String PHASES_URI_PART = "phases";
    private static final String BUILDS_URI_PART = "builds";

    private URI rootURI;

    /**
     * @param rootURI application's root URI
     * @throws IllegalArgumentException if the uri is invalid
     * @throws NullPointerException if the argument is null
     */
    public HttpUtilImpl(String rootURI) {

        if (rootURI.endsWith("/")) {
            rootURI = rootURI.substring(0, rootURI.length() - 1);
        }

        try {
            this.rootURI = new URI(rootURI);
        } catch (URISyntaxException e) {
            String message = String.format("URI %s is not valid.", rootURI);
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRootURIString() {
        return rootURI.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProjectsURIString() {
        return PROJECTS_URI_PART;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProjectURIString(String projectName) {
        return String.format("%s/%s", PROJECTS_URI_PART, projectName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVersionsURIString(String projectName) {
        return String.format("%s/%s/%s", PROJECTS_URI_PART, projectName, VERSIONS_URI_PART);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPhasesURIString(String projectName, String versionNumber) {
        return String.format("%s/%s/%s/%s/%s", PROJECTS_URI_PART, projectName, 
                VERSIONS_URI_PART, versionNumber, PHASES_URI_PART);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVersionURIString(String projectName, String versionNumber) {
        return String.format("%s/%s/%s/%s", PROJECTS_URI_PART, projectName, VERSIONS_URI_PART, versionNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBuildsURIString(String projectName, String versionNumber) {

        return String.format("%s/%s/%s/%s/%s", 
                PROJECTS_URI_PART, projectName, VERSIONS_URI_PART, versionNumber, BUILDS_URI_PART);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBuildURIString(String projectName, String versionNumber, int buildNumber) {

        return String.format("%s/%s/%s/%s/%s/%d", 
                PROJECTS_URI_PART, projectName, VERSIONS_URI_PART, versionNumber, BUILDS_URI_PART, buildNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResourceResponse getResource(String uriString, String description) {
        return getResource(uriString, description, "GET", null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResourceResponse getResource(String uriString, String description, String method) {
        return getResource(uriString, description, method, null);
    }

    /**
     * {@inheritDoc}
     * @throws URISyntaxException 
     */
    @Override
    public ResourceResponse getResource(String uriString, String description, String method, String schemaURIString) {

        LinkResponse linkResponse = new LinkResponse();
        linkResponse.setMethod(method);

        try {
            linkResponse.setHref(new URI(uriString));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(String.format("URI String %s is invalid", uriString));
        }

        if (null != schemaURIString) {
            try {
                linkResponse.setSchemaLocation(new URI(schemaURIString));
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException(String.format("Schema URI String %s is invalid", schemaURIString));
            }
        }

        ResourceResponse resourceResponse = new ResourceResponse();
        resourceResponse.setLink(linkResponse);
        resourceResponse.setDescription(description);
        return resourceResponse;
    }
}
