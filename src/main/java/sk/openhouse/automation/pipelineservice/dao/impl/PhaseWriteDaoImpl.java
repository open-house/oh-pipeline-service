package sk.openhouse.automation.pipelineservice.dao.impl;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import sk.openhouse.automation.pipelineservice.dao.PhaseWriteDao;
import sk.openhouse.automation.pipelinedomain.domain.request.PhaseRequest;

/**
 *
 * @author pete
 */
public class PhaseWriteDaoImpl implements PhaseWriteDao {

    private static final Logger logger = Logger.getLogger(PhaseWriteDaoImpl.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PhaseWriteDaoImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPhase(String projectName, String versionNumber, PhaseRequest phaseRequest) {

        String sql = "INSERT INTO phases (name, uri, username, password, version_id) " 
                + "VALUES(:phaseName, :phaseUri, :username, :password, " 
                + "(SELECT v.id FROM versions v JOIN projects p "
                + "ON (v.project_id = p.id) " 
                + "WHERE v.version_number = :versionNumber AND p.name = :projectName))";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);
        args.addValue("phaseName", phaseRequest.getName());
        args.addValue("phaseUri", phaseRequest.getUri().toString());
        args.addValue("username", phaseRequest.getUsername());
        args.addValue("password", phaseRequest.getPassword());

        logger.debug(String.format("Adding phase - %s args - %s", sql, args.getValues()));
        namedParameterJdbcTemplate.update(sql, args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePhase(String projectName, String versionNumber, String phaseName, PhaseRequest phaseRequest) {

        String sql = "UPDATE phases ph SET %s "
                + "WHERE ph.name = :phaseName "
                + "AND ph.version_id = (SELECT v.id FROM versions v JOIN projects p "
                + "ON (v.project_id = p.id) " 
                + "WHERE v.version_number = :versionNumber AND p.name = :projectName)";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);
        args.addValue("phaseName", phaseName);

        StringBuilder sb = new StringBuilder();
        if (null != phaseRequest.getName()) {
            sb.append(", name = :newPhaseName");
            args.addValue("newPhaseName", phaseRequest.getName());
        }

        if (null != phaseRequest.getUri()) {
            sb.append(", uri = :newPhaseUri");
            args.addValue("newPhaseUri", phaseRequest.getUri().toString());
        }

        if (null != phaseRequest.getUsername()) {
            sb.append(", username = :newUsername");
            /* username is deleted (if the authentication is not required) by setting it to empty string */
            String username = (phaseRequest.getUsername().isEmpty()) ? null : phaseRequest.getUsername();
            args.addValue("newUsername", username);
        }

        if (null != phaseRequest.getPassword()) {
            sb.append(", password = :newPassword");
            args.addValue("newPassword", phaseRequest.getPassword());
        }

        String updatePart = sb.toString();
        if (updatePart.isEmpty()) {
            logger.debug("Nothing to update.");
            return;
        }

        sql = String.format(sql, updatePart.substring(2));

        logger.debug(String.format("Updating phase - %s args - %s", sql, args.getValues()));
        namedParameterJdbcTemplate.update(sql, args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePhase(String projectName, String versionNumber, String phaseName) {

        String sql = "DELETE FROM phases "
                + "WHERE phases.name = :phaseName "
                + "AND phases.version_id = (SELECT v.id FROM versions v JOIN projects p "
                + "ON (v.project_id = p.id) " 
                + "WHERE v.version_number = :versionNumber AND p.name = :projectName)";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);
        args.addValue("phaseName", phaseName);

        logger.debug(String.format("Deleting phase - %s args - %s", sql, args.getValues()));
        namedParameterJdbcTemplate.update(sql, args);
    }
}
