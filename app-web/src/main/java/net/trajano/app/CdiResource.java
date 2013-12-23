package net.trajano.app;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * JAX-RS App Resource.
 */
@Path("cdi")
@ApplicationScoped
public class CdiResource {

    /**
     * Injected bean.
     */
    @Inject
    @Qualified
    private Bean injectedBean;

    /**
     * Returns an injected CDI bean.
     * 
     * @return bean
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Bean helloCdiBean() {
        return injectedBean;
    }
}
