package sk.openhouse.pipelineservice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import sk.openhouse.pipelineservice.dao.ProjectReadDao;
import sk.openhouse.pipelineservice.domain.response.ProjectDetailsResponse;
import sk.openhouse.pipelineservice.domain.response.ProjectResponse;
import sk.openhouse.pipelineservice.domain.response.VersionResponse;
import sk.openhouse.pipelineservice.domain.response.VersionsResponse;

public class ProjectReadDaoImpl implements ProjectReadDao {

    private static final Logger logger = Logger.getLogger(ProjectReadDaoImpl.class);

    private JdbcTemplate jdbcTemplate;

    public ProjectReadDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ProjectDetailsResponse getProject(String name) {

        String sql = "SELECT p.name, v.number FROM projects p "
                + "JOIN versions v ON (p.id = v.project_id) "
                + "WHERE name = ?";

        logger.debug(String.format("Quering for project - %s args - %s", sql, name));
        return (ProjectDetailsResponse) jdbcTemplate.query(sql, new ProjectDetailsExtractor(), name);
    }

    @Override
    public List<ProjectResponse> getProjects() {

        String sql = "SELECT name FROM projects";
        logger.debug(String.format("Quering projects - %s", sql));
        return jdbcTemplate.query(sql, new ProjectMapper());
    }

    private static final class ProjectMapper implements RowMapper<ProjectResponse> {

        public ProjectResponse mapRow(ResultSet rs, int rowNum) throws SQLException {

            ProjectResponse project = new ProjectResponse();
            project.setName(rs.getString("name"));
            return project;
        }
    }

    private static final class ProjectDetailsExtractor implements ResultSetExtractor<ProjectDetailsResponse> {

        public ProjectDetailsResponse extractData(ResultSet rs) throws SQLException {

            ProjectDetailsResponse projectDetails = new ProjectDetailsResponse();
            List<VersionResponse> versions = new ArrayList<VersionResponse>();

            while(rs.next()) {
                if (rs.isFirst()) {
                    projectDetails.getProject().setName(rs.getString("name"));
                }
                VersionResponse version = new VersionResponse();
                version.setNumber(rs.getString("number"));
                versions.add(version);
            }

            VersionsResponse versionsResponse = new VersionsResponse();
            versionsResponse.setVersions(versions);
            projectDetails.setVersions(versionsResponse);

            return projectDetails;
        }
    }
}
