package sk.openhouse.automation.pipelineservice.resource;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sk.openhouse.automation.pipelineservice.service.BuildPhaseService;

import javax.xml.bind.JAXBException;

/**
 *
 * @author pete
 */
public class BuildPhaseResourceTest {

    @Mock
    private BuildPhaseService buildPhaseService;

    private BuildPhaseResource buildPhaseResource;

    @BeforeMethod
    public void beforeMethod() {

        MockitoAnnotations.initMocks(this);
        this.buildPhaseResource = new BuildPhaseResource(buildPhaseService);
    }

    @Test
    public void testGetBuildPhase() throws JAXBException {

        String projectName = "test_project";
        String versionNumber = "2.0";
        int buildNumber = 7;
        String phaseName = "QA";

        buildPhaseResource.getBuildPhase(projectName, versionNumber, buildNumber, phaseName);
        Mockito.verify(buildPhaseService, Mockito.times(1))
                .getBuildPhase(projectName, versionNumber, buildNumber, phaseName);
    }
}
