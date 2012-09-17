package sk.openhouse.pipelineservice.service;

import sk.openhouse.pipelineservice.domain.response.ResourcesResponse;

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
