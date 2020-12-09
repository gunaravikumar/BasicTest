package com.ravsoft.framework.automation.ui.pageobjects.ohif.viewer;

import com.ravsoft.framework.automation.ui.pageobjects.PageClass;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class StudyListPage extends PageClass {

    public StudyListPage(RemoteWebDriver theWebDriver)
    {
        super(theWebDriver);
    }

    //locator values
    private By nameAndMRNSearchField() {
        return By.id("filter-patientNameOrId");
    }
    private By descriptionAccessionAndModalityField() {
        return By.id("filter-accessionOrModalityOrDescription");
    }
    private By patientName(String patientName) {
        return By.xpath("//tr[.//text()='"+patientName+"']//td[@class='']");
    }
    private By accessionNumber(String accessionNumber) {
        return By.xpath("//tr[.//text()='"+accessionNumber+"']//td//div[@class='modalities']");
    }
    private By loadingIndicator() {
        return By.xpath("//div[@class='image-thumbnail-loading-indicator']");
    }

    //Test Methods here

    /**
     * This method is used to enter the given value in the appropriate search field
     * @param label - patientName / patientMRN / description / accession / modality
     * @param value - attribute value to search
     * @throws Exception
     */
    public void searchStudy(String label, String value) throws Exception {
        if(label.contains("patientName") || label.contains("patientMRN")) {
            sendKeys(nameAndMRNSearchField(), value);
        }
        else if(label.contains("description") || label.contains("accession") || label.contains("modality")) {
            sendKeys(descriptionAccessionAndModalityField(), value);
        }

    }

    /**
     * This method is used to click the study based on the label
     * @param label - patientName / accessionNumber
     * @param value - attribute value to search
     * @throws Exception
     */
    public void selectStudy(String label, String value) throws Exception {
        if(label.contains("patientName")) {
            click(patientName(value));
        }
        else if(label.contains("accessionNumber")) {
            click(accessionNumber(value));
        }
        waitTillStudyLoad();
    }

    /**
     * This method is used to wait till the study gets loaded
     * @throws InterruptedException
     */
    public void waitTillStudyLoad() throws InterruptedException {
        int attempt = 1;
        while(attempt <5) {
            waitAndLoseElement(60, loadingIndicator());
            Thread.sleep(500);
            attempt++;
        }
    }

}
