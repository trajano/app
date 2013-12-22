package net.trajano.app.test;

import net.trajano.app.BeanResource;

import org.junit.Test;

/**
 * Tests the module.
 */
public class ResourceTest {

    /**
     * Tests the module method.
     */
    @Test
    public void testHello() {
        new BeanResource().helloBean();
    }
}