package sk.openhouse.pipelineservice.dao.impl;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import sk.openhouse.pipelineservice.dao.VersionWriteDao;
import sk.openhouse.pipelineservice.domain.request.VersionRequest;

public class VersionWriteDaoImpl implements VersionWriteDao {

    private static final Logger logger = Logger.getLogger(VersionWriteDaoImpl.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public VersionWriteDaoImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addVersion(String projectName, VersionRequest versionRequest) {

        String sql = "INSERT INTO versions (version_number, project_id) " 
                + "VALUES(:versionNumber, " 
                + "(SELECT id FROM projects WHERE name = :projectName))";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionRequest.getVersionNumber());

        logger.debug(String.format("Adding version - %s args - %s", sql, args));
        namedParameterJdbcTemplate.update(sql, args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateVersion(String projectName, String versionNumber, VersionRequest versionRequest) {

        String sql = "UPDATE versions SET version_number = :newVersionNumber " 
                + "WHERE versions.version_number = :versionNumber " 
                + "AND versions.project_id = (SELECT id FROM projects WHERE projects.name = :projectName)";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);
        args.addValue("newVersionNumber", versionRequest.getVersionNumber());

        logger.debug(String.format("Updating version - %s args - %s", sql, args));
        namedParameterJdbcTemplate.update(sql, args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteVersion(String projectName, String versionNumber) {

        String sql = "DELETE FROM versions " 
                + "WHERE versions.version_number = :versionNumber " 
                + "AND versions.project_id = (SELECT id FROM projects WHERE projects.name = :projectName)";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);

        logger.debug(String.format("Deleting version - %s args - %s", sql, args));
        namedParameterJdbcTemplate.update(sql, args);
    }
}
