package net.trajano.app.it;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Contains the Selenium Driver and Wait objects used for testing.
 * 
 * @author Archimedes Trajano
 */
public class IntegrationTestContext implements AutoCloseable {
    /**
     * Web driver used for testing.
     */
    private final WebDriver driver;

    /**
     * URL Properties.
     */
    private final Properties urlProperties;

    /**
     * Web driver wait object to allow waiting for conditions to occur.
     */
    private final WebDriverWait wait;

    /**
     * Initializes the {@link WebDriver} and {@link WebDriverWait}. Uses Firefox
     * as it is readily available.
     * 
     * @throws IOException
     */
    public IntegrationTestContext() throws IOException {
        final FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setAcceptUntrustedCertificates(true);
        driver = new FirefoxDriver(firefoxProfile);
        wait = new WebDriverWait(driver, WebDriverWait.DEFAULT_SLEEP_TIMEOUT);
        urlProperties = new Properties();
        try (final InputStream is = getClass().getResourceAsStream(
                "Urls.properties")) {
            urlProperties.load(is);
        }

    }

    /**
     * Authenticates the current browser session.
     */
    public void authenticate() {
        driver.get(urlProperties.getProperty("authenticate"));
    }

    /**
     * Terminates the {@link WebDriver}.
     */
    @Override
    public void close() {
        driver.quit();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    /**
     * Opens up the main page of the app.
     */
    public void open() {
        driver.get(urlProperties.getProperty("base"));
    }

    /**
     * Opens up the servlet.
     */
    public void openServlet() {
        driver.get(urlProperties.getProperty("servlet"));
    }
}
