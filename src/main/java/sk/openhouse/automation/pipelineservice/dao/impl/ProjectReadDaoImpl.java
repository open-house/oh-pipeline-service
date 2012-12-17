package sk.openhouse.automation.pipelineservice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import sk.openhouse.automation.pipelineservice.dao.ProjectReadDao;
import sk.openhouse.automation.pipelinedomain.domain.response.ProjectResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.ProjectsResponse;

public class ProjectReadDaoImpl implements ProjectReadDao {

    private static final Logger logger = Logger.getLogger(ProjectReadDaoImpl.class);
    private static final MapSqlParameterSource NO_ARGS = new MapSqlParameterSource();

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProjectReadDaoImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
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
            return namedParameterJdbcTemplate.queryForObject(sql, args, new ProjectMapper());
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
        List<ProjectResponse> projects = namedParameterJdbcTemplate.query(sql, NO_ARGS, new ProjectMapper());

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
