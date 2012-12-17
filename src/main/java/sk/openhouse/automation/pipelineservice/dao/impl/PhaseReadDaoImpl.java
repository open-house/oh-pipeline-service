package sk.openhouse.automation.pipelineservice.dao.impl;

import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import sk.openhouse.automation.pipelineservice.dao.PhaseReadDao;
import sk.openhouse.automation.pipelinedomain.domain.response.PhaseResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.PhasesResponse;

public class PhaseReadDaoImpl implements PhaseReadDao {

    private static final Logger logger = Logger.getLogger(PhaseReadDaoImpl.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PhaseReadDaoImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PhaseResponse getFirstPhase(String projectName, String versionNumber) {

        String sql = "SELECT ph.name, ph.uri FROM phases ph \n"
                + "JOIN versions v ON (ph.version_id = v.id) \n"
                + "JOIN projects p ON (v.project_id = p.id) \n"
                + "WHERE v.version_number = :versionNumber \n"
                + "ORDER BY ph.timestamp LIMIT 1";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);

        return namedParameterJdbcTemplate.queryForObject(sql, args, new PhaseMapper());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PhaseResponse getPhase(String projectName, String versionNumber, String phaseName) {

        String sql = "SELECT ph.name, ph.uri FROM phases ph " 
                + "JOIN versions v ON (ph.version_id = v.id) "
                + "JOIN projects p ON (v.project_id = p.id) "
                + "WHERE p.name = :projectName AND v.version_number = :versionNumber AND ph.name = :phaseName";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);
        args.addValue("phaseName", phaseName);

        return namedParameterJdbcTemplate.queryForObject(sql, args, new PhaseMapper());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PhasesResponse getPhases(String projectName, String versionNumber) {

        String sql = "SELECT ph.name, ph.uri FROM phases ph " 
                + "JOIN versions v ON (ph.version_id = v.id) "
                + "JOIN projects p ON (v.project_id = p.id) "
                + "WHERE p.name = :projectName AND v.version_number = :versionNumber";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);

        List<PhaseResponse> phases = namedParameterJdbcTemplate.query(sql, args, new PhaseMapper());
        if (null == phases) {
            phases = new ArrayList<PhaseResponse>();
        }

        PhasesResponse phasesResponse = new PhasesResponse();
        phasesResponse.setPhases(phases);
        return phasesResponse;
    }

    private static final class PhaseMapper implements RowMapper<PhaseResponse> {

        public PhaseResponse mapRow(ResultSet rs, int rowNum) throws SQLException {

            PhaseResponse phase = new PhaseResponse();
            phase.setName(rs.getString("name"));
            try {
                phase.setUri(rs.getURL("uri").toURI());
            } catch (URISyntaxException e) {
                logger.error("URI in phase table is incorrect");
            }

            return phase;
        }
    }
}
