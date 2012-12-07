package sk.openhouse.pipelineservice.resource;

/**
 * Path templates (for endpoints/resources) used in javax.ws.rs.Path annotation
 * 
 * @author pete
 */
final class ResourceUtil {

    /** regex (validation) patterns used in resource paths */
    private static final String NAME_PATTERN = "[a-zA-Z0-9-_]+";
    private static final String VERSION_NUMBER_PATTERN = "[0-9\\.]+";
    private static final String NUMBER_PATTERN = "[0-9]+";

    /** project url part */
    static final String PROJECT_PARAM = "project";

    /** version url part */
    static final String VERSION_PARAM = "version";

    /** build url part */
    static final String BUILD_PARAM = "build";

    /** phase url part */
    static final String PHASE_PARAM = "phase";

    /** Root Resource Path */
    static final String ROOT_PATH = "";

    /** Projects Path */
    static final String PROJECTS_PATH = ROOT_PATH + "/projects";

    /** Project Path */
    static final String PROJECT_PATH = PROJECTS_PATH + "/{" + PROJECT_PARAM + ": " + NAME_PATTERN + "}";

    /** Project Versions Path */
    static final String VERSIONS_PATH = PROJECT_PATH + "/versions";

    /** Project Version Path */
    static final String VERSION_PATH = VERSIONS_PATH + "/{" + VERSION_PARAM + ": " + VERSION_NUMBER_PATTERN + "}";

    /** Project Version Builds Path */
    static final String BUILDS_PATH = VERSION_PATH + "/builds";

    /** Project Version Build Path */
    static final String BUILD_PATH = BUILDS_PATH + "/{" + BUILD_PARAM + ": " + NUMBER_PATTERN + "}";

    /** Project Version Build Phases Path */
    static final String BUILD_PHASES_PATH = BUILD_PATH + "/phases";

    /** Project Version Build Phase Path */
    static final String BUILD_PHASE_PATH = BUILD_PHASES_PATH + "/{" + PHASE_PARAM + ": " + NAME_PATTERN + "}";

    /** Project Version Build Phase States Path */
    static final String BUILD_STATES_PATH = BUILD_PHASE_PATH + "/states";

    /** Project Version Phases Path */
    static final String PHASES_PATH = VERSION_PATH + "/phases";

    /** Project Version Phase Path */
    static final String PHASE_PATH = PHASES_PATH + "/{" + PHASE_PARAM + ": " + NAME_PATTERN + "}";
}
