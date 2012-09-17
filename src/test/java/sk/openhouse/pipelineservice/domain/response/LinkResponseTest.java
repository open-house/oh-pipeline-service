package sk.openhouse.pipelineservice.domain.response;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.testng.annotations.Test;

import sk.openhouse.pipelineservice.domain.response.LinkResponse;

public class LinkResponseTest {

    @Test
    public void testEquals() {

        EqualsVerifier.forClass(LinkResponse.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}
