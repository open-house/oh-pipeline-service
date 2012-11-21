package sk.openhouse.pipelineservice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import sk.openhouse.pipelineservice.dao.VersionReadDao;
import sk.openhouse.pipelineservice.domain.response.VersionResponse;
import sk.openhouse.pipelineservice.domain.response.VersionsResponse;

/**
 * 
 * @author pete
 */
public class VersionReadDaoImpl implements VersionReadDao {

    private static final Logger logger = Logger.getLogger(VersionReadDaoImpl.class);

    private JdbcTemplate jdbcTemplate;

    public VersionReadDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionResponse getVersion(String projectName, String versionNumber) {

        String sql = "SELECT v.number from versions v " + "JOIN projects p ON (v.project_id = p.id) "
                + "WHERE p.name = ? AND v.number = ?";

        Object[] args = new Object[] { projectName, versionNumber };

        logger.debug(String.format("Quering for versions - %s args - [%s,%s]", sql, projectName, versionNumber));
        return jdbcTemplate.queryForObject(sql, args, new VersionMapper());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionsResponse getVersions(String projectName) {

        String sql = "SELECT v.number FROM versions v " + "JOIN projects p ON (v.project_id = p.id) "
                + "WHERE p.name = ?";

        Object[] args = new Object[] { projectName };

        logger.debug(String.format("Quering for versions - %s args - [%s]", sql, projectName));
        List<VersionResponse> versions = jdbcTemplate.query(sql, args, new VersionMapper());

        VersionsResponse versionsResponse = new VersionsResponse();
        if (null != versions) {
            versionsResponse.setVersions(versions);
        }
        return versionsResponse;
    }

    private static final class VersionMapper implements RowMapper<VersionResponse> {

        public VersionResponse mapRow(ResultSet rs, int rowNum) throws SQLException {

            VersionResponse version = new VersionResponse();
            version.setNumber(rs.getString("number"));
            return version;
        }
    }
}
