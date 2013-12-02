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
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Tests the Java EE web application components.
 */
public class SampleIT {

    /**
     * Initializes the {@link WebDriver} and {@link WebDriverWait}. Uses Firefox
     * as it is readily available.
     */
    @Before
    public void setupDriver() {
        driver = new FirefoxDriver();
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
     * Web driver used for testing.
     */
    private WebDriver driver;

    /**
     * Web driver wait object to allow waiting for conditions to occur.
     */
    private WebDriverWait wait;

    /**
     * Tests the servlet call.
     */
    @Test
    public void testServlet() {
        driver.get("http://localhost:18080/app/hello");
        assertThat(driver.findElement(By.id("text")).getText(),
                startsWith("Hello servlet on "));
    }

    /**
     * Tests a REST API call using Angular to make the call to the REST API.
     */
    @Test
    public void testRest() {
        driver.get("http://localhost:18080/app/");
        assertThat(driver.findElement(By.id("attribute")).getText(),
                is("attribute value"));
        wait.until(textToBePresentInElement(By.id("message"), "Hello JAX-RS"));
        assertThat(driver.findElement(By.id("message")).getText(),
                is("Hello JAX-RS"));
    }
}