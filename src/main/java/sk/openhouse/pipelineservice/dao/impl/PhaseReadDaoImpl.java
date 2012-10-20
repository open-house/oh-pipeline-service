package sk.openhouse.pipelineservice.dao.impl;

import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import sk.openhouse.pipelineservice.dao.PhaseReadDao;
import sk.openhouse.pipelineservice.domain.response.PhaseResponse;
import sk.openhouse.pipelineservice.domain.response.PhasesResponse;

public class PhaseReadDaoImpl implements PhaseReadDao {

    private static final Logger logger = Logger.getLogger(PhaseReadDaoImpl.class);

    private JdbcTemplate jdbcTemplate;

    public PhaseReadDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public PhaseResponse getPhase(String projectName, String phaseName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PhasesResponse getPhases(String projectName) {

        String sql = "SELECT name, call_uri, poll_uri, timeout_seconds, order_index "
                + "FROM phases WHERE project_id = "
                + "(SELECT id FROM projects WHERE name = ?)";

        String[] args = new String[] {projectName};

        List<PhaseResponse> phases = jdbcTemplate.query(sql, args, new PhaseMapper());
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
                phase.setCallURI(rs.getURL("call_uri").toURI());
                phase.setCallURI(rs.getURL("poll_uri").toURI());
            } catch (URISyntaxException e) {
                logger.error("URI in phase table is incorrect");
            }
            phase.setTimeoutSeconds(rs.getInt("timeout_seconds"));
            phase.setOrderIndex(rs.getInt("order_index"));

            return phase;
        }
    }
}
