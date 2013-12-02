package net.trajano.app;

import java.util.Date;

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
     * Creates a new bean and returns it.
     * 
     * @return bean
     */
    @GET
    @Path("cdi")
    @Produces(MediaType.APPLICATION_JSON)
    public Bean helloCdiBean() {
        return injectedBean;
    }

}
