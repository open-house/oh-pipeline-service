package sk.openhouse.pipelineservice.util.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import sk.openhouse.pipelineservice.domain.response.LinkResponse;
import sk.openhouse.pipelineservice.domain.response.ResourceResponse;
import sk.openhouse.pipelineservice.domain.response.ResourcesResponse;

public class XmlUtilImplTest {

    private XmlUtilImpl xmlUtilImpl = new XmlUtilImpl();
    private ResourcesResponse resourcesResponse;

    @BeforeMethod
    public void beforeMethod() throws URISyntaxException {

        LinkResponse linkResponse = new LinkResponse();
        linkResponse.setMethod("PUT");
        linkResponse.setHref(new URI("http://some_resource"));
        linkResponse.setSchemaLocation(new URI("http://some_schema.xsd"));

        ResourceResponse resourceResponse = new ResourceResponse();
        resourceResponse.setLink(linkResponse);
        resourceResponse.setDescription("test description");

        List<ResourceResponse> resources = new ArrayList<ResourceResponse>();
        resources.add(resourceResponse);

        resourcesResponse = new ResourcesResponse();
        resourcesResponse.setResources(resources);
    }

    @Test
    public void testMarshall() throws JAXBException {

        String marshalled = xmlUtilImpl.marshall(ResourcesResponse.class, resourcesResponse);
        ResourcesResponse unmarshalled = (ResourcesResponse) xmlUtilImpl.unmarshall(ResourcesResponse.class, marshalled);

        Assert.assertEquals(unmarshalled, resourcesResponse);
    }
}
