package sk.openhouse.automation.pipelineservice.resource;

/**
 * Path templates (for end points/resources) used in javax.ws.rs.Path annotation
 * 
 * @author pete
 */
final public class ResourceUtil {

    /** regex (validation) patterns used in resource paths */
    public static final String NAME_PATTERN = "[a-zA-Z0-9-_]+";
    public static final String VERSION_NUMBER_PATTERN = "[0-9\\.]+";
    public static final String NUMBER_PATTERN = "[0-9]+";

    /** project url part */
    public static final String PROJECT_PARAM = "project";

    /** version url part */
    public static final String VERSION_PARAM = "version";

    /** build url part */
    public static final String BUILD_PARAM = "build";

    /** phase url part */
    public static final String PHASE_PARAM = "phase";

    /** Root Resource Path */
    public static final String ROOT_PATH = "";

    /** Projects Path */
    public static final String PROJECTS_PATH = ROOT_PATH + "/projects";

    /** Project Path */
    public static final String PROJECT_PATH = PROJECTS_PATH + "/{" + PROJECT_PARAM + ": " + NAME_PATTERN + "}";

    /** Project Versions Path */
    public static final String VERSIONS_PATH = PROJECT_PATH + "/versions";

    /** Project Version Path */
    public static final String VERSION_PATH = VERSIONS_PATH + "/{" + VERSION_PARAM + ": " + VERSION_NUMBER_PATTERN + "}";

    /** Project Version Builds Path */
    public static final String BUILDS_PATH = VERSION_PATH + "/builds";

    /** Project Version Build Path */
    public static final String BUILD_PATH = BUILDS_PATH + "/{" + BUILD_PARAM + ": " + NUMBER_PATTERN + "}";

    /** Project Version Build Phases Path */
    public static final String BUILD_PHASES_PATH = BUILD_PATH + "/phases";

    /** Project Version Build Phase Path */
    public static final String BUILD_PHASE_PATH = BUILD_PHASES_PATH + "/{" + PHASE_PARAM + ": " + NAME_PATTERN + "}";

    /** Project Version Phases Path */
    public static final String PHASES_PATH = VERSION_PATH + "/phases";

    /** Project Version Phase Path */
    public static final String PHASE_PATH = PHASES_PATH + "/{" + PHASE_PARAM + ": " + NAME_PATTERN + "}";
}
