package sk.openhouse.pipelineservice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import sk.openhouse.pipelineservice.dao.BuildReadDao;
import sk.openhouse.pipelineservice.domain.PhaseState;
import sk.openhouse.pipelineservice.domain.response.BuildPhaseResponse;
import sk.openhouse.pipelineservice.domain.response.BuildResponse;
import sk.openhouse.pipelineservice.domain.response.BuildsResponse;
import sk.openhouse.pipelineservice.domain.response.StateResponse;

public class BuildReadDaoImpl implements BuildReadDao {

    private static final Logger logger = Logger.getLogger(VersionReadDaoImpl.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BuildReadDaoImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildsResponse getBuilds(String projectName, String versionNumber) {

        String sql = "SELECT b.number, ph.name as phase_name, bp.state \n"
                + "FROM builds b \n"
                + "LEFT JOIN build_phases bp ON (bp.build_id = b.id) \n"
                + "LEFT JOIN phases ph ON (bp.phase_id = ph.id) \n"
                + "JOIN versions v ON (b.version_id = v.id) \n"
                + "JOIN projects p ON (v.project_id = p.id) \n"
                + "WHERE p.name = :projectName \n"
                + "AND v.version_number = :versionNumber";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);

        logger.debug(String.format("Quering for builds - %s args - %s", sql, args));
        return namedParameterJdbcTemplate.query(sql, args, new BuildsExtractor());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildResponse getBuild(String projectName, String versionNumber, int buildNumber) {

        String sql = "SELECT b.number, ph.name as phase_name, bp.state \n"
                + "FROM builds b \n"
                + "LEFT JOIN build_phases bp ON (bp.build_id = b.id) \n"
                + "LEFT JOIN phases ph ON (bp.phase_id = ph.id) \n"
                + "JOIN versions v ON (b.version_id = v.id) \n"
                + "JOIN projects p ON (v.project_id = p.id) \n"
                + "WHERE p.name = :projectName \n"
                + "AND v.version_number = :versionNumber \n"
                + "AND b.number = :buildNumber";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);
        args.addValue("buildNumber", buildNumber);

        logger.debug(String.format("Quering for builds - %s args - %s", sql, args));
        try {
            return namedParameterJdbcTemplate.query(sql, args, new BuildExtractor());
        } catch (EmptyResultDataAccessException e) {
            logger.debug(String.format("Build %d for project $s and version %s cannot be fond", 
                    buildNumber, projectName, versionNumber));
        }

        return null;
    }

    private final class BuildsExtractor implements ResultSetExtractor<BuildsResponse> {

        public BuildsResponse extractData(ResultSet rs) throws SQLException {

            /* builds grouped by build number - map key */
            Map<Integer, List<Map<String, String>>> buildRows = new HashMap<Integer, List<Map<String, String>>>();

            while (rs.next()) {

                int buildNumber = rs.getInt("number");
                if (!buildRows.containsKey(buildNumber)) {
                    buildRows.put(buildNumber, new ArrayList<Map<String, String>>());
                }

                Map<String, String> row = new HashMap<String, String>();
                row.put("number", rs.getString("number"));
                row.put("phase_name", rs.getString("phase_name"));
                row.put("state", rs.getString("state"));
                buildRows.get(buildNumber).add(row);
            }

            List<BuildResponse> buildResponses = new ArrayList<BuildResponse>();
            for (Map.Entry<Integer, List<Map<String, String>>> keyValue : buildRows.entrySet()) {
                buildResponses.add(getBuildResponse(keyValue.getValue()));
            }

            BuildsResponse buildsResponse = new BuildsResponse();
            buildsResponse.setBuilds(buildResponses);
            return buildsResponse;
        }
    }

    private final class BuildExtractor implements ResultSetExtractor<BuildResponse> {

        public BuildResponse extractData(ResultSet rs) throws SQLException {

            List<Map<String, String>> rows = new ArrayList<Map<String,String>>();
            while (rs.next()) {

                Map<String, String> row = new HashMap<String, String>();
                row.put("number", rs.getString("number"));
                row.put("phase_name", rs.getString("phase_name"));
                row.put("state", rs.getString("state"));
                rows.add(row);
            }

            return (rows.isEmpty()) ? null : getBuildResponse(rows);
        }
    }

    /**
     * Converts db rows (represented as list of maps) to build response object
     * 
     * @param rows db rows represented as list of maps where key is column name
     * @return
     */
    private BuildResponse getBuildResponse(List<Map<String, String>> rows) {

        BuildResponse buildResponse = new BuildResponse();
        List<BuildPhaseResponse> buildPhases = new ArrayList<BuildPhaseResponse>();

        boolean firstRow = true;
        for (Map<String, String> row : rows) {

            if (firstRow) {
                buildResponse.setNumber(new Integer(row.get("number")));
            }

            String phaseName = row.get("phase_name");
            /* build has most likely been added before phases were set */
            if (null == phaseName) {
                continue;
            }

            BuildPhaseResponse buildPhaseResponse = new BuildPhaseResponse();
            buildPhaseResponse.setName(phaseName);

            /* get index of build phase, add if not in the list*/
            int indexOfBuildPhase = buildPhases.indexOf(buildPhaseResponse);
            if (indexOfBuildPhase < 0) {
                buildPhases.add(buildPhaseResponse);
                indexOfBuildPhase = buildPhases.size() - 1;
            }

            /* add phase state */
            StateResponse stateResponse = new StateResponse();
            String phaseState = row.get("state");
            stateResponse.setName(PhaseState.valueOf(phaseState));
            buildPhases.get(indexOfBuildPhase).getStates().getStates().add(stateResponse);
        }

        buildResponse.getBuildPhases().setBuildPhases(buildPhases);
        return buildResponse;
    }
}
