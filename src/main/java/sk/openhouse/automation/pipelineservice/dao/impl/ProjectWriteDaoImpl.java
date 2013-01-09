package sk.openhouse.automation.pipelineservice.dao.impl;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import sk.openhouse.automation.pipelineservice.dao.ProjectWriteDao;
import sk.openhouse.automation.pipelinedomain.domain.request.ProjectRequest;

/**
 * 
 * @author pete
 */
public class ProjectWriteDaoImpl implements ProjectWriteDao {

    private static final Logger logger = Logger.getLogger(ProjectWriteDao.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProjectWriteDaoImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addProject(ProjectRequest project) {

        String sql = "INSERT INTO projects (name) VALUES(:projectName)";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", project.getName());

        logger.debug(String.format("Adding project - %s args - %s", sql, args.getValues()));
        namedParameterJdbcTemplate.update(sql, args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateProject(String projectName, ProjectRequest project) {

        String sql = "UPDATE projects SET name = :newName WHERE name = :projectName";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("newName", project.getName());

        logger.debug(String.format("Updating project - %s args - %s", sql, args.getValues()));
        namedParameterJdbcTemplate.update(sql, args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProject(String projectName) {

        String sql = "DELETE FROM projects WHERE name = :projectName"; 

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);

        logger.debug(String.format("Deleting project - %s args - %s", sql, args.getValues()));
        namedParameterJdbcTemplate.update(sql, args);
    }
}
