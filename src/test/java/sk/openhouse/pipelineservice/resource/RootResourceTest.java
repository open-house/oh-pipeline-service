package sk.openhouse.pipelineservice.resource;

import javax.xml.bind.JAXBException;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import sk.openhouse.pipelineservice.resource.RootResource;
import sk.openhouse.pipelineservice.service.RootResourceService;
import sk.openhouse.pipelineservice.util.XmlUtil;

public class RootResourceTest {

    @Mock
    private RootResourceService rootResourceService;

    @Mock
    private XmlUtil xmlUtil;

    private RootResource rootResource;

    @BeforeMethod
    public void beforeMethod() {
        MockitoAnnotations.initMocks(this);
        rootResource = new RootResource(rootResourceService, xmlUtil);
    }

    @Test
    public void testGetResources() throws JAXBException {

        rootResource.getResources();
        Mockito.verify(rootResourceService, Mockito.times(1)).getRootResources();
    }
}
