package sk.openhouse.automation.pipelineservice.service;

import sk.openhouse.automation.pipelinedomain.domain.response.ResourcesResponse;

/**
 * Service for the main/index page
 * 
 * @author pete
 */
public interface RootResourceService {

    /**
     * @return resources that appear on the index page
     */
    ResourcesResponse getRootResources();
}
