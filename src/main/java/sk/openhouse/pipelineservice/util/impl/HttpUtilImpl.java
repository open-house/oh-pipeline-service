package sk.openhouse.pipelineservice.util.impl;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

import sk.openhouse.pipelineservice.domain.response.LinkResponse;
import sk.openhouse.pipelineservice.domain.response.ResourceResponse;
import sk.openhouse.pipelineservice.util.HttpUtil;

public class HttpUtilImpl implements HttpUtil {

    private static final Logger logger = Logger.getLogger(HttpUtilImpl.class);

    private static final String PROJECTS_RELATIVE_URI = "projects";
    private static final String VERSIONS_URI_PART = "versions";
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
    public String getRootURI() {
        return rootURI.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URI getAbsoluteURI(String relativeURI) {

        if (null == relativeURI || relativeURI.isEmpty()) {
            return rootURI;
        }

        relativeURI = relativeURI.trim();
        if (relativeURI.startsWith("/")) {
            relativeURI = relativeURI.substring(1, relativeURI.length());
        }

        String absoluteURI = String.format("%s/%s", getRootURI(), relativeURI);
        try {
            return new URI(absoluteURI);
        } catch (URISyntaxException e) {
            String message = String.format("Invalid URI - %s", absoluteURI);
            logger.error(message, e);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProjectsRelativeURI() {
        return PROJECTS_RELATIVE_URI;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProjectRelativeURI(String projectName) {

        if (null == projectName || projectName.isEmpty()) {
            return getProjectsRelativeURI();
        }
        return String.format("%s/%s", PROJECTS_RELATIVE_URI, projectName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVersionRelativeURI(String projectName, String versionNumber) {

        if (null == versionNumber || versionNumber.isEmpty()) {
            return getProjectRelativeURI(projectName);
        }
        return String.format("%s/%s/%s/%s", PROJECTS_RELATIVE_URI, projectName, VERSIONS_URI_PART, versionNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBuildRelativeURI(String projectName, String versionNumber, int buildNumber) {

        return String.format("%s/%s/%s/%s/%d", 
                PROJECTS_RELATIVE_URI, projectName, VERSIONS_URI_PART, versionNumber, buildNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResourceResponse getResource(String relativeURI, String description) {
        return getResource(relativeURI, description, "GET", null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResourceResponse getResource(String relativeURI, String description, String method) {
        return getResource(relativeURI, description, method, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResourceResponse getResource(String relativeURI, String description, String method, String relativeSchemaURI) {

        LinkResponse linkResponse = new LinkResponse();
        linkResponse.setHref(getAbsoluteURI(relativeURI));
        linkResponse.setMethod(method);

        if (null != relativeSchemaURI) {
            linkResponse.setSchemaLocation(getAbsoluteURI(relativeSchemaURI));
        }

        ResourceResponse resourceResponse = new ResourceResponse();
        resourceResponse.setLink(linkResponse);
        resourceResponse.setDescription(description);
        return resourceResponse;
    }
}