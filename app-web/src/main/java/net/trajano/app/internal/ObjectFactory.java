package net.trajano.app.internal;

import java.util.Date;

import javax.enterprise.inject.Produces;

import net.trajano.app.Bean;
import net.trajano.app.Qualified;

/**
 * CDI object factory.
 */
public class ObjectFactory {
    /**
     * Creates a CDI bean.
     * 
     * @return
     */
    @Produces
    @Qualified
    public Bean createBean() {
        final Bean bean = new Bean();
        bean.setDate(new Date());
        bean.setMessage("Hello CDI");
        return bean;
    }
}
