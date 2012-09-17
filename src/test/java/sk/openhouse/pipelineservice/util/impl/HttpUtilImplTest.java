package sk.openhouse.pipelineservice.util.impl;

import java.net.URI;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HttpUtilImplTest {

    @Test
    public void testGetRootURI() {

        String uri = "http://test.com";
        HttpUtilImpl httpUtilImpl = new HttpUtilImpl(uri);
        Assert.assertEquals(httpUtilImpl.getRootURI(), uri);
    }

    @Test
    public void testGetRootURITrailingSlash() {

        HttpUtilImpl httpUtilImpl = new HttpUtilImpl("http://test.com/");
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

        HttpUtilImpl httpUtilImpl = new HttpUtilImpl("http://test.com/");
        URI uri = httpUtilImpl.getAbsoluteURI("/product");
        Assert.assertEquals(uri.toString(), "http://test.com/product");
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetAbsoluteURIIllegal() {

        HttpUtilImpl httpUtilImpl = new HttpUtilImpl("http://test.com/");
        httpUtilImpl.getAbsoluteURI("t est");
    }

    @Test
    public void testGetAbsoluteURINull() {

        HttpUtilImpl httpUtilImpl = new HttpUtilImpl("http://test.com/");
        URI uri = httpUtilImpl.getAbsoluteURI(null);
        Assert.assertEquals(uri.toString(), "http://test.com");
    }

    @Test
    public void testGetAbsoluteURIEmpty() {

        HttpUtilImpl httpUtilImpl = new HttpUtilImpl("http://test.com/");
        URI uri = httpUtilImpl.getAbsoluteURI("");
        Assert.assertEquals(uri.toString(), "http://test.com");
    }
}
