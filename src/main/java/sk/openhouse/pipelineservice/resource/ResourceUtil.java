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

    /** Root Resource Path */
    static final String ROOT_PATH = "";

    /** Projects Path */
    static final String PROJECTS_PATH = ROOT_PATH + "/projects";

    /** Project Path */
    static final String PROJECT_PATH = PROJECTS_PATH + "/{project: " + NAME_PATTERN + "}";

    /** Project Versions Path */
    static final String VERSIONS_PATH = PROJECT_PATH + "/versions";

    /** Project Version Path */
    static final String VERSION_PATH = VERSIONS_PATH + "/{version: " + VERSION_NUMBER_PATTERN + "}";

    /** Project Version Builds Path */
    static final String BUILDS_PATH = VERSION_PATH + "/builds";

    /** Project Version Build Path */
    static final String BUILD_PATH = BUILDS_PATH + "/{build: " + NUMBER_PATTERN + "}";

    /** Project Version Build Phases Path */
    static final String BUILD_PHASES_PATH = BUILD_PATH + "/phases";

    /** Project Version Build Phase Path */
    static final String BUILD_PHASE_PATH = BUILD_PHASES_PATH + "/{phase: " + NAME_PATTERN + "}";

    /** Project Version Build Phase States Path */
    static final String BUILD_STATES_PATH = BUILD_PHASE_PATH + "/states";

    /** Project Version Build Phase State Path */
    static final String BUILD_STATE_PATH = BUILD_STATES_PATH + "/{state: " + NAME_PATTERN + "}";

    /** Project Version Phases Path */
    static final String PHASES_PATH = VERSION_PATH + "/phases";

    /** Project Version Phase Path */
    static final String PHASE_PATH = PHASES_PATH + "/{phase: " + NAME_PATTERN + "}";
}
