package sk.openhouse.pipelineservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import sk.openhouse.pipelineservice.domain.response.LinkResponse;
import sk.openhouse.pipelineservice.domain.response.ResourceResponse;
import sk.openhouse.pipelineservice.domain.response.ResourcesResponse;
import sk.openhouse.pipelineservice.service.RootResourceService;
import sk.openhouse.pipelineservice.util.HttpUtil;

public class RootResourceServiceImpl implements RootResourceService {

    private HttpUtil httpUtil;

    public RootResourceServiceImpl(HttpUtil httpUtil) {
        this.httpUtil = httpUtil;
    }

    @Override
    public ResourcesResponse getRootResources() {

        List<ResourceResponse> resources = new ArrayList<ResourceResponse>();

        LinkResponse projectsLink = new LinkResponse();
        projectsLink.setHref(httpUtil.getAbsoluteURI("projects"));
        projectsLink.setSchemaLocation(httpUtil.getAbsoluteURI("some-schema.xsd"));
        projectsLink.setMethod("GET");

        ResourceResponse projectsResource = new ResourceResponse();
        projectsResource.setLink(projectsLink);
        projectsResource.setDescription("List of all projects in pipeline service");

        resources.add(projectsResource);

        ResourcesResponse rootResources = new ResourcesResponse();
        rootResources.setResources(resources);
        return rootResources;
    }
}
