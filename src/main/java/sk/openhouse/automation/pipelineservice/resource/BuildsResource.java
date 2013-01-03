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
import sk.openhouse.automation.pipelineservice.util.XmlUtil;

@Component
@Path(ResourceUtil.BUILDS_PATH)
public class BuildsResource {

    private final BuildService buildService;
    private final XmlUtil xmlUtil;

    public BuildsResource(BuildService buildService, XmlUtil xmlUtil) {
        this.buildService = buildService;
        this.xmlUtil = xmlUtil;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getBuild(@PathParam(ResourceUtil.PROJECT_PARAM) String projectName, 
            @PathParam(ResourceUtil.VERSION_PARAM) String versionNumber, 
            @QueryParam("limit") String limit) throws JAXBException {

        Integer limitQuery = parseLimit(limit);
        BuildsResponse builds = (null == limitQuery) 
                ? buildService.getBuilds(projectName, versionNumber)
                : buildService.getBuilds(projectName, versionNumber, limitQuery);

        return xmlUtil.marshall(BuildsResponse.class, builds);
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
