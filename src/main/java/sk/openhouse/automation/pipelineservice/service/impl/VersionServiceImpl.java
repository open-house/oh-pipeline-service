package sk.openhouse.automation.pipelineservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import sk.openhouse.automation.pipelineservice.dao.VersionReadDao;
import sk.openhouse.automation.pipelineservice.dao.VersionWriteDao;
import sk.openhouse.automation.pipelinedomain.domain.request.VersionRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.LinkResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.LinksResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.VersionResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.VersionsResponse;
import sk.openhouse.automation.pipelineservice.service.LinkService;
import sk.openhouse.automation.pipelineservice.service.ProjectService;
import sk.openhouse.automation.pipelineservice.service.VersionService;
import sk.openhouse.automation.pipelineservice.service.exception.NotFoundException;

public class VersionServiceImpl implements VersionService {

    private final LinkService linkService;
    private final VersionReadDao versionReadDao;
    private final VersionWriteDao versionWriteDao;
    private final ProjectService projectService;

    public VersionServiceImpl(LinkService linkService, VersionReadDao versionReadDao,
            VersionWriteDao versionWriteDao, ProjectService projectService) {

        this.linkService = linkService;
        this.versionReadDao = versionReadDao;
        this.versionWriteDao = versionWriteDao;
        this.projectService = projectService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionResponse getVersion(String projectName, String versionNumber) {

        VersionResponse versionResponse = versionReadDao.getVersion(projectName, versionNumber);
        if (null == versionResponse) {
            throw new NotFoundException(String.format("Version %s for project %s cannot be found.",
                    versionNumber, projectName));
        }
        return versionResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionsResponse getVersions(String projectName) {

        VersionsResponse versionsResponse = versionReadDao.getVersions(projectName);
        if (versionsResponse.getVersions().isEmpty()) {
            /* check if project exists - service will throw NotFoundException if it doesn't */
            projectService.getProject(projectName);
        }

        versionsResponse.setHref(linkService.getVersionUriTemplate(projectName));
        versionsResponse.setMethod("PUT");
        versionsResponse.setDescription("adds new project version");
        for (VersionResponse version : versionsResponse.getVersions()) {
            version.setLinks(getVersionsLinks(projectName, version));
        }

        return versionsResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addVersion(String projectName, VersionRequest versionRequest) {
        versionWriteDao.addVersion(projectName, versionRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateVersion(String projectName, String versionNumber, VersionRequest versionRequest) {
        versionWriteDao.updateVersion(projectName, versionNumber, versionRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteVersion(String projectName, String versionNumber) {
        versionWriteDao.deleteVersion(projectName, versionNumber);
    }

    private LinksResponse getVersionsLinks(String projectName, VersionResponse version) {

        String versionUri = linkService.getVersionUriString(projectName, version.getVersionNumber());
        List<LinkResponse> links = new ArrayList<LinkResponse>();

        /* GET - specific project */
        links.add(linkService.getLink(versionUri, "version details"));

        LinksResponse linksResponse = new LinksResponse();
        linksResponse.setLinks(links);
        return linksResponse;
    }
}
