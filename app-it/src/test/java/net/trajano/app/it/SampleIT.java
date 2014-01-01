package net.trajano.app.it;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Tests the Java EE web application components.
 */
public class SampleIT {
    /**
     * Integration test context.
     */
    private IntegrationTestContext context;

    /**
     * Terminates the {@link WebDriver}.
     */
    @After
    public void closeContext() {
        context.close();
    }

    @Before
    public void setupContext() throws IOException {
        context = new IntegrationTestContext();
    }

    /**
     * Tests a REST API call using Angular to make the call to the REST API.
     */
    @Test
    public void testRest() throws Exception {
        context.authenticate();
        context.open();
        context.getWait()
                .until(textToBePresentInElement(By.id("bean-message"),
                        "Hello JAX-RS"));
        context.getWait().until(
                textToBePresentInElement(By.id("cdi-message"), "Hello CDI"));
        context.getWait().until(
                textToBePresentInElement(By.id("jpa-message"), "Hello JPA"));

        assertThat(context.getDriver().findElement(By.id("bean-attribute"))
                .getText(), is("bean"));
        assertThat(context.getDriver().findElement(By.id("bean-message"))
                .getText(), is("Hello JAX-RS"));
        assertThat(context.getDriver().findElement(By.id("cdi-message"))
                .getText(), is("Hello CDI"));
        assertThat(context.getDriver().findElement(By.id("jpa-message"))
                .getText(), is("Hello JPA"));
    }

    /**
     * Tests the servlet call.
     */
    @Test
    public void testServlet() {
        context.openServlet();
        assertThat(context.getDriver().findElement(By.id("text")).getText(),
                startsWith("Hello servlet on "));
    }
}