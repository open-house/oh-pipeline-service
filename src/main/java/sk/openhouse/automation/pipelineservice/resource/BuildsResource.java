package sk.openhouse.automation.pipelineservice.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import sk.openhouse.automation.pipelinedomain.domain.response.BuildsResponse;
import sk.openhouse.automation.pipelineservice.service.BuildService;

/**
 * 
 * @author pete
 */
@Component
@Path(ResourceUtil.BUILDS_PATH)
public class BuildsResource {

    private final BuildService buildService;

    public BuildsResource(BuildService buildService) {
        this.buildService = buildService;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public BuildsResponse getBuild(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber, 
            @QueryParam(ResourceUtil.LIMIT_QUERY_PARAM) String limit) throws JAXBException {

        Integer limitQuery = parseLimit(limit);
        return (null == limitQuery) 
                ? buildService.getBuilds(projectName, versionNumber)
                : buildService.getBuilds(projectName, versionNumber, limitQuery);
    }

    /**
     * @param limit query string to be converted to integer
     * @return int if the string can be parsed or null otherwise
     */
    private Integer parseLimit(String limit) {

        int parsedLimit;
        try {
            parsedLimit = Integer.parseInt(limit);
        } catch (NumberFormatException e) {
            return null;
        }

        return (parsedLimit > 0) ? parsedLimit : null;
    }
}
