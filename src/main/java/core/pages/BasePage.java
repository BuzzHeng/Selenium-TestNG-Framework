package core.pages;

import core.utils.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    private final int TIMEOUT;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.TIMEOUT = getTimeoutFromConfig();
    }

    protected void navigateTo(String url) {
        driver.get(url);
    }

    protected WebElement find(By locator) {
        return driver.findElement(locator);
    }

    protected void click(By locator) {
        find(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement element = find(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return find(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected void waitForVisible(By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForVisible(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
                .until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForVisible(By locator, int timeout) {
        new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForVisible(WebElement element, int timeout) {
        new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public String getTitle() {
        return driver.getTitle();
    }

    private int getTimeoutFromConfig() {
        String timeoutStr = ConfigReader.get("timeout");
        if (timeoutStr != null) {
            try {
                return Integer.parseInt(timeoutStr);
            } catch (NumberFormatException ignored) {
                // Log warning if needed
            }
        }
        return 10; // Default timeout
    }
}
