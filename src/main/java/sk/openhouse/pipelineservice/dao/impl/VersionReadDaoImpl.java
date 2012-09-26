package sk.openhouse.pipelineservice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import sk.openhouse.pipelineservice.dao.VersionReadDao;
import sk.openhouse.pipelineservice.domain.response.BuildResponse;
import sk.openhouse.pipelineservice.domain.response.BuildsResponse;
import sk.openhouse.pipelineservice.domain.response.VersionDetailsResponse;

/**
 * 
 * @author pete
 */
public class VersionReadDaoImpl implements VersionReadDao {

    private static final Logger logger = Logger.getLogger(VersionReadDaoImpl.class);

    private JdbcTemplate jdbcTemplate;

    public VersionReadDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public VersionDetailsResponse getVersion(String projectName, String versionNumber) {

        String sql = "SELECT v.number, b.number as buildNumber from versions v " 
                + "JOIN projects p ON (v.project_id = p.id) "
                + "JOIN builds b ON (v.id = b.version_id) "
                + "WHERE p.name = ? AND v.number = ?";

        Object[] args = new Object[]{projectName, versionNumber};

        logger.debug(String.format("Quering for versions - %s args - [%s,%s]", sql, projectName, versionNumber));
        VersionDetailsResponse response = jdbcTemplate.query(sql, args, new VersionDetailsExtractor());
        /* if no builds are found, version is not set in extractor */
        if (null == response.getVersion().getNumber()) {
            response.getVersion().setNumber(versionNumber);
        }
        return response;
    }

    private static final class VersionDetailsExtractor implements ResultSetExtractor<VersionDetailsResponse> {

        public VersionDetailsResponse extractData(ResultSet rs) throws SQLException {

            VersionDetailsResponse versionDetails = new VersionDetailsResponse();
            List<BuildResponse> builds = new ArrayList<BuildResponse>();

            while(rs.next()) {
                if (rs.isFirst()) {
                    versionDetails.getVersion().setNumber(rs.getString("number"));
                }
                BuildResponse build = new BuildResponse();
                build.setNumber(rs.getInt("buildNumber"));
                builds.add(build);
            }

            BuildsResponse buildsResponse = new BuildsResponse();
            buildsResponse.setBuilds(builds);
            versionDetails.setBuilds(buildsResponse);

            return versionDetails;
        }
    }
}
