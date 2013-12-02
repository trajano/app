package net.trajano.app;

import java.util.Date;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("app")
public class AppResource {

    /**
     * Injected bean.
     */
    @Inject
    @Qualified
    private Bean injectedBean;

    @EJB
    private PersistedBeans persistedBeans;

    /**
     * Creates a new bean and returns it.
     * 
     * @return bean
     */
    @GET
    @Path("bean")
    @Produces(MediaType.APPLICATION_JSON)
    public Bean helloBean() {
        final Bean bean = new Bean();
        bean.setDate(new Date());
        bean.setMessage("Hello JAX-RS");
        return bean;
    }

    /**
     * Returns an injected CDI bean.
     * 
     * @return bean
     */
    @GET
    @Path("cdi")
    @Produces(MediaType.APPLICATION_JSON)
    public Bean helloCdiBean() {
        return injectedBean;
    }

    /**
     * Persists a new JPA bean, reads and then returns it.
     * 
     * @return bean
     */
    @GET
    @Path("jpa")
    @Produces(MediaType.APPLICATION_JSON)
    public PersistedBean helloJpaBean() {
        final PersistedBean bean = new PersistedBean();
        bean.setDate(new Date());
        bean.setMessage("Hello JPA");
        persistedBeans.save(bean);

        return persistedBeans.getLatest();
    }

}
