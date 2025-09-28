package ui.driver;

import config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * WebDriver Factory for creating browser instances
 * Supports local, grid, and docker execution modes
 */
public class WebDriverFactory {

    /**
     * Create WebDriver instance based on configuration
     * @return WebDriver instance
     */
    public static WebDriver createDriver() {
        String execution = Config.getExecution();
        String browser = Config.getBrowser();

        WebDriver driver = switch (execution) {
            case "local" -> createLocalDriver(browser);
            case "grid", "docker" -> createRemoteDriver(browser);
            default -> throw new IllegalArgumentException("❌ Unsupported execution mode: " + execution);
        };

        // Set timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Config.getImplicitTimeout()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Config.getPageLoadTimeout()));

        System.out.println("✅ WebDriver created: " + browser + " (" + execution + ")");
        return driver;
    }

    /**
     * Create local WebDriver instance
     */
    private static WebDriver createLocalDriver(String browser) {
        return switch (browser.toLowerCase()) {
            case "chrome" -> createChromeDriver();
            case "firefox" -> createFirefoxDriver();
            case "edge" -> createEdgeDriver();
            default -> throw new IllegalArgumentException("❌ Unsupported browser: " + browser);
        };
    }

    /**
     * Create remote WebDriver instance for Grid/Docker
     */
    private static WebDriver createRemoteDriver(String browser) {
        try {
            URL gridUrl = new URL(Config.getGridUrl());
            DesiredCapabilities capabilities = switch (browser.toLowerCase()) {
                case "chrome" -> getChromeCapabilities();
                case "firefox" -> getFirefoxCapabilities();
                case "edge" -> getEdgeCapabilities();
                default -> throw new IllegalArgumentException("❌ Unsupported browser for remote: " + browser);
            };

            return new RemoteWebDriver(gridUrl, capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException("❌ Invalid Grid URL: " + Config.getGridUrl(), e);
        }
    }

    // ======================
    // Local Driver Creation
    // ======================
    private static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        configureChromeOptions(options);
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        configureFirefoxOptions(options);
        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver() {
        EdgeOptions options = new EdgeOptions();
        configureEdgeOptions(options);
        return new EdgeDriver(options);
    }

    // ======================
    // Chrome Configuration
    // ======================
    private static void configureChromeOptions(ChromeOptions options) {
        // Performance optimizations
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-plugins");
        options.addArguments("--disable-images");
        options.addArguments("--disable-popup-blocking");

        // Security and privacy
        options.addArguments("--disable-web-security");
        options.addArguments("--disable-features=VizDisplayCompositor");
        options.addArguments("--ignore-ssl-errors=yes");
        options.addArguments("--ignore-certificate-errors");

        // Headless mode
        if (Config.isHeadless()) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        // Additional preferences
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--disable-blink-features=AutomationControlled");
    }

    private static DesiredCapabilities getChromeCapabilities() {
        ChromeOptions options = new ChromeOptions();
        configureChromeOptions(options);

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName("chrome");
        caps.setCapability(ChromeOptions.CAPABILITY, options);
        return caps;
    }

    // ======================
    // Firefox Configuration
    // ======================
    private static void configureFirefoxOptions(FirefoxOptions options) {
        // Performance settings
        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("media.volume_scale", "0.0");

        // Headless mode
        if (Config.isHeadless()) {
            options.addArguments("--headless");
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
        }

        // Security settings
        options.addPreference("security.tls.insecure_fallback_hosts", "localhost");
        options.addPreference("security.fileuri.strict_origin_policy", false);
    }

    private static DesiredCapabilities getFirefoxCapabilities() {
        FirefoxOptions options = new FirefoxOptions();
        configureFirefoxOptions(options);

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName("firefox");
        caps.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
        return caps;
    }

    // ======================
    // Edge Configuration
    // ======================
    private static void configureEdgeOptions(EdgeOptions options) {
        // Similar to Chrome options
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        if (Config.isHeadless()) {
            options.addArguments("--headless");
            options.addArguments("--window-size=1920,1080");
        }
    }

    private static DesiredCapabilities getEdgeCapabilities() {
        EdgeOptions options = new EdgeOptions();
        configureEdgeOptions(options);

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName("MicrosoftEdge");
        caps.setCapability(EdgeOptions.CAPABILITY, options);
        return caps;
    }

    // ======================
    // Utility Methods
    // ======================
    public static void quitDriver(WebDriver driver) {
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("✅ WebDriver quit successfully");
            } catch (Exception e) {
                System.err.println("❌ Error quitting WebDriver: " + e.getMessage());
            }
        }
    }
}