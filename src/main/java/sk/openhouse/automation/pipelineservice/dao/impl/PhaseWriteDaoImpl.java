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

        String sql = "INSERT INTO phases (name, uri, version_id) " 
                + "VALUES(:phaseName, :phaseUri, " 
                + "(SELECT v.id FROM versions v JOIN projects p "
                + "ON (v.project_id = p.id) " 
                + "WHERE v.version_number = :versionNumber AND p.name = :projectName))";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);
        args.addValue("phaseName", phaseRequest.getName());
        args.addValue("phaseUri", phaseRequest.getUri().toString());

        logger.debug(String.format("Adding phase - %s args - %s", sql, args.getValues()));
        namedParameterJdbcTemplate.update(sql, args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePhase(String projectName, String versionNumber, String phaseName, PhaseRequest phaseRequest) {

        String sql = "UPDATE phases ph SET name = :newPhaseName%s "
                + "WHERE ph.name = :phaseName "
                + "AND ph.version_id = (SELECT v.id FROM versions v JOIN projects p "
                + "ON (v.project_id = p.id) " 
                + "WHERE v.version_number = :versionNumber AND p.name = :projectName)";

        MapSqlParameterSource args = new MapSqlParameterSource();
        args.addValue("projectName", projectName);
        args.addValue("versionNumber", versionNumber);
        args.addValue("phaseName", phaseName);
        args.addValue("newPhaseName", phaseRequest.getName());

        /* uri is optional */
        if (null != phaseRequest.getUri()) {
            args.addValue("newPhaseUri", phaseRequest.getUri().toString());
            sql = String.format(sql, ", uri = :newPhaseUri");
        } else {
            sql = String.format(sql, "");
        }

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
