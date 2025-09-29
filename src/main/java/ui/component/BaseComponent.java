package ui.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BaseComponent {
    private static final Logger logger = LogManager.getLogger(BaseComponent.class);
    protected WebDriver driver;
    protected int timeOut = 5;

    public BaseComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); // Add this for @FindBy support
        logger.info("BaseComponent initialized for: {}", this.getClass().getSimpleName());
    }

    // Navigation
    public void navigateTo(String url) {
        logger.info("Navigating to: {}", url);
        driver.get(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        String title = driver.getTitle();
        logger.info("Page title retrieved: {}", title);
        return title;
    }

    // Element finding
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

    // Actions
    public void click(By locator) {
        logger.info("Clicking element: {}", locator);
        find(locator).click();
    }

    public void click(WebElement element) {
        logger.info("Clicking element: {}", element);
        element.click();
    }

    public void type(By locator, String text) {
        logger.info("Typing '{}' into element: {}", text, locator);
        WebElement element = find(locator);
        element.clear();
        element.sendKeys(text);
    }

    public void type(WebElement element, String text) {
        logger.info("Typing text into element: {}", element);
        element.clear();
        element.sendKeys(text);
    }

    public String getText(By locator) {
        logger.info("Getting text from element: {}", locator);
        String text = find(locator).getText();
        logger.debug("Text retrieved: '{}'", text);
        return text;
    }

    public String getText(WebElement element) {
        String text = element.getText();
        logger.debug("Text retrieved: '{}'", text);
        return text;
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

    public boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            logger.warn("Element not displayed");
            return false;
        }
    }

    // Wait methods
    public void waitForElementToAppear(By locator) {
        logger.info("Waiting for element to appear: {}", locator);
        new WebDriverWait(driver, Duration.ofSeconds(timeOut))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        logger.debug("Element appeared: {}", locator);
    }

    public void waitForElementToAppear(WebElement ele) {
        logger.info("Waiting for element to appear");
        new WebDriverWait(driver, Duration.ofSeconds(timeOut))
                .until(ExpectedConditions.visibilityOf(ele));
    }
    public void waitForElementToAppear(WebElement ele, int timeOut) {
        logger.info("Waiting for element to appear");
        new WebDriverWait(driver, Duration.ofSeconds(timeOut))
                .until(ExpectedConditions.visibilityOf(ele));
    }
    public void waitForElementToAppear(By locator, int timeout) {
        logger.info("Waiting for element to appear: {} with timeout: {}s", locator, timeout);
        new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForElementToDisappear(By findBy) {
        logger.info("Waiting for element to disappear: {}", findBy);
        new WebDriverWait(driver, Duration.ofSeconds(timeOut))
                .until(ExpectedConditions.invisibilityOfElementLocated(findBy));
        logger.debug("Element disappeared: {}", findBy);
    }

    public void waitForElementToDisappear(WebElement ele) {
        logger.info("Waiting for element to disappear");
        new WebDriverWait(driver, Duration.ofSeconds(timeOut))
                .until(ExpectedConditions.invisibilityOf(ele));
    }
}