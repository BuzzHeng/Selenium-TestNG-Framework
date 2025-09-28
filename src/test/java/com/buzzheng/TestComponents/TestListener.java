package com.buzzheng.TestComponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.buzzheng.TestComponents.BaseTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ScreenshotUtils;

import java.io.File;

/**
 * TestNG Listener for handling test execution events
 * Captures screenshots, logs test results, and integrates with reporting
 */
public class TestListener extends BaseTest implements ITestListener {

    ExtentTest test;
    ExtentReports extent = ExtentReporterNG.getReportObject();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); //ThreadSafe

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String className = result.getTestClass().getRealClass().getSimpleName();
        System.out.println("\n🧪 Starting Test: " + className + "." + testName);

        // Create a new test in ExtentReports
        test = extent.createTest(testName);
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String className = result.getTestClass().getRealClass().getSimpleName();
        long duration = result.getEndMillis() - result.getStartMillis();
        System.out.println("✅ Test PASSED: " + className + "." + testName + " (" + duration + "ms)");
        extentTest.get().log(Status.PASS,"✅ Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        String testName = result.getMethod().getMethodName();
        String className = result.getTestClass().getRealClass().getSimpleName();
        long duration = result.getEndMillis() - result.getStartMillis();

        System.err.println("❌ Test FAILED: " + className + "." + testName + " (" + duration + "ms)");
        System.err.println("❌ Failure Reason: " + result.getThrowable().getMessage());
        // Log failure in ExtentReports
        extentTest.get().fail(result.getThrowable());

        // Take screenshot if WebDriver is available
        if (BaseTest.getDriver() != null) {
            String screenshotPath = ScreenshotUtils.takeScreenshotOnFailure(
                    BaseTest.getDriver(),
                    className + "_" + testName
            );
            System.out.println("📸 Screenshot saved at: " + screenshotPath);

            if (screenshotPath != null) {
                // Attach to TestNG result for reporting
                System.setProperty("screenshot.path." + testName, screenshotPath);
                // Attach screenshot to ExtentReports
                try {
                    String relativePath = "../screenshots/" + new File(screenshotPath).getName();
                    System.out.println("📎 Attaching screenshot to report: " + relativePath);
                    test.addScreenCaptureFromPath(relativePath, testName);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String className = result.getTestClass().getRealClass().getSimpleName();
        System.out.println("⏭️ Test SKIPPED: " + className + "." + testName);
        System.out.println("⏭️ Skip Reason: " + result.getThrowable().getMessage());

        // Log skipped test in ExtentReports
        extentTest.get().skip(result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String className = result.getTestClass().getRealClass().getSimpleName();
        System.out.println("⚠️ Test FAILED but within success percentage: " + className + "." + testName);
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}