package sk.openhouse.automation.pipelineservice.resource;

import java.io.IOException;
import java.io.StringWriter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * @author pete
 *
 */
@Component
@Path(ResourceUtil.SCHEMA_REQUEST_PATH)
public class SchemaRequestResource {

    private static final Logger logger = Logger.getLogger(SchemaRequestResource.class);

    private static final String PROJECT_SCHEMA = getClassPathResource("request/project.xsd");
    private static final String VERSION_SCHEMA = getClassPathResource("request/version.xsd");
    private static final String PHASE_SCHEMA = getClassPathResource("request/phase.xsd");
    private static final String BUILD_SCHEMA = getClassPathResource("request/build.xsd");
    private static final String STATE_SCHEMA = getClassPathResource("request/state.xsd");

    @GET
    @Path("project")
    @Produces(MediaType.APPLICATION_XML)
    public String getProjectSchema() {
        return PROJECT_SCHEMA;
    }

    @GET
    @Path("version")
    @Produces(MediaType.APPLICATION_XML)
    public String getVersionSchema() {
        return VERSION_SCHEMA;
    }

    @GET
    @Path("phase")
    @Produces(MediaType.APPLICATION_XML)
    public String getPhaseSchema() {
        return PHASE_SCHEMA;
    }

    @GET
    @Path("build")
    @Produces(MediaType.APPLICATION_XML)
    public String getBuildSchema() {
        return BUILD_SCHEMA;
    }

    @GET
    @Path("state")
    @Produces(MediaType.APPLICATION_XML)
    public String getStateSchema() {
        return STATE_SCHEMA;
    }

    /**
     * @param path location of the resource
     * @return String representation of the resource
     */
    private static String getClassPathResource(String path) {

        Resource resource = new ClassPathResource(path);
        StringWriter stringWriter = new StringWriter();

        try {
            IOUtils.copy(resource.getInputStream(), stringWriter);
        } catch (IOException e) {
            logger.error(String.format("Cannoot load classpath resource %s.", path), e);
        }
        return stringWriter.toString();
    }
}
