package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Screenshot utility class for capturing and saving screenshots
 * Provides static methods for screenshot operations
 */
public class ScreenshotUtils {

    private static final String SCREENSHOT_DIR = "test-output/screenshots/";
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    /**
     * Take screenshot and save to file
     * @param driver WebDriver instance
     * @param testName Name of the test (used for filename)
     * @return File path of saved screenshot, null if failed
     */
    public static String takeScreenshot(WebDriver driver, String testName) {
        if (driver == null) {
            System.err.println("❌ Cannot take screenshot - WebDriver is null");
            return null;
        }

        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);

            String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
            String fileName = testName + "_" + timestamp + ".png";
            String filePath = SCREENSHOT_DIR + fileName;

            // Ensure directory exists
            createScreenshotDirectory();

            // Copy screenshot file
            Files.copy(sourceFile.toPath(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("📸 Screenshot saved: " + filePath);
            return filePath;

        } catch (WebDriverException | IOException e) {
            System.err.println("❌ Failed to take screenshot for: " + testName);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Take screenshot on test failure
     * @param driver WebDriver instance
     * @param testName Name of the failed test
     * @return File path of saved screenshot
     */
    public static String takeScreenshotOnFailure(WebDriver driver, String testName) {
        System.out.println("📸 Taking screenshot for failed test: " + testName);
        return takeScreenshot(driver, "FAILED_" + testName);
    }

    /**
     * Take screenshot with custom prefix
     * @param driver WebDriver instance
     * @param testName Test name
     * @param prefix Custom prefix for filename
     * @return File path of saved screenshot
     */
    public static String takeScreenshotWithPrefix(WebDriver driver, String testName, String prefix) {
        return takeScreenshot(driver, prefix + "_" + testName);
    }

    /**
     * Take screenshot for debugging purposes
     * @param driver WebDriver instance
     * @param testName Test name
     * @param stepDescription Description of the current step
     * @return File path of saved screenshot
     */
    public static String takeDebugScreenshot(WebDriver driver, String testName, String stepDescription) {
        String debugName = testName + "_" + stepDescription.replaceAll("[^a-zA-Z0-9]", "_");
        return takeScreenshot(driver, "DEBUG_" + debugName);
    }

    /**
     * Take screenshot and return as byte array (useful for Allure reports)
     * @param driver WebDriver instance
     * @return Screenshot as byte array, null if failed
     */
    public static byte[] takeScreenshotAsBytes(WebDriver driver) {
        if (driver == null) {
            System.err.println("❌ Cannot take screenshot - WebDriver is null");
            return null;
        }

        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            return screenshot.getScreenshotAs(OutputType.BYTES);
        } catch (WebDriverException e) {
            System.err.println("❌ Failed to take screenshot as bytes");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Take screenshot and return as base64 string
     * @param driver WebDriver instance
     * @return Screenshot as base64 string, null if failed
     */
    public static String takeScreenshotAsBase64(WebDriver driver) {
        if (driver == null) {
            System.err.println("❌ Cannot take screenshot - WebDriver is null");
            return null;
        }

        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            return screenshot.getScreenshotAs(OutputType.BASE64);
        } catch (WebDriverException e) {
            System.err.println("❌ Failed to take screenshot as base64");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Create screenshot directory if it doesn't exist
     */
    public static void createScreenshotDirectory() {
        try {
            Files.createDirectories(Paths.get(SCREENSHOT_DIR));
        } catch (IOException e) {
            System.err.println("❌ Failed to create screenshot directory: " + SCREENSHOT_DIR);
            e.printStackTrace();
        }
    }

    /**
     * Clean up old screenshots (older than specified days)
     * @param daysToKeep Number of days to keep screenshots
     */
    public static void cleanupOldScreenshots(int daysToKeep) {
        try {
            File screenshotDir = new File(SCREENSHOT_DIR);
            if (!screenshotDir.exists()) {
                return;
            }

            long cutoffTime = System.currentTimeMillis() - (daysToKeep * 24L * 60L * 60L * 1000L);

            File[] files = screenshotDir.listFiles();
            if (files != null) {
                int deletedCount = 0;
                for (File file : files) {
                    if (file.isFile() && file.lastModified() < cutoffTime) {
                        if (file.delete()) {
                            deletedCount++;
                        }
                    }
                }
                if (deletedCount > 0) {
                    System.out.println("🗑️ Cleaned up " + deletedCount + " old screenshots");
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to cleanup old screenshots");
            e.printStackTrace();
        }
    }

    /**
     * Get screenshot directory path
     * @return Screenshot directory path
     */
    public static String getScreenshotDirectory() {
        return SCREENSHOT_DIR;
    }

    /**
     * Check if WebDriver supports screenshot functionality
     * @param driver WebDriver instance
     * @return true if driver supports screenshots, false otherwise
     */
    public static boolean supportsScreenshots(WebDriver driver) {
        return driver instanceof TakesScreenshot;
    }
}