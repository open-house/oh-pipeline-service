package sk.openhouse.pipelineservice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import sk.openhouse.pipelineservice.dao.BuildReadDao;
import sk.openhouse.pipelineservice.domain.response.BuildResponse;
import sk.openhouse.pipelineservice.domain.response.BuildsResponse;

public class BuildReadDaoImpl implements BuildReadDao {

    private static final Logger logger = Logger.getLogger(VersionReadDaoImpl.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BuildReadDaoImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildsResponse getBuilds(String projectName, String versionNumber) {

        String sql = "SELECT b.number FROM builds b " 
                + "JOIN versions v ON (b.version_id = v.id) "
                + "JOIN projects p ON (v.project_id = p.id) "
                + "WHERE p.name = :projectName AND v.version_number = :versionNumber";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);

        logger.debug(String.format("Quering for builds - %s args - %s", sql, args));
        List<BuildResponse> builds = namedParameterJdbcTemplate.query(sql, args, new BuildMapper());

        BuildsResponse buildsResponse = new BuildsResponse();
        if (null != builds) {
            buildsResponse.setBuilds(builds);
        }
        return buildsResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildResponse getBuild(String projectName, String versionNumber, int buildNumber) {

        String sql = "SELECT b.number FROM builds b " 
                + "JOIN versions v ON (b.version_id = v.id) "
                + "JOIN projects p ON (v.project_id = p.id) "
                + "WHERE p.name = :projectName AND v.version_number = :versionNumber AND b.number = :buildNumber";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);
        args.addValue("buildNumber", buildNumber);

        logger.debug(String.format("Quering for builds - %s args - %s", sql, args));
        try {
            return namedParameterJdbcTemplate.queryForObject(sql, args, new BuildMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug(String.format("Build %d for project $s and version %s cannot be fond", 
                    buildNumber, projectName, versionNumber));
        }

        return null;
    }

    private static final class BuildMapper implements RowMapper<BuildResponse> {

        public BuildResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            BuildResponse buildResponse = new BuildResponse();
            buildResponse.setNumber(rs.getInt("number"));
            return buildResponse;
        }
    }
}
