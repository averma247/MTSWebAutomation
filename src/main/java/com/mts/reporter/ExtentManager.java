package com.mts.reporter;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ExtentManager {

    public static final String REPORT_BASE_FOLDER = "Report";
    public static final String EXTENT_REPORT_FOLDER = "Extent";
    public static final String ARCHIVE_FOLDER_NAME = "Archive";
    public static final String ARCHIVE_EXTENSION = ".zip";
    private static final String EXTENT_REPORT_FILE = "Report.html";
    private static final String EXTENT_REPORT_TITLE = "Extent Report";
    private static final Logger logger = LogManager.getLogger(ExtentManager.class);
    private static ExtentReports extent;

    private ExtentManager() {
    }

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            archiveReport();
            createInstance(Paths.get(REPORT_BASE_FOLDER).resolve(EXTENT_REPORT_FOLDER).resolve(EXTENT_REPORT_FILE).toString());
        }
        return extent;
    }

    protected static synchronized ExtentReports createInstance(String fileName) {
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(fileName);
        extentSparkReporter.config().setTheme(Theme.STANDARD);
        extentSparkReporter.config().setDocumentTitle(EXTENT_REPORT_TITLE);
        extentSparkReporter.config().setReportName(EXTENT_REPORT_TITLE);
        extent = new ExtentReports();
        extent.setAnalysisStrategy(AnalysisStrategy.CLASS);
        extent.attachReporter(extentSparkReporter);
        return extent;
    }

    /**
     * Archive the previous report.
     */
    protected static void archiveReport() {
        StringBuilder archiveFile = new StringBuilder(Utils.getCurrentDateTimeAsString());
        Path archiveDir =
                Paths.get(REPORT_BASE_FOLDER).resolve(ARCHIVE_FOLDER_NAME);
        Path reportBaseDir =
                Paths.get(REPORT_BASE_FOLDER).resolve(EXTENT_REPORT_FOLDER);
        Path archivePath =
                archiveDir.resolve(
                        archiveFile.append(ARCHIVE_EXTENSION).toString());
        archivePath.getParent().toFile().mkdirs();

        try {
            Utils.zipFolder(reportBaseDir, archivePath);
        } catch (Exception archiveException) {
            logger.error(
                    "Error in creating zip folder for archive due to: {}", archiveException.getMessage());
        }
    }

}