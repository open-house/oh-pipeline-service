package sk.openhouse.automation.pipelineservice.domain.response;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.testng.annotations.Test;

import sk.openhouse.automation.pipelineservice.domain.response.ProjectResponse;

public class ProjectResponseTest {

    @Test
    public void testEquals() {

        EqualsVerifier.forClass(ProjectResponse.class)
        .suppress(Warning.NONFINAL_FIELDS)
        .verify();
    }
}
