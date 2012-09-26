package sk.openhouse.pipelineservice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import sk.openhouse.pipelineservice.dao.BuildReadDao;
import sk.openhouse.pipelineservice.domain.response.BuildDetailsResponse;

public class BuildReadDaoImpl implements BuildReadDao {

    private static final Logger logger = Logger.getLogger(VersionReadDaoImpl.class);

    private SimpleJdbcTemplate simpleJdbcTemplate;

    public BuildReadDaoImpl(DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    @Override
    public BuildDetailsResponse getBuild(String projectName, String versionNumber, int buildNumber) {

        String sql = "SELECT b.number from builds b\n" 
                + "JOIN versions v ON (b.version_id = v.id)\n"
                + "JOIN projects p ON (v.project_id = p.id)\n"
                + "WHERE p.name = :projectName AND v.number = :versionNumber AND b.number = :buildNumber";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);
        args.addValue("buildNumber", buildNumber);

        logger.debug(String.format("Quering for builds - %s args - %s", sql, args));
        return simpleJdbcTemplate.queryForObject(sql, new BuildDetailsMapper(), args);
    }

    private static final class BuildDetailsMapper implements RowMapper<BuildDetailsResponse> {

        public BuildDetailsResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            BuildDetailsResponse buildDetails = new BuildDetailsResponse();
            buildDetails.getBuild().setNumber(rs.getInt("number"));
            return buildDetails;
        }
    }
}
