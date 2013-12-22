package net.trajano.app;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * JAX-RS App Resource.
 */
@Path("bean")
public class BeanResource {

    // /**
    // * Injected bean.
    // */
    // @Inject
    // @Qualified
    // private Bean injectedBean;

    // /**
    // * Persisted beans EJB.
    // */
    // @EJB
    // private PersistedBeans persistedBeans;
    //
    /**
     * Creates a new bean and returns it.
     * 
     * @return bean
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Bean helloBean() {
        final Bean bean = new Bean();
        bean.setDate(new Date());
        bean.setMessage("Hello JAX-RS");
        return bean;
    }

    // /**
    // * Returns an injected CDI bean.
    // *
    // * @return bean
    // */
    // @GET
    // @Path("cdi")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Bean helloCdiBean() {
    // return injectedBean;
    // }
    //
    // /**
    // * Persists a new JPA bean, reads and then returns it.
    // *
    // * @return bean
    // */
    // @GET
    // @Path("jpa")
    // @RequestScoped
    // @Produces(MediaType.APPLICATION_JSON)
    // public PersistedBean helloJpaBean() {
    // final PersistedBean bean = new PersistedBean();
    // bean.setSomeDate(new Date());
    // bean.setSomeTime(new Date());
    // bean.setSomeTimestamp(new Date());
    // bean.setMessage("Hello JPA" + new Date());
    // persistedBeans.save(bean);
    //
    // return persistedBeans.getLatest();
    // }
    //
    // /**
    // * Persists a new JPA bean, reads and then returns it.
    // *
    // * @return bean
    // */
    // @GET
    // @Path("jpa/{id}")
    // @RequestScoped
    // @Produces(MediaType.APPLICATION_JSON)
    // public PersistedBean helloJpaBean(@PathParam("id") final long id) {
    // return persistedBeans.get(id);
    // }

}
