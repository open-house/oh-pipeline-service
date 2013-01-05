package sk.openhouse.automation.pipelineservice.service.impl;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

import sk.openhouse.automation.pipelinedomain.domain.response.LinkResponse;
import sk.openhouse.automation.pipelineservice.resource.ResourceUtil;
import sk.openhouse.automation.pipelineservice.service.LinkService;
import sk.openhouse.automation.pipelineservice.util.impl.HttpUtilImpl;

public class LinkServiceImpl implements LinkService {

    private static final Logger logger = Logger.getLogger(HttpUtilImpl.class);

    private static final String PROJECTS_URI_PART = "projects";
    private static final String VERSIONS_URI_PART = "versions";
    private static final String PHASES_URI_PART = "phases";

    private final String rootUri;

    /**
     * @param rootUri application's root URI
     * @throws IllegalArgumentException if the uri is invalid
     * @throws NullPointerException if the argument is null
     */
    public LinkServiceImpl(String rootUri) {

        if (rootUri.endsWith("/")) {
            rootUri = rootUri.substring(0, rootUri.length() - 1);
        }

        try {
            this.rootUri = new URI(rootUri).toString();
        } catch (URISyntaxException e) {
            String message = String.format("URI %s is not valid.", rootUri);
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProjectsUriString() {
        return String.format("%s/%s", rootUri, PROJECTS_URI_PART);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProjectUriTemplate() {
        return String.format("%s%s", rootUri, ResourceUtil.PROJECT_PATH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProjectUriString(String projectName) {
        return String.format("%s/%s", getProjectsUriString(), projectName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVersionstUriString(String projectName) {
        return String.format("%s/%s/%s/%s", rootUri, PROJECTS_URI_PART, projectName, VERSIONS_URI_PART);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVersionUriTemplate(String projectName) {

        return String.format("%s/{%s: %s}", getVersionstUriString(projectName), 
                ResourceUtil.VERSION_PARAM, ResourceUtil.VERSION_NUMBER_PATTERN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVersionUriString(String projectName, String versionNumber) {

        return String.format("%s/%s/%s/%s/%s", rootUri, PROJECTS_URI_PART, projectName, 
                VERSIONS_URI_PART, versionNumber);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getPhasesUriString(String projectName) {
        return String.format("%s/%s/%s/%s", rootUri, PROJECTS_URI_PART, projectName, PHASES_URI_PART);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkResponse getLink(String uriString, String description) {
        return getLink(uriString, description, "GET", null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkResponse getLink(String uriString, String description, String method) {
        return getLink(uriString, description, method, null);
    }

    /**
     * {@inheritDoc}
     * @throws URISyntaxException 
     */
    @Override
    public LinkResponse getLink(String uriString, String description, String method, String schemaURIString) {

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

        linkResponse.setDescription(description);
        return linkResponse;
    }
}
