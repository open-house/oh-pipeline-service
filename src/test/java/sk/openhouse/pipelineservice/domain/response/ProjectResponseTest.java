package sk.openhouse.pipelineservice.domain.response;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.testng.annotations.Test;

public class ProjectResponseTest {

    @Test
    public void testEquals() {

        EqualsVerifier.forClass(ProjectResponse.class)
        .suppress(Warning.NONFINAL_FIELDS)
        .verify();
    }
}
