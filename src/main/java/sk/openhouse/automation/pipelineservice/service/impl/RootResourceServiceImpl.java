package sk.openhouse.automation.pipelineservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import sk.openhouse.automation.pipelinedomain.domain.response.ResourceResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.ResourcesResponse;
import sk.openhouse.automation.pipelineservice.service.ResourceService;
import sk.openhouse.automation.pipelineservice.service.RootResourceService;

public class RootResourceServiceImpl implements RootResourceService {

    private final ResourceService resourceService;

    public RootResourceServiceImpl(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @Override
    public ResourcesResponse getRootResources() {

        List<ResourceResponse> resources = new ArrayList<ResourceResponse>();
        ResourceResponse projectsResource = resourceService.getResource(resourceService.getProjectsURIString(), 
                "List of all projects in pipeline service", "GET");
        resources.add(projectsResource);

        ResourcesResponse rootResources = new ResourcesResponse();
        rootResources.setResources(resources);
        return rootResources;
    }
}
