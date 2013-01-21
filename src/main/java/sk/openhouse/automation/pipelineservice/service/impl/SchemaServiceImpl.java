package sk.openhouse.automation.pipelineservice.service.impl;

import java.util.List;

import sk.openhouse.automation.pipelinedomain.domain.response.LinkResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.LinksResponse;
import sk.openhouse.automation.pipelineservice.service.LinkService;
import sk.openhouse.automation.pipelineservice.service.SchemaService;

public class SchemaServiceImpl implements SchemaService {

    private final LinkService linkService;

    public SchemaServiceImpl(LinkService linkService) {
        this.linkService = linkService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinksResponse getSchemaLinks() {

        LinksResponse linksResponse = new LinksResponse();
        List<LinkResponse> links = linksResponse.getLinks();
        links.add(linkService.getLink(linkService.getSchemaRequestUriString(), 
                "XML Schema for project requests."));

        return linksResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinksResponse getSchemaRequestLinks() {

        LinksResponse linksResponse = new LinksResponse();
        List<LinkResponse> links = linksResponse.getLinks();
        links.add(linkService.getLink(linkService.getProjectSchemaRequestUriString(), 
                "XML Schema for project requests."));
        links.add(linkService.getLink(linkService.getVersionSchemaRequestUriString(), 
                "XML Schema for project version requests."));
        links.add(linkService.getLink(linkService.getPhaseSchemaRequestUriString(), 
                "XML Schema for project phase requests."));
        links.add(linkService.getLink(linkService.getBuildSchemaRequestUriString(), 
                "XML Schema for project build requests."));
        links.add(linkService.getLink(linkService.getStateSchemaRequestUriString(), 
                "XML Schema for build state requests."));

        return linksResponse;
    }

}
