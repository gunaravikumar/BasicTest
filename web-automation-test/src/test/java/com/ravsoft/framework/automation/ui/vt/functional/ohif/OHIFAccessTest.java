package com.ravsoft.framework.automation.ui.vt.functional.ohif;

import com.ravsoft.framework.automation.ui.pageobjects.jenkins.login.LoginPage;
import com.ravsoft.framework.automation.ui.pageobjects.ohif.viewer.OHIFViewerPage;
import com.ravsoft.framework.automation.ui.pageobjects.ohif.viewer.StudyListPage;
import com.ravsoft.framework.automation.ui.utils.DicomUtils;
import com.ravsoft.framework.automation.ui.utils.ExcelUtils;
import com.ravsoft.framework.automation.ui.utils.Logger;
import com.ravsoft.framework.automation.ui.utils.Utils;
import com.ravsoft.framework.automation.ui.vt.TestClass;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import java.net.MalformedURLException;

public class OHIFAccessTest extends TestClass {

    private static RemoteWebDriver theDriver;
    private static final String testProtocol = "OHIFAccessTest";
    private static final String testData = Utils.getAutomationProperties().getProperty("OHIFAccessTest");
    LoginPage loginPage = null;
    OHIFViewerPage viewerPage = null;
    StudyListPage studyList = null;
    static String appURL = null;

    @BeforeTest
    @Parameters({"browser"})
    public void setupForTest(String browser) {
        ThreadContext.put("threadName", Logger.logLocation(browser) + "\\" + testProtocol);
        appURL = Utils.getAutomationProperties().getProperty("OHIFViewerApplication");
    }

    @BeforeMethod
    @Parameters({"browser"})
    public void launchURL(String browser) throws MalformedURLException {
        theDriver = (RemoteWebDriver) TestClass.getInstance().setUpDriver(browser);
        setDriver(theDriver);
        loginPage = new LoginPage(theDriver);
        viewerPage = new OHIFViewerPage(theDriver);
        studyList = new StudyListPage(theDriver);
        loginPage.launchApplication(appURL);
    }

    @Test
    public void loadStudy() throws Exception {
        String testID = "Viewer_1";
        Reporter.log("<br><strong>Testcase ID: </strong>" + testID + "<br>");
        String testCaseName = "loadStudy";
        String patientName = ExcelUtils.getTestData(testData, "Viewer_1", testID, "Pat_1");
        String patientMRN = ExcelUtils.getTestData(testData, "Viewer_1", testID, "MRN_1");
        String accessionNumber = ExcelUtils.getTestData(testData, "Viewer_1", testID, "Accession_1");
        Reporter.log("<br>1. Enter the Patient MRN in search field <br>");
        studyList.searchStudy("patientName", patientMRN);
        Reporter.log("<br>2. Click on the patient name "+patientMRN+" in study list page based on accession number<br>");
        studyList.selectStudy("accessionNumber", accessionNumber);
        Reporter.log("<br>3. Verify Patient name in the viewer <br>");
        String actualPatientName = viewerPage.getPatientNameFromViewer();
        Assert.assertTrue(actualPatientName.contains(patientName));
        Reporter.log("<br>4. Study Loaded successfully <br>");
    }

    @AfterMethod
    public void tearDown() {
        if (theDriver != null) {
            TestClass.getInstance().removeDriver();
        }
    }
}
