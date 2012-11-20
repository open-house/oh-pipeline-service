package sk.openhouse.pipelineservice.util.impl;

import java.net.URI;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HttpUtilImplTest {

    private HttpUtilImpl httpUtilImpl;

    @BeforeMethod
    public void beforeMethod() {
        httpUtilImpl = new HttpUtilImpl("http://test.com/");
    }

    @Test
    public void testGetRootURI() {

        String uri = "http://test.com";
        httpUtilImpl = new HttpUtilImpl(uri);
        Assert.assertEquals(httpUtilImpl.getRootURI(), uri);
    }

    @Test
    public void testGetRootURITrailingSlash() {
        Assert.assertEquals(httpUtilImpl.getRootURI(), "http://test.com");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetRootURIInvalid() {
        new HttpUtilImpl("http://t est.com/");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testGetRootURINull() {
        new HttpUtilImpl(null);
    }

    @Test
    public void testGetAbsoluteURI() {

        URI uri = httpUtilImpl.getAbsoluteURI("/product");
        Assert.assertEquals(uri.toString(), "http://test.com/product");
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetAbsoluteURIIllegal() {
        httpUtilImpl.getAbsoluteURI("t est");
    }

    @Test
    public void testGetAbsoluteURINull() {
        URI uri = httpUtilImpl.getAbsoluteURI(null);
        Assert.assertEquals(uri.toString(), "http://test.com");
    }

    @Test
    public void testGetAbsoluteURIEmpty() {
        URI uri = httpUtilImpl.getAbsoluteURI("");
        Assert.assertEquals(uri.toString(), "http://test.com");
    }

    @Test
    public void testGetProjectsRelativeUri() {
        Assert.assertEquals(httpUtilImpl.getProjectsRelativeURI(), "projects");
    }

    @Test
    public void testGetProjectRelativeUri() {
        Assert.assertEquals(httpUtilImpl.getProjectRelativeURI("x"), "projects/x");
    }

    @Test
    public void testGetProjectRelativeUriEmpty() {
        Assert.assertEquals(httpUtilImpl.getProjectRelativeURI(""), "projects");
    }

    @Test
    public void testGetProjectRelativeUriNull() {
        Assert.assertEquals(httpUtilImpl.getProjectRelativeURI(null), "projects");
    }
}
