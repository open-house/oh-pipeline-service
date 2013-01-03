package sk.openhouse.automation.pipelineservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import sk.openhouse.automation.pipelinedomain.domain.response.LinkResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.LinksResponse;
import sk.openhouse.automation.pipelineservice.service.LinkService;
import sk.openhouse.automation.pipelineservice.service.RootResourceService;

public class RootResourceServiceImpl implements RootResourceService {

    private final LinkService linkService;

    public RootResourceServiceImpl(LinkService linkService) {
        this.linkService = linkService;
    }

    @Override
    public LinksResponse getRootLinks() {

        List<LinkResponse> links = new ArrayList<LinkResponse>();
        LinkResponse projectsLink = linkService.getLink(linkService.getProjectsUriString(), 
                "List of all projects in pipeline service", "GET");
        links.add(projectsLink);

        LinksResponse rootLinks = new LinksResponse();
        rootLinks.setLinks(links);
        return rootLinks;
    }
}
