package sk.openhouse.automation.pipelineservice.service;

import sk.openhouse.automation.pipelinedomain.domain.response.LinksResponse;

/**
 * 
 * @author pete
 */
public interface SchemaService {

    /**
     * @return links for XML Schema
     */
    LinksResponse getSchemaLinks();

    /**
     * @return links for XML Schema requests
     */
    LinksResponse getSchemaRequestLinks();
}
