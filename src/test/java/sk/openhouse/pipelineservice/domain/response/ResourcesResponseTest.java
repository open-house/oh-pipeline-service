package sk.openhouse.pipelineservice.domain.response;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.testng.annotations.Test;

public class ResourcesResponseTest {

    @Test
    public void testEquals() {

        EqualsVerifier.forClass(ResourcesResponse.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}
