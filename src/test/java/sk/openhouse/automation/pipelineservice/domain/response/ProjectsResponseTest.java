package sk.openhouse.automation.pipelineservice.domain.response;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.testng.annotations.Test;

import sk.openhouse.automation.pipelineservice.domain.response.ProjectsResponse;

public class ProjectsResponseTest {

    @Test
    public void testEquals() {

        EqualsVerifier.forClass(ProjectsResponse.class)
        .suppress(Warning.NONFINAL_FIELDS)
        .verify();
    }
}
