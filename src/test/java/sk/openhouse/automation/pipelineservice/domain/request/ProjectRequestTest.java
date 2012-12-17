package sk.openhouse.automation.pipelineservice.domain.request;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.testng.annotations.Test;

import sk.openhouse.automation.pipelineservice.domain.request.ProjectRequest;

public class ProjectRequestTest {

    @Test
    public void testEquals() {

        EqualsVerifier.forClass(ProjectRequest.class)
        .suppress(Warning.NONFINAL_FIELDS)
        .verify();
    }
}
