package sk.openhouse.pipelineservice.dao.impl;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import sk.openhouse.pipelineservice.dao.BuildPhaseReadDao;
import sk.openhouse.pipelineservice.domain.PhaseState;

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
                + "AND ph name = :phaseName";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);
        args.addValue("buildNumber", buildNumber);
        args.addValue("phaseName", phaseName);

        logger.debug(String.format("Quering for last build state - %s args - %s", sql, args));
        try {
            return namedParameterJdbcTemplate.queryForObject(sql, args, PhaseState.class);
        } catch (DataAccessException e) {
            logger.debug(String.format("No build state for project $s version %s build %d and phase %s cannot be fond", 
                    projectName, versionNumber, buildNumber, phaseName));
        }

        return null;
    }

}
