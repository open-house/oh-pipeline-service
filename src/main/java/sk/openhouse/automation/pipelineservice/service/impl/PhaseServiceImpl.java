package sk.openhouse.automation.pipelineservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import sk.openhouse.automation.pipelineservice.dao.PhaseReadDao;
import sk.openhouse.automation.pipelineservice.dao.PhaseWriteDao;
import sk.openhouse.automation.pipelinedomain.domain.request.PhaseRequest;
import sk.openhouse.automation.pipelinedomain.domain.response.LinkResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.LinksResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.PhaseResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.PhasesResponse;
import sk.openhouse.automation.pipelineservice.resource.ResourceUtil;
import sk.openhouse.automation.pipelineservice.service.LinkService;
import sk.openhouse.automation.pipelineservice.service.PhaseService;
import sk.openhouse.automation.pipelineservice.service.VersionService;
import sk.openhouse.automation.pipelineservice.service.exception.BadRequestException;
import sk.openhouse.automation.pipelineservice.service.exception.ConflictException;
import sk.openhouse.automation.pipelineservice.service.exception.NotFoundException;

/**
 * 
 * @author pete
 */
public class PhaseServiceImpl implements PhaseService {

    private final String phaseSchemaLocation;
    private final LinkService linkService;
    private final PhaseReadDao phaseReadDao;
    private final PhaseWriteDao phaseWriteDao;
    private final VersionService versionService;

    public PhaseServiceImpl(LinkService linkService, PhaseReadDao phaseReadDao, PhaseWriteDao phaseWriteDao, VersionService versionService) {

        this.linkService = linkService;
        this.phaseReadDao = phaseReadDao;
        this.phaseWriteDao = phaseWriteDao;
        this.versionService = versionService;

        phaseSchemaLocation = String.format("%s/%s", linkService.getSchemaRequestUriString(), ResourceUtil.PHASE_PARAM);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PhaseResponse getFirstPhase(String projectName, String versionNumber) {

        PhaseResponse phase = phaseReadDao.getFirstPhase(projectName, versionNumber);
        if (null == phase) {
            throw new NotFoundException(String.format("No phases found for project %s and version %s.",
                    projectName, versionNumber));
        }

        return phase;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PhaseResponse getPhase(String projectName, String versionNumber, String phaseName) {

        PhaseResponse phaseResponse = phaseReadDao.getPhase(projectName, versionNumber, phaseName);
        if (null == phaseResponse) {
            throw new NotFoundException(String.format("Phase %s for project %s and version %s cannot be found.",
                    phaseName, projectName, versionNumber));
        }

        phaseResponse.setLinks(getPhaseLinks(projectName, versionNumber, phaseName));
        return phaseResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PhasesResponse getPhases(String projectName, String versionNumber) {

        PhasesResponse phasesResponse = phaseReadDao.getPhases(projectName, versionNumber);
        if (phasesResponse.getPhases().isEmpty()) {
            /* check if version exists - service will throw NotFoundException if it doesn't */
            versionService.getVersion(projectName, versionNumber);
        }

        phasesResponse.setHref(linkService.getPhaseUriTemplate(projectName, versionNumber));
        phasesResponse.setMethod("PUT");
        phasesResponse.setDescription("adds new project phase");
        phasesResponse.setSchemaLocation(phaseSchemaLocation);
        for (PhaseResponse phase : phasesResponse.getPhases()) {
            phase.setLinks(getPhasesLinks(projectName, versionNumber, phase));
        }
        return phasesResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPhase(String projectName, String versionNumber, String phaseName, PhaseRequest phaseRequest) {

        if (null == phaseRequest.getName()) {
            phaseRequest.setName(phaseName);
        }

        String phaseRequestName = phaseRequest.getName();
        if (!phaseRequestName.equals(phaseName)) {
            String messageTemplate = "Phase name in the request (%s) does not match the one in the url (%s)."
                    + " Either remove name element from the request, or use the same name as in the url.";
            throw new BadRequestException(String.format(messageTemplate, phaseRequestName, phaseName));
        }

        try {
            phaseWriteDao.addPhase(projectName, versionNumber, phaseRequest);
        } catch (DuplicateKeyException e) {
            throw new ConflictException(String.format("Phase %s for the project %s and version %s already exists.",
                    phaseRequest.getName(), projectName, versionNumber));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePhase(String projectName, String versionNumber, String phaseName, PhaseRequest phaseRequest) {

        if (null == phaseRequest.getName()) {
            phaseRequest.setName(phaseName);
        }
        phaseWriteDao.updatePhase(projectName, versionNumber, phaseName, phaseRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePhase(String projectName, String versionNumber, String phaseName) {
        phaseWriteDao.deletePhase(projectName, versionNumber, phaseName);
    }

    private LinksResponse getPhasesLinks(String projectName, String versionNumber, PhaseResponse phase) {

        String phaseUri = linkService.getPhaseUriString(projectName, versionNumber, phase.getName());
        List<LinkResponse> links = new ArrayList<LinkResponse>();

        /* GET - specific project */
        links.add(linkService.getLink(phaseUri, "phase details"));

        LinksResponse linksResponse = new LinksResponse();
        linksResponse.setLinks(links);
        return linksResponse;
    }

    private LinksResponse getPhaseLinks(String projectName, String versionNumber, String phaseName) {

        String phaseUri = linkService.getPhaseUriString(projectName, versionNumber, phaseName);
        List<LinkResponse> links = new ArrayList<LinkResponse>();

        /* POST - update */
        links.add(linkService.getLink(phaseUri, "updates existing phase", "POST", phaseSchemaLocation));
        /* DELETE */
        links.add(linkService.getLink(phaseUri, "deletes phase", "DELETE"));

        LinksResponse linksResponse = new LinksResponse();
        linksResponse.setLinks(links);
        return linksResponse;
    }
}
