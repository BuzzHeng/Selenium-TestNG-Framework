package ui.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseComponent {
    private static final Logger logger = LogManager.getLogger(BaseComponent.class);
    WebDriver driver;
    int timeOut = 5;

    public BaseComponent(WebDriver driver) {
        this.driver = driver;
        logger.info("BaseComponent initialized with driver: {}", driver);
    }

    public void navigateTo(String url) {
        logger.info("Navigating to: {}", url);
        driver.get(url);
    }

    public WebElement find(By locator) {
        logger.debug("Finding element by locator: {}", locator);
        try {
            WebElement element = driver.findElement(locator);
            logger.debug("Element found: {}", locator);
            return element;
        } catch (NoSuchElementException e) {
            logger.warn("Element not found: {}", locator);
            throw e;
        }
    }

    public void click(By locator) {
        logger.info("Clicking element: {}", locator);
        find(locator).click();
    }

    public void type(By locator, String text) {
        logger.info("Typing '{}' into element: {}", text, locator);
        WebElement element = find(locator);
        element.clear();
        element.sendKeys(text);
    }

    public String getText(By locator) {
        logger.info("Getting text from element: {}", locator);
        String text = find(locator).getText();
        logger.debug("Text retrieved: '{}' from {}", text, locator);
        return find(locator).getText();
    }

    public boolean isDisplayed(By locator) {
        logger.debug("Checking if element is displayed: {}", locator);
        try {
            boolean displayed = find(locator).isDisplayed();
            logger.debug("Element displayed: {} - {}", locator, displayed);
            return displayed;
        } catch (NoSuchElementException e) {
            logger.warn("Element not found for display check: {}", locator);
            return false;
        }
    }

    public void waitForElementToAppear(By locator) {
        logger.info("Waiting for element to appear: {}", locator);
        new WebDriverWait(driver, Duration.ofSeconds(timeOut))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        logger.debug("Element appeared: {}", locator);
    }

    public void waitForElementToAppear(WebElement ele) {
        logger.info("Waiting for element to appear: {}", ele);
        new WebDriverWait(driver, Duration.ofSeconds(timeOut))
                .until(ExpectedConditions.visibilityOf(ele));
    }

    public void waitForElementToAppear(By locator, int timeout) {
        logger.info("Waiting for element to appear: {}", locator);
        new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        logger.debug("Element appeared: {}", locator);
    }

    public void waitForElementToAppear(WebElement ele, int timeout) {
        logger.info("Waiting for element to appear: {}", ele);
        new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.visibilityOf(ele));
        logger.debug("Element appeared: {}", ele);
    }

    public void waitForElementToDisappear(By findBy) throws InterruptedException {
        logger.info("Waiting for element to disappear: {}", findBy);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(findBy));
        logger.debug("Element disappeared: {}", findBy);
    }
    public void waitForElementToDisappear(WebElement ele) throws InterruptedException {
        logger.info("Waiting for element to disappear: {}", ele);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.invisibilityOf(ele));
        logger.debug("Element disappeared: {}", ele);
    }

    public void waitForElementToDisappear(By findBy, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(findBy));
    }

    public void waitForElementToDisappear(WebElement ele, int timeout) {
        logger.info("Waiting for element to disappear: {}", ele);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.invisibilityOf(ele));
        logger.debug("Element disappeared: {}", ele);
    }

    public String getTitle() {
        String title = driver.getTitle();
        logger.info("Page title retrieved: {}", title);
        return title;
    }
}
