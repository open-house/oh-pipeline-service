package sk.openhouse.automation.pipelineservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import sk.openhouse.automation.pipelinedomain.domain.response.LinkResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.LinksResponse;
import sk.openhouse.automation.pipelineservice.service.LinkService;
import sk.openhouse.automation.pipelineservice.service.RootResourceService;

/**
 * 
 * @author pete
 */
public class RootResourceServiceImpl implements RootResourceService {

    private final LinkService linkService;

    public RootResourceServiceImpl(LinkService linkService) {
        this.linkService = linkService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinksResponse getRootLinks() {

        List<LinkResponse> links = new ArrayList<LinkResponse>();
        links.add(linkService.getLink(linkService.getProjectsUriString(), 
                "List of all projects in pipeline service"));
        links.add(linkService.getLink(linkService.getSchemaUriString(), 
                "List of all projects in pipeline service"));

        LinksResponse rootLinks = new LinksResponse();
        rootLinks.setLinks(links);
        return rootLinks;
    }
}
