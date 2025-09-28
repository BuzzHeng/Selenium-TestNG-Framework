package com.buzzheng.TestComponents;

import config.Config;
import org.apache.logging.log4j.ThreadContext;
import ui.driver.WebDriverFactory;
import utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.*;
import org.testng.ITestResult;

import java.time.Duration;

/**
 * Base test class for all test implementations
 * Handles WebDriver lifecycle and common utilities
 */
public class BaseTest {

    // Thread-safe WebDriver instances for parallel execution
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> waitThreadLocal = new ThreadLocal<>();

    protected WebDriver driver;
    protected WebDriverWait wait;

    // ======================
    // Test Lifecycle Methods
    // ======================

    @BeforeSuite(alwaysRun = true)
    public void suiteSetup() {
        System.out.println("\n🚀 Starting Test Suite Execution");
        Config.printConfiguration();
        ScreenshotUtils.createScreenshotDirectory();
    }

    @AfterSuite(alwaysRun = true)
    public void suiteTeardown() {
        System.out.println("\n✅ Test Suite Execution Completed");
    }

    /**
     * Setup method for UI and E2E tests
     * Only initializes WebDriver for tests that need it
     */
    @BeforeMethod(groups = {"ui", "e2e"}, alwaysRun = true)
    public void setUp(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String className = result.getTestClass().getRealClass().getSimpleName();

        ThreadContext.put("testName", result.getMethod().getMethodName());  // for log4j2
        System.out.println("\n🔧 Setting up test: " + className + "." + testName);

        try {
            // Create WebDriver instance
            WebDriver webDriver = WebDriverFactory.createDriver();
            driverThreadLocal.set(webDriver);
            this.driver = webDriver;

            // Create WebDriverWait instance
            WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(Config.getExplicitTimeout()));
            waitThreadLocal.set(webDriverWait);
            this.wait = webDriverWait;

            // Configure browser window
            if (!Config.isHeadless()) {
                driver.manage().window().maximize();
            }

            // Navigate to base URL
            driver.get(Config.getBaseUrl());
            waitForPageLoad();

            System.out.println("✅ Test setup completed for: " + testName);

        } catch (Exception e) {
            System.err.println("❌ Failed to setup test: " + testName);
            e.printStackTrace();
            throw new RuntimeException("Test setup failed", e);
        }
    }

    /**
     * Teardown method for UI and E2E tests
     */
    @AfterMethod(groups = {"ui", "e2e"}, alwaysRun = true)
    public void tearDown(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String className = result.getTestClass().getRealClass().getSimpleName();

        // Take screenshot on failure
        if (result.getStatus() == ITestResult.FAILURE) {
            ScreenshotUtils.takeScreenshotOnFailure(driver, className + "_" + testName);
        }

        // Quit WebDriver
        WebDriver webDriver = driverThreadLocal.get();
        if (webDriver != null) {
            try {
                WebDriverFactory.quitDriver(webDriver);
                System.out.println("✅ Test teardown completed for: " + testName);
            } catch (Exception e) {
                System.err.println("❌ Error during teardown for: " + testName);
                e.printStackTrace();
            } finally {
                driverThreadLocal.remove();
                waitThreadLocal.remove();
                this.driver = null;
                this.wait = null;
            }
        }
        ThreadContext.remove("testName");
    }

    /**
     * Setup method for API and DB tests
     * No WebDriver initialization needed
     */
    @BeforeMethod(groups = {"api", "db"}, alwaysRun = true)
    public void setUpNonUI(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String className = result.getTestClass().getRealClass().getSimpleName();
        System.out.println("\n🔧 Setting up non-UI test: " + className + "." + testName);
    }

    @AfterMethod(groups = {"api", "db"}, alwaysRun = true)
    public void tearDownNonUI(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("✅ Non-UI test completed: " + testName);
    }

    // ======================
    // Utility Methods
    // ======================

    /**
     * Wait for page to load completely
     */
    protected void waitForPageLoad() {
        if (driver != null) {
            wait.until(webDriver -> {
                String readyState = ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").toString();
                return "complete".equals(readyState);
            });
        }
    }

    /**
     * Wait for page to load with custom timeout
     */
    protected void waitForPageLoad(int timeoutSeconds) {
        if (driver != null) {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            customWait.until(webDriver -> {
                String readyState = ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").toString();
                return "complete".equals(readyState);
            });
        }
    }

    /**
     * Take screenshot using ScreenshotUtils
     */
    protected String takeScreenshot(String testName) {
        return ScreenshotUtils.takeScreenshot(driver, testName);
    }

    /**
     * Get current WebDriver instance (thread-safe)
     */
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    /**
     * Get current WebDriverWait instance (thread-safe)
     */
    public static WebDriverWait getWait() {
        return waitThreadLocal.get();
    }

    /**
     * Navigate to URL relative to base URL
     */
    protected void navigateToUrl(String relativePath) {
        if (driver != null) {
            String fullUrl = Config.getBaseUrl() + relativePath;
            System.out.println("🔗 Navigating to: " + fullUrl);
            driver.get(fullUrl);
            waitForPageLoad();
        }
    }

    /**
     * Refresh current page
     */
    protected void refreshPage() {
        if (driver != null) {
            System.out.println("🔄 Refreshing page");
            driver.navigate().refresh();
            waitForPageLoad();
        }
    }

    /**
     * Get current page title
     */
    protected String getPageTitle() {
        return driver != null ? driver.getTitle() : null;
    }

    /**
     * Get current page URL
     */
    protected String getCurrentUrl() {
        return driver != null ? driver.getCurrentUrl() : null;
    }
}