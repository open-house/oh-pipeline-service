package sk.openhouse.automation.pipelineservice.domain.response;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.testng.annotations.Test;

import sk.openhouse.automation.pipelineservice.domain.response.ResourceResponse;

public class ResourceResponseTest {

    @Test
    public void testEquals() {

        EqualsVerifier.forClass(ResourceResponse.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}
