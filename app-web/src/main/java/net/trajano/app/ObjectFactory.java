package net.trajano.app;

import java.util.Date;

import javax.enterprise.inject.Produces;

/**
 * CDI object factory.
 */
public class ObjectFactory {
    /**
     * Creates a CDI bean.
     * 
     * @return produces a bean.
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
