package com.ravsoft.framework.automation.ui.pageobjects.ohif.viewer;

import com.ravsoft.framework.automation.ui.pageobjects.PageClass;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class OHIFViewerPage extends PageClass {

    public OHIFViewerPage(RemoteWebDriver theWebDriver)
    {
        super(theWebDriver);
    }

    //locator values

    private By patientNameInViewer() {
        return By.xpath("//div[@class='top-left overlay-element']");
    }

    //Test Methods here

    /**
     * This Method searches for patient name in the viewer page and returns the same
     * @return
     * @throws Exception
     */
    public String getPatientNameFromViewer() throws Exception {
            return getText(patientNameInViewer());
    }

}
