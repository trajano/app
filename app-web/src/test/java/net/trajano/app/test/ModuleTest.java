package net.trajano.app.test;

import net.trajano.app.Bean;

import org.junit.Test;

/**
 * Tests the module.
 */
public class ModuleTest {

    /**
     * Tests the module method.
     */
    @Test
    public void testMethod() {
        new Bean().setMessage("Hello");
    }
}