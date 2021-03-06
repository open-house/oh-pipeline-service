package sk.openhouse.automation.pipelineservice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import sk.openhouse.automation.pipelineservice.dao.VersionReadDao;
import sk.openhouse.automation.pipelinedomain.domain.response.VersionResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.VersionsResponse;

/**
 * 
 * @author pete
 */
public class VersionReadDaoImpl implements VersionReadDao {

    private static final Logger logger = Logger.getLogger(VersionReadDaoImpl.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public VersionReadDaoImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionResponse getVersion(String projectName, String versionNumber) {

        String sql = "SELECT v.version_number \n"
                + "FROM versions v \n"
                + "JOIN projects p ON (v.project_id = p.id) \n"
                + "WHERE p.name = :projectName \n"
                + "AND v.version_number = :versionNumber";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);

        logger.debug(String.format("Quering for versions - %s args - %s", sql, args.getValues()));
        try {
            return namedParameterJdbcTemplate.queryForObject(sql, args, new VersionMapper());
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

        String sql = "SELECT v.version_number FROM versions v \n" 
                + "JOIN projects p ON (v.project_id = p.id) \n"
                + "WHERE p.name = :projectName";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);

        logger.debug(String.format("Quering for versions - %s args - %s", sql, args.getValues()));
        List<VersionResponse> versions = namedParameterJdbcTemplate.query(sql, args, new VersionMapper());

        Collections.sort(versions);

        VersionsResponse versionsResponse = new VersionsResponse();
        versionsResponse.setVersions(versions);
        return versionsResponse;
    }

    private static final class VersionMapper implements RowMapper<VersionResponse> {

        public VersionResponse mapRow(ResultSet rs, int rowNum) throws SQLException {

            VersionResponse version = new VersionResponse();
            version.setVersionNumber(rs.getString("version_number"));
            return version;
        }
    }
}
