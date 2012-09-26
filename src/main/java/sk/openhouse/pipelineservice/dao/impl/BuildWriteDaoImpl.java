package sk.openhouse.pipelineservice.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import sk.openhouse.pipelineservice.dao.BuildWriteDao;
import sk.openhouse.pipelineservice.domain.request.BuildRequest;

public class BuildWriteDaoImpl implements BuildWriteDao {

    private static final Logger logger = Logger.getLogger(BuildWriteDaoImpl.class);

    private SimpleJdbcTemplate simpleJdbcTemplate;

    public BuildWriteDaoImpl(DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    @Override
    public void addBuild(String projectName, String versionNumber, BuildRequest buildRequest) {

        String sql = "INSERT INTO builds (number, version_id) " 
                + "VALUES(:buildNumber, " 
                + "(SELECT v.id FROM versions v JOIN projects p "
                + "ON (v.project_id = p.id) " 
                + "WHERE v.number = :versionNumber AND p.name = :projectName))";

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("projectName", projectName);
        args.put("versionNumber", versionNumber);
        args.put("buildNumber", buildRequest.getNumber());

        logger.debug(String.format("Adding build - %s args - %s", sql, args));
        simpleJdbcTemplate.update(sql, args);
    }

    @Override
    public void updateBuild(String projectName, String versionNumber, int buildNumber, BuildRequest buildRequest) {

        String sql = "UPDATE builds b SET number = :newBuildNumber "
                + "WHERE b.number = :buildNumber "
                + "AND b.version_id = (SELECT v.id FROM versions v JOIN projects p "
                + "ON (v.project_id = p.id) " 
                + "WHERE v.number = :versionNumber AND p.name = :projectName)";

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("projectName", projectName);
        args.put("versionNumber", versionNumber);
        args.put("buildNumber", buildNumber);
        args.put("newBuildNumber", buildRequest.getNumber());

        logger.debug(String.format("Updating build - %s args - %s", sql, args));
        simpleJdbcTemplate.update(sql, args);
    }

    @Override
    public void deleteBuild(String projectName, String versionNumber, int buildNumber) {

        String sql = "DELETE FROM builds "
                + "WHERE builds.number = :buildNumber "
                + "AND builds.version_id = (SELECT v.id FROM versions v JOIN projects p "
                + "ON (v.project_id = p.id) " 
                + "WHERE v.number = :versionNumber AND p.name = :projectName)";

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("projectName", projectName);
        args.put("versionNumber", versionNumber);
        args.put("buildNumber", buildNumber);

        logger.debug(String.format("Deleting build - %s args - %s", sql, args));
        simpleJdbcTemplate.update(sql, args);
    }
}
