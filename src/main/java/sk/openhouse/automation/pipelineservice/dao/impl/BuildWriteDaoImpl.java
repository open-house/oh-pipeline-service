package sk.openhouse.automation.pipelineservice.dao.impl;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import sk.openhouse.automation.pipelineservice.dao.BuildWriteDao;
import sk.openhouse.automation.pipelinedomain.domain.request.BuildRequest;

public class BuildWriteDaoImpl implements BuildWriteDao {

    private static final Logger logger = Logger.getLogger(BuildWriteDaoImpl.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BuildWriteDaoImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBuild(String projectName, String versionNumber, BuildRequest buildRequest) {

        String sql = "INSERT INTO builds (number, version_id) " 
                + "VALUES(:buildNumber, " 
                + "(SELECT v.id FROM versions v JOIN projects p "
                + "ON (v.project_id = p.id) " 
                + "WHERE v.version_number = :versionNumber AND p.name = :projectName))";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);
        args.addValue("buildNumber", buildRequest.getNumber());

        logger.debug(String.format("Adding build - %s args - %s", sql, args.getValues()));
        namedParameterJdbcTemplate.update(sql, args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateBuild(String projectName, String versionNumber, int buildNumber, BuildRequest buildRequest) {

        String sql = "UPDATE builds b SET number = :newBuildNumber "
                + "WHERE b.number = :buildNumber "
                + "AND b.version_id = (SELECT v.id FROM versions v JOIN projects p "
                + "ON (v.project_id = p.id) " 
                + "WHERE v.version_number = :versionNumber AND p.name = :projectName)";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);
        args.addValue("buildNumber", buildNumber);
        args.addValue("newBuildNumber", buildRequest.getNumber());

        logger.debug(String.format("Updating build - %s args - %s", sql, args.getValues()));
        namedParameterJdbcTemplate.update(sql, args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteBuild(String projectName, String versionNumber, int buildNumber) {

        String sql = "DELETE FROM builds "
                + "WHERE builds.number = :buildNumber "
                + "AND builds.version_id = (SELECT v.id FROM versions v JOIN projects p "
                + "ON (v.project_id = p.id) " 
                + "WHERE v.version_number = :versionNumber AND p.name = :projectName)";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);
        args.addValue("buildNumber", buildNumber);

        logger.debug(String.format("Deleting build - %s args - %s", sql, args.getValues()));
        namedParameterJdbcTemplate.update(sql, args);
    }
}
