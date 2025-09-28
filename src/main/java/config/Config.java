package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration manager for test automation framework
 * Handles environment-specific properties and CLI overrides
 */
public class Config {
    private static Properties properties;
    private static final String DEFAULT_CONFIG_PATH = "src/test/resources/config/";

    static {
        loadProperties();
    }

    /**
     * Load properties based on environment
     * Priority: CLI params > Environment file > Default values
     */
    private static void loadProperties() {
        properties = new Properties();

        // Determine environment (default: local)
        String env = System.getProperty("env", "local");
        String configFile = DEFAULT_CONFIG_PATH + env + ".properties";

        try (FileInputStream fis = new FileInputStream(configFile)) {
            properties.load(fis);
            System.out.println("✅ Loaded config: " + configFile);
        } catch (IOException e) {
            System.err.println("❌ Failed to load config: " + configFile);
            System.err.println("Using default values...");
            loadDefaultProperties();
        }
    }

    /**
     * Load default properties as fallback
     */
    private static void loadDefaultProperties() {
        properties.setProperty("browser", "chrome");
        properties.setProperty("execution", "local");
        properties.setProperty("headless", "false");
        properties.setProperty("base.url", "https://example.com");
        properties.setProperty("grid.url", "http://localhost:4444/wd/hub");
        properties.setProperty("timeout.implicit", "10");
        properties.setProperty("timeout.explicit", "15");
    }

    /**
     * Get property with CLI override support
     */
    private static String getProperty(String key, String defaultValue) {
        // CLI parameter has highest priority
        String cliValue = System.getProperty(key);
        if (cliValue != null && !cliValue.trim().isEmpty()) {
            return cliValue.trim();
        }

        // Then config file value
        String configValue = properties.getProperty(key);
        if (configValue != null && !configValue.trim().isEmpty()) {
            return configValue.trim();
        }

        // Finally default value
        return defaultValue;
    }

    // ======================
    // Browser Configuration
    // ======================
    public static String getBrowser() {
        return getProperty("browser", "chrome").toLowerCase();
    }

    public static String getExecution() {
        return getProperty("execution", "local").toLowerCase();
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }

    public static String getGridUrl() {
        return getProperty("grid.url", "http://localhost:4444/wd/hub");
    }

    // ======================
    // Application URLs
    // ======================
    public static String getBaseUrl() {
        return getProperty("base.url", "https://example.com");
    }

    public static String getApiBaseUrl() {
        return getProperty("api.base.url", getBaseUrl() + "/api");
    }

    // ======================
    // Timeouts
    // ======================
    public static int getImplicitTimeout() {
        return Integer.parseInt(getProperty("timeout.implicit", "10"));
    }

    public static int getExplicitTimeout() {
        return Integer.parseInt(getProperty("timeout.explicit", "15"));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("timeout.pageload", "30"));
    }

    // ======================
    // Database Configuration
    // ======================
    public static String getDbUrl() {
        return getProperty("db.url", "jdbc:mysql://localhost:3306/testdb");
    }

    public static String getDbUsername() {
        return getProperty("db.username", "testuser");
    }

    public static String getDbPassword() {
        return getProperty("db.password", "testpass");
    }

    // ======================
    // Parallel Execution
    // ======================
    public static int getThreadCount() {
        return Integer.parseInt(getProperty("thread.count", "1"));
    }

    public static String getParallelMode() {
        return getProperty("parallel", "none");
    }

    // ======================
    // Utility Methods
    // ======================
    public static void printConfiguration() {
        System.out.println("\n=== Test Configuration ===");
        System.out.println("Environment: " + System.getProperty("env", "local"));
        System.out.println("Browser: " + getBrowser());
        System.out.println("Execution: " + getExecution());
        System.out.println("Headless: " + isHeadless());
        System.out.println("Base URL: " + getBaseUrl());
        System.out.println("Grid URL: " + getGridUrl());
        System.out.println("Thread Count: " + getThreadCount());
        System.out.println("==========================\n");
    }
}