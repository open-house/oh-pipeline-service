package sk.openhouse.pipelineservice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import sk.openhouse.pipelineservice.dao.VersionReadDao;
import sk.openhouse.pipelineservice.domain.response.VersionResponse;
import sk.openhouse.pipelineservice.domain.response.VersionsResponse;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionResponse getVersion(String projectName, String versionNumber) {

        String sql = "SELECT v.number from versions v " + "JOIN projects p ON (v.project_id = p.id) "
                + "WHERE p.name = :projectName AND v.number = :versionNumber";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);

        logger.debug(String.format("Quering for versions - %s args - [%s,%s]", sql, projectName, versionNumber));
        try {
            return simpleJdbcTemplate.queryForObject(sql, new VersionMapper(), args);
        } catch (EmptyResultDataAccessException e) {
            logger.debug(String.format("Cannot find project %s version $s.", projectName, versionNumber));
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionsResponse getVersions(String projectName) {

        String sql = "SELECT v.number FROM versions v " 
                + "JOIN projects p ON (v.project_id = p.id) "
                + "WHERE p.name = :projectName";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);

        logger.debug(String.format("Quering for versions - %s args - [%s]", sql, projectName));
        List<VersionResponse> versions = simpleJdbcTemplate.query(sql, new VersionMapper(), args);

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
