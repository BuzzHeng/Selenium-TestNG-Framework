package com.buzzheng.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retry analyzer for handling flaky tests
 * Automatically retries failed tests up to a specified limit
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = getMaxRetryCount();

    /**
     * Determines if a test should be retried
     * @param result Test result
     * @return true if test should be retried, false otherwise
     */
    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT) {
            String testName = result.getMethod().getMethodName();
            String className = result.getTestClass().getRealClass().getSimpleName();

            retryCount++;
            System.out.println("🔄 Retrying test: " + className + "." + testName
                    + " (Attempt " + (retryCount + 1) + " of " + (MAX_RETRY_COUNT + 1) + ")");
            System.out.println("🔄 Retry reason: " + result.getThrowable().getMessage());

            return true;
        }

        return false;
    }

    /**
     * Get maximum retry count from system properties or default
     * @return Maximum retry count
     */
    private static int getMaxRetryCount() {
        String retryCountStr = System.getProperty("retry.count", "2");
        try {
            return Integer.parseInt(retryCountStr);
        } catch (NumberFormatException e) {
            System.err.println("❌ Invalid retry count: " + retryCountStr + ". Using default: 2");
            return 2;
        }
    }

    /**
     * Get current retry count
     * @return Current retry count
     */
    public int getRetryCount() {
        return retryCount;
    }

    /**
     * Reset retry count (useful for test isolation)
     */
    public void resetRetryCount() {
        retryCount = 0;
    }
}