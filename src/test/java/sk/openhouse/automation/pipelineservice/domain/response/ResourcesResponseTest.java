package sk.openhouse.automation.pipelineservice.domain.response;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.testng.annotations.Test;

import sk.openhouse.automation.pipelineservice.domain.response.ResourcesResponse;

public class ResourcesResponseTest {

    @Test
    public void testEquals() {

        EqualsVerifier.forClass(ResourcesResponse.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}
