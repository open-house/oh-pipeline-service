package sk.openhouse.pipelineservice.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import sk.openhouse.pipelineservice.dao.VersionWriteDao;
import sk.openhouse.pipelineservice.domain.request.VersionRequest;

public class VersionWriteDaoImpl implements VersionWriteDao {

    private static final Logger logger = Logger.getLogger(VersionWriteDaoImpl.class);

    private SimpleJdbcTemplate simpleJdbcTemplate;

    public VersionWriteDaoImpl(DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    @Override
    public void addVersion(String projectName, VersionRequest versionRequest) {

        String sql = "INSERT INTO versions (number, project_id)v" 
                + "VALUES(:versionNumber, " 
                + "(SELECT id FROM projects WHERE name = :projectName))";

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("projectName", projectName);
        args.put("versionNumber", versionRequest.getNumber());

        logger.debug(String.format("Adding version - %s args - %s", sql, args));
        simpleJdbcTemplate.update(sql, args);
    }

    @Override
    public void updateVersion(String projectName, String versionNumber, VersionRequest versionRequest) {

        String sql = "UPDATE versions SET number = :newVersionNumber " 
                + "WHERE versions.number = :versionNumber " 
                + "AND versions.project_id = (SELECT id FROM projects WHERE projects.name = :projectName)";

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("projectName", projectName);
        args.put("versionNumber", versionNumber);
        args.put("newVersionNumber", versionRequest.getNumber());

        logger.debug(String.format("Updating version - %s args - %s", sql, args));
        simpleJdbcTemplate.update(sql, args);
    }

    @Override
    public void deleteVersion(String projectName, String versionNumber) {

        String sql = "DELETE FROM versions " 
                + "WHERE versions.number = :versionNumber " 
                + "AND versions.project_id = (SELECT id FROM projects WHERE projects.name = :projectName)";

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("projectName", projectName);
        args.put("versionNumber", versionNumber);

        logger.debug(String.format("Deleting version - %s args - %s", sql, args));
        simpleJdbcTemplate.update(sql, args);
    }
}
