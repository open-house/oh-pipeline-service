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

import sk.openhouse.pipelineservice.dao.ProjectReadDao;
import sk.openhouse.pipelineservice.domain.response.ProjectResponse;
import sk.openhouse.pipelineservice.domain.response.ProjectsResponse;

public class ProjectReadDaoImpl implements ProjectReadDao {

    private static final Logger logger = Logger.getLogger(ProjectReadDaoImpl.class);

    private SimpleJdbcTemplate simpleJdbcTemplate;

    public ProjectReadDaoImpl(DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectResponse getProject(String name) {

        String sql = "SELECT name FROM projects WHERE name = :name";
        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("name", name);

        logger.debug(String.format("Quering for project - %s args - %s", sql, name));
        try {
            return simpleJdbcTemplate.queryForObject(sql, new ProjectMapper(), args);
        } catch (EmptyResultDataAccessException e) {
            logger.debug(String.format("Project $s found", name));
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectsResponse getProjects() {

        String sql = "SELECT name FROM projects";
        logger.debug(String.format("Quering projects - %s", sql));
        List<ProjectResponse> projects = simpleJdbcTemplate.query(sql, new ProjectMapper());

        ProjectsResponse projectsResponse = new ProjectsResponse();
        if (null != projects) {
            projectsResponse.setProjects(projects);
        }
        return projectsResponse;
    }

    private static final class ProjectMapper implements RowMapper<ProjectResponse> {

        public ProjectResponse mapRow(ResultSet rs, int rowNum) throws SQLException {

            ProjectResponse project = new ProjectResponse();
            project.setName(rs.getString("name"));
            return project;
        }
    }
}
