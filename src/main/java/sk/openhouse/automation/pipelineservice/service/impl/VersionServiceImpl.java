package sk.openhouse.automation.pipelineservice.service.impl;

import sk.openhouse.automation.pipelineservice.dao.VersionReadDao;
import sk.openhouse.automation.pipelineservice.dao.VersionWriteDao;
import sk.openhouse.automation.pipelinedomain.domain.request.VersionRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.VersionResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.VersionsResponse;
import sk.openhouse.automation.pipelineservice.service.ProjectService;
import sk.openhouse.automation.pipelineservice.service.VersionService;
import sk.openhouse.automation.pipelineservice.service.exception.NotFoundException;

public class VersionServiceImpl implements VersionService {

    private final VersionReadDao versionReadDao;
    private final VersionWriteDao versionWriteDao;
    private final ProjectService projectService;

    public VersionServiceImpl(VersionReadDao versionReadDao,
            VersionWriteDao versionWriteDao, ProjectService projectService) {

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
}
