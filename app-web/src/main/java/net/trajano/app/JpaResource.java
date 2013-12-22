package net.trajano.app;

import java.util.Date;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * JAX-RS App Resource.
 */
@Path("jpa")
public class JpaResource {

    /**
     * Persisted beans EJB.
     */
    @EJB
    private PersistedBeans persistedBeans;

    /**
     * Persists a new JPA bean, reads and then returns it.
     * 
     * @return bean
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PersistedBean helloJpaBean() {
        final PersistedBean bean = new PersistedBean();
        bean.setSomeDate(new Date());
        bean.setSomeTime(new Date());
        bean.setSomeTimestamp(new Date());
        bean.setMessage("Hello JPA");
        persistedBeans.save(bean);

        return persistedBeans.getLatest();
    }

    /**
     * Persists a new JPA bean, reads and then returns it.
     * 
     * @param id
     *            primary key.
     * @return bean
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PersistedBean helloJpaBean(@PathParam("id") final long id) {
        return persistedBeans.get(id);
    }

}
