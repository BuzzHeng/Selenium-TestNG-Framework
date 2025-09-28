package com.buzzheng.TestComponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ExtentReports configuration and setup utility
 * Provides centralized reporting configuration for test automation
 */
public class ExtentReporterNG {

    private static ExtentReports extent;
    private static final String REPORTS_DIR = System.getProperty("user.dir") + "/test-output/reports/";
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    /**
     * Get ExtentReports instance with configuration
     * @return Configured ExtentReports instance
     */
    public static ExtentReports getReportObject() {
        if (extent == null) {
            createReportInstance();
        }
        return extent;
    }

    /**
     * Create ExtentReports instance with custom configuration
     */
    private static void createReportInstance() {
        // Create reports directory if it doesn't exist
        createReportsDirectory();

        // Generate timestamped report file name
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        String fileName = "TestReport_" + timestamp + ".html";
        String filePath = REPORTS_DIR + fileName;

        // Configure Spark Reporter
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(filePath);
        configureSparkReporter(sparkReporter);

        // Create ExtentReports instance
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Set system information
        setSystemInformation();

        System.out.println("📊 ExtentReports initialized: " + filePath);
    }

    /**
     * Configure Spark Reporter settings
     */
    private static void configureSparkReporter(ExtentSparkReporter reporter) {
        // Basic configuration
        reporter.config().setReportName("QA Automation Test Results");
        reporter.config().setDocumentTitle("Test Execution Report");
        reporter.config().setTheme(Theme.STANDARD);

        // Timeline and system info
        reporter.config().setTimelineEnabled(true);
        reporter.config().setEncoding("UTF-8");

        // Custom CSS and JavaScript (optional)
        reporter.config().setCss(getCustomCSS());
        reporter.config().setJs(getCustomJS());

        // Logo and additional info
        reporter.config().thumbnailForBase64(true);
    }

    /**
     * Set system information in the report
     */
    private static void setSystemInformation() {
        extent.setSystemInfo("Tester", "Yuan Heng Lee");
        extent.setSystemInfo("Environment", System.getProperty("env", "local"));
        extent.setSystemInfo("Browser", System.getProperty("browser", "chrome"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("User Directory", System.getProperty("user.dir"));

        // Add execution details
        extent.setSystemInfo("Execution Mode", System.getProperty("execution", "local"));
        extent.setSystemInfo("Headless", System.getProperty("headless", "false"));
        extent.setSystemInfo("Report Generated", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    /**
     * Create reports directory
     */
    private static void createReportsDirectory() {
        File reportsDir = new File(REPORTS_DIR);
        if (!reportsDir.exists()) {
            if (reportsDir.mkdirs()) {
                System.out.println("📁 Created reports directory: " + REPORTS_DIR);
            } else {
                System.err.println("❌ Failed to create reports directory: " + REPORTS_DIR);
            }
        }
    }

    /**
     * Get custom CSS for report styling
     */
    private static String getCustomCSS() {
        return """
            .brand-logo {
                display: none;
            }
            .nav-wrapper {
                background-color: #1565C0 !important;
            }
            .test-status.pass {
                background-color: #4CAF50 !important;
            }
            .test-status.fail {
                background-color: #F44336 !important;
            }
            .test-status.skip {
                background-color: #FF9800 !important;
            }
            """;
    }

    /**
     * Get custom JavaScript for report functionality
     */
    private static String getCustomJS() {
        return """
            // Add any custom JavaScript functionality here
            console.log('ExtentReports custom JS loaded');
            """;
    }

    /**
     * Flush the ExtentReports instance
     * Should be called at the end of test suite
     */
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
            System.out.println("✅ ExtentReports flushed successfully");
        }
    }

    /**
     * Clean up old report files (older than specified days)
     * @param daysToKeep Number of days to keep reports
     */
    public static void cleanupOldReports(int daysToKeep) {
        try {
            File reportsDir = new File(REPORTS_DIR);
            if (!reportsDir.exists()) {
                return;
            }

            long cutoffTime = System.currentTimeMillis() - (daysToKeep * 24L * 60L * 60L * 1000L);

            File[] files = reportsDir.listFiles();
            if (files != null) {
                int deletedCount = 0;
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".html") &&
                            file.lastModified() < cutoffTime) {
                        if (file.delete()) {
                            deletedCount++;
                        }
                    }
                }
                if (deletedCount > 0) {
                    System.out.println("🗑️ Cleaned up " + deletedCount + " old report files");
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to cleanup old reports: " + e.getMessage());
        }
    }

    /**
     * Get the current report file path
     */
    public static String getCurrentReportPath() {
        // This would need to be tracked during report creation
        // For now, return the reports directory
        return REPORTS_DIR;
    }
}