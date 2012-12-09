package sk.openhouse.pipelineservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import sk.openhouse.pipelineservice.domain.response.ResourceResponse;
import sk.openhouse.pipelineservice.domain.response.ResourcesResponse;
import sk.openhouse.pipelineservice.service.RootResourceService;
import sk.openhouse.pipelineservice.service.ResourceService;

public class RootResourceServiceImpl implements RootResourceService {

    private ResourceService resourceService;

    public RootResourceServiceImpl(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @Override
    public ResourcesResponse getRootResources() {

        List<ResourceResponse> resources = new ArrayList<ResourceResponse>();
        ResourceResponse projectsResource = resourceService.getResource(resourceService.getProjectsURIString(), 
                "List of all projects in pipeline service", 
                "GET", "some-schema.xsd");
        resources.add(projectsResource);

        ResourcesResponse rootResources = new ResourcesResponse();
        rootResources.setResources(resources);
        return rootResources;
    }
}
