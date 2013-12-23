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
}
