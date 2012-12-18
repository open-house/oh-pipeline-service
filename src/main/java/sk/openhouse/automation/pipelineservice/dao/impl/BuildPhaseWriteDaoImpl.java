package sk.openhouse.automation.pipelineservice.dao.impl;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import sk.openhouse.automation.pipelineservice.dao.BuildPhaseWriteDao;
import sk.openhouse.automation.pipelinedomain.domain.PhaseState;

public class BuildPhaseWriteDaoImpl implements BuildPhaseWriteDao {

    private static final Logger logger = Logger.getLogger(BuildPhaseWriteDaoImpl.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BuildPhaseWriteDaoImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void addPhase(String projectName, String versionNumber, int buildNumber, String phaseName) {

        String sql = "INSERT INTO build_phases (build_id, phase_id, state) \n"
                + "VALUES(\n"
                    + "(SELECT b.id FROM builds b \n"
                        + "JOIN versions v ON (b.version_id = v.id) \n"
                        + "JOIN projects p ON (v.project_id = p.id) \n"
                        + "WHERE b.number = :buildNumber \n"
                        + "AND v.version_number = :versionNumber \n"
                        + "AND p.name = :projectName), \n"
                    + "(SELECT ph.id FROM phases ph WHERE ph.name = :phaseName), \n"
                    + ":phaseState)";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);
        args.addValue("buildNumber", buildNumber);
        args.addValue("phaseName", phaseName);
        args.addValue("phaseState", PhaseState.IN_PROGRESS.name());

        logger.debug(String.format("Adding build phase - %s args - %s", sql, args.getValues()));
        namedParameterJdbcTemplate.update(sql, args);
    }

    @Override
    public void addState(String projectName, String versionNumber, int buildNumber, String phaseName, PhaseState phaseState) {

        String sql = "INSERT INTO build_phases (build_id, phase_id, state) \n"
                + "VALUES(\n"
                    + "(SELECT b.id FROM builds b \n"
                        + "JOIN versions v ON (b.version_id = v.id) \n"
                        + "JOIN projects p ON (v.project_id = p.id) \n"
                        + "WHERE b.number = :buildNumber \n"
                        + "AND v.version_number = :versionNumber \n"
                        + "AND p.name = :projectName), \n"
                    + "(SELECT ph.id FROM phases ph WHERE ph.name = :phaseName), \n"
                    + ":phaseState)";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);
        args.addValue("buildNumber", buildNumber);
        args.addValue("phaseName", phaseName);
        args.addValue("phaseState", phaseState.name());

        logger.debug(String.format("Adding build phase - %s args - %s", sql, args.getValues()));
        namedParameterJdbcTemplate.update(sql, args);
    }
}
