package sk.openhouse.pipelineservice.util.impl;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HttpUtilImplTest {

    private HttpUtilImpl httpUtilImpl;

    @BeforeMethod
    public void beforeMethod() {
        httpUtilImpl = new HttpUtilImpl("http://test.com/");
    }
}
