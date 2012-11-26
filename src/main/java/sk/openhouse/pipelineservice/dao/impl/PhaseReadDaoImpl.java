package sk.openhouse.pipelineservice.dao.impl;

import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import sk.openhouse.pipelineservice.dao.PhaseReadDao;
import sk.openhouse.pipelineservice.domain.response.PhaseResponse;
import sk.openhouse.pipelineservice.domain.response.PhasesResponse;

public class PhaseReadDaoImpl implements PhaseReadDao {

    private static final Logger logger = Logger.getLogger(PhaseReadDaoImpl.class);

    private SimpleJdbcTemplate simpleJdbcTemplate;

    public PhaseReadDaoImpl(DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PhaseResponse getPhase(String projectName, String phaseName) {

        String sql = "SELECT ph.name, ph.uri "
                + "FROM phases ph " 
                + "JOIN projects pr ON (ph.project_id = pr.id) "
                + "WHERE pr.name = :projectName AND ph.name = :phaseName";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("phaseName", phaseName);

        return simpleJdbcTemplate.queryForObject(sql, new PhaseMapper(), args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PhasesResponse getPhases(String projectName) {

        String sql = "SELECT ph.name, ph.uri "
                + "FROM phases ph " 
                + "JOIN projects pr ON (ph.project_id = pr.id) "
                + "WHERE pr.name = :projectName";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);

        List<PhaseResponse> phases = simpleJdbcTemplate.query(sql, new PhaseMapper(), args);
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
