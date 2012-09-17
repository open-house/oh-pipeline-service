package sk.openhouse.pipelineservice.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import sk.openhouse.pipelineservice.dao.ProjectWriteDao;
import sk.openhouse.pipelineservice.domain.request.ProjectRequest;

public class ProjectWriteDaoImpl implements ProjectWriteDao {

    private static final Logger logger = Logger.getLogger(ProjectWriteDao.class);

    private SimpleJdbcTemplate simpleJdbcTemplate;

    public ProjectWriteDaoImpl(DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    @Override
    public void addProject(ProjectRequest project) {

        String sql = "INSERT INTO projects (name) VALUES(:projectName)";

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("projectName", project.getName());

        logger.debug(String.format("Adding project - %s args - %s", sql, args));
        simpleJdbcTemplate.update(sql, args);
    }

    @Override
    public void updateProject(String projectName, ProjectRequest project) {

        String sql = "UPDATE projects SET name = :newName WHERE name = :projectName";

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("projectName", projectName);
        args.put("newName", project.getName());

        logger.debug(String.format("Updating project - %s args - %s", sql, args));
        simpleJdbcTemplate.update(sql, args);
    }

    @Override
    public void deleteProject(String projectName) {

        String sql = "DELETE FROM projects WHERE name = :projectName"; 

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("projectName", projectName);

        logger.debug(String.format("Deleting project - %s args - %s", sql, args));
        simpleJdbcTemplate.update(sql, args);
    }
}
