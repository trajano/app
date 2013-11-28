package net.trajano.app;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("app")
public class AppResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Bean hello() {
        final Bean bean = new Bean();
        bean.setDate(new Date());
        bean.setMessage("Hello JAX-RS");
        return bean;
    }
}
