package sk.openhouse.pipelineservice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import sk.openhouse.pipelineservice.dao.VersionReadDao;
import sk.openhouse.pipelineservice.domain.response.VersionDetailsResponse;

/**
 * 
 * @author pete
 */
public class VersionReadDaoImpl implements VersionReadDao {

    private static final Logger logger = Logger.getLogger(VersionReadDaoImpl.class);

    private SimpleJdbcTemplate simpleJdbcTemplate;

    public VersionReadDaoImpl(DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    @Override
    public VersionDetailsResponse getVersion(String projectName, String versionNumber) {

        String sql = "SELECT v.number from versions v " 
                + "JOIN projects p ON (v.project_id = p.id) "
                + "WHERE p.name = :projectName AND v.number = :versionNumber";

        Map<String, String> args = new HashMap<String, String>();
        args.put("projectName", projectName);
        args.put("versionNumber", versionNumber);

        logger.debug(String.format("Quering for versions - %s args - %s", sql, args));
        return simpleJdbcTemplate.queryForObject(sql, new VersionDetailsMapper(), args);
    }

    private static final class VersionDetailsMapper implements RowMapper<VersionDetailsResponse> {

        public VersionDetailsResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            VersionDetailsResponse versionDetails = new VersionDetailsResponse();
            versionDetails.getVersion().setNumber(rs.getString("number"));
            return versionDetails;
        }
    }
}
