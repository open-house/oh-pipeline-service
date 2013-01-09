package sk.openhouse.automation.pipelineservice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import sk.openhouse.automation.pipelineservice.dao.BuildPhaseReadDao;
import sk.openhouse.automation.pipelinedomain.domain.PhaseState;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildPhaseResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildPhasesResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.StateResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.StatesResponse;

/**
 * 
 * @author pete
 */
public class BuildPhaseReadDaoImpl implements BuildPhaseReadDao {

    private static final Logger logger = Logger.getLogger(BuildPhaseReadDaoImpl.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BuildPhaseReadDaoImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PhaseState getLastState(String projectName, String versionNumber, int buildNumber, String phaseName) {

        String sql = "SELECT bp.state \n"
                + "FROM build_phases bp \n"
                + "JOIN builds b ON (bp.build_id = b.id) \n"
                + "JOIN phases ph ON (bp.phase_id = ph.id) \n"
                + "JOIN versions v ON (b.version_id = v.id) \n"
                + "JOIN projects p ON (v.project_id = p.id) \n"
                + "WHERE p.name = :projectName \n"
                + "AND v.version_number = :versionNumber \n"
                + "AND b.number = :buildNumber \n"
                + "AND ph.name = :phaseName \n"
                + "ORDER BY bp.id DESC LIMIT 1";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);
        args.addValue("buildNumber", buildNumber);
        args.addValue("phaseName", phaseName);

        logger.debug(String.format("Quering for last build state - %s args - %s", sql, args.getValues()));
        try {
            String state = namedParameterJdbcTemplate.queryForObject(sql, args, String.class);
            return PhaseState.valueOf(state);
        } catch (DataAccessException e) {
            logger.debug(String.format("No build state found for project %s version %s build %d and phase %s.", 
                    projectName, versionNumber, buildNumber, phaseName), e);
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildPhasesResponse getBuildPhases(String projectName, String versionNumber, int buildNumber) {

        String sql = "SELECT ph.name as phase_name, bp.state \n"
                + "FROM build_phases bp \n"
                + "LEFT JOIN phases ph ON (bp.phase_id = ph.id) \n"
                + "JOIN builds b ON (bp.build_id = b.id) \n"
                + "JOIN versions v ON (b.version_id = v.id) \n"
                + "JOIN projects p ON (v.project_id = p.id) \n"
                + "WHERE p.name = :projectName \n"
                + "AND v.version_number = :versionNumber \n"
                + "AND b.number = :buildNumber";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);
        args.addValue("buildNumber", buildNumber);

        logger.debug(String.format("Quering for build phases - %s args - %s", sql, args.getValues()));
        return namedParameterJdbcTemplate.query(sql, args, new BuildPhasesExtractor());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildPhaseResponse getBuildPhase(String projectName, String versionNumber, int buildNumber, String phaseName) {

        String sql = "SELECT bp.state \n"
                + "FROM build_phases bp \n"
                + "LEFT JOIN phases ph ON (bp.phase_id = ph.id) \n"
                + "JOIN builds b ON (bp.build_id = b.id) \n"
                + "JOIN versions v ON (b.version_id = v.id) \n"
                + "JOIN projects p ON (v.project_id = p.id) \n"
                + "WHERE p.name = :projectName \n"
                + "AND v.version_number = :versionNumber \n"
                + "AND b.number = :buildNumber \n"
                + "AND ph.name = :phaseName";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);
        args.addValue("buildNumber", buildNumber);
        args.addValue("phaseName", phaseName);

        logger.debug(String.format("Quering for build phases - %s args - %s", sql, args.getValues()));
        List<StateResponse> states = namedParameterJdbcTemplate.query(sql, args, new StateMapper());
        if (states.isEmpty()) {
            logger.debug(String.format("No build phase %s for build %d project $s and version %s fond", 
                    phaseName, buildNumber, projectName, versionNumber));
            return null;
        }

        BuildPhaseResponse buildPhaseResponse = new BuildPhaseResponse();
        buildPhaseResponse.setName(phaseName);

        StatesResponse statesResponse = new StatesResponse();
        statesResponse.setStates(states);
        buildPhaseResponse.setStates(statesResponse);

        return buildPhaseResponse;
    }

    private final class BuildPhasesExtractor implements ResultSetExtractor<BuildPhasesResponse> {

        public BuildPhasesResponse extractData(ResultSet rs) throws SQLException {

            List<BuildPhaseResponse> buildPhases = new ArrayList<BuildPhaseResponse>();
            while (rs.next()) {

                String phaseName = rs.getString("phase_name");

                BuildPhaseResponse buildPhaseResponse = new BuildPhaseResponse();
                buildPhaseResponse.setName(phaseName);

                /* get index of build phase, add if not in the list*/
                int indexOfBuildPhase = buildPhases.indexOf(buildPhaseResponse);
                if (indexOfBuildPhase < 0) {
                    buildPhases.add(buildPhaseResponse);
                    indexOfBuildPhase = buildPhases.size() - 1;
                }

                /* add phase state */
                StateResponse stateResponse = new StateResponse();
                PhaseState phaseState = PhaseState.valueOf(rs.getString("state"));
                stateResponse.setName(phaseState);
                buildPhases.get(indexOfBuildPhase).getStates().getStates().add(stateResponse);
            }

            BuildPhasesResponse buildPhasesResponse = new BuildPhasesResponse();
            buildPhasesResponse.setBuildPhases(buildPhases);
            return buildPhasesResponse;
        }
    }

    private static final class StateMapper implements RowMapper<StateResponse> {

        public StateResponse mapRow(ResultSet rs, int rowNum) throws SQLException {

            StateResponse stateResponse = new StateResponse();
            PhaseState phaseState = PhaseState.valueOf(rs.getString("state"));
            stateResponse.setName(phaseState);
            return stateResponse;
        }
    }
}
