package com.ravsoft.framework.automation.ui.utils;

import com.pixelmed.dicom.*;
import com.pixelmed.network.MultipleInstanceTransferStatusHandler;
import com.pixelmed.network.StorageSOPClassSCU;
import java.io.File;

public class DicomUtils {

    public static boolean importStudy(String hostname, int port, String calledAETitle, String callingAETitle, String dicomLocation) {
        boolean isImported = false;
        try {
            File directoryPath = new File(dicomLocation);
            File dicomFile[] = directoryPath.listFiles();
            SetOfDicomFiles dicomFilesToTransmit = new SetOfDicomFiles();
            for (File file : dicomFile) {
                Logger.instance.info("File name: " + file.getName());
                Logger.instance.info("File path: " + file.getAbsolutePath());
                dicomFilesToTransmit.add(file);
            }
            Logger.instance.info("Initiating C-STORE operation...");
            StorageSOPClassSCU storage = new StorageSOPClassSCU(hostname, port, calledAETitle, callingAETitle, dicomFilesToTransmit, 0, new UpdateStudyStatus());
            if(storage.encounteredTrappedExceptions()) {
                isImported = false;
            } else {
                isImported = true;
            }
        } catch (Exception exp) {
            Logger.instance.error("Failed to import studies : " + exp);
        }
        return isImported;
    }

    public static String getDicomTagValue(AttributeTag attrTag, String dicomFile) throws Exception {
        AttributeList attribute = new AttributeList();
        attribute.read(dicomFile);
        return Attribute.getDelimitedStringValuesOrEmptyString(attribute, attrTag);
    }

    public static String getDumpDicomTags(String dicomFile) throws Exception {
        AttributeList attribute = new AttributeList();
        attribute.read(dicomFile);
        Logger.instance.info(attribute.toString());
        return attribute.toString();
    }
}

class UpdateStudyStatus extends MultipleInstanceTransferStatusHandler {
    @Override
    public void updateStatus(int remaining, int completed, int failed, int warning, String sopInstanceUID) {
        Logger.instance.info("SOP Instance ID being transferred:" + sopInstanceUID);
        Logger.instance.info("Items in Queue:" + remaining);
        Logger.instance.info("Transfers Completed:" + completed);
        Logger.instance.info("Failed Transmits:" + failed);
        Logger.instance.info("Warnings:" + warning);
    }
}