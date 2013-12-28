package net.trajano.app.it;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Tests the Java EE web application components.
 */
public class SampleIT {

    /**
     * Web driver used for testing.
     */
    private WebDriver driver;

    /**
     * Web driver wait object to allow waiting for conditions to occur.
     */
    private WebDriverWait wait;

    /**
     * Initializes the {@link WebDriver} and {@link WebDriverWait}. Uses Firefox
     * as it is readily available.
     */
    @Before
    public void setupDriver() {
        final FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setAcceptUntrustedCertificates(true);
        driver = new FirefoxDriver(firefoxProfile);
        wait = new WebDriverWait(driver, WebDriverWait.DEFAULT_SLEEP_TIMEOUT);
    }

    /**
     * Terminates the {@link WebDriver}.
     */
    @After
    public void teardownDriver() {
        driver.quit();
    }

    /**
     * Tests a REST API call using Angular to make the call to the REST API.
     */
    @Test
    public void testRest() {
        driver.get("https://trajano:trajano@localhost:18181/app/rest");
        driver.get("http://localhost:18080/app");
        wait.until(textToBePresentInElement(By.id("bean-message"),
                "Hello JAX-RS"));
        wait.until(textToBePresentInElement(By.id("cdi-message"), "Hello CDI"));
        wait.until(textToBePresentInElement(By.id("jpa-message"), "Hello JPA"));

        assertThat(driver.findElement(By.id("bean-attribute")).getText(),
                is("bean"));
        assertThat(driver.findElement(By.id("bean-message")).getText(),
                is("Hello JAX-RS"));
        assertThat(driver.findElement(By.id("cdi-message")).getText(),
                is("Hello CDI"));
        assertThat(driver.findElement(By.id("jpa-message")).getText(),
                is("Hello JPA"));
    }

    /**
     * Tests the servlet call.
     */
    @Test
    public void testServlet() {
        driver.get("http://localhost:18080/app/hello");
        assertThat(driver.findElement(By.id("text")).getText(),
                startsWith("Hello servlet on "));
    }
}