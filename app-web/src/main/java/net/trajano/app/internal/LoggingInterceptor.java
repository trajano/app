package net.trajano.app.internal;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import net.trajano.app.BusinessMethod;

/**
 * @author Archimedes
 */
@BusinessMethod
@Interceptor
@ApplicationScoped
public class LoggingInterceptor {
    /**
     * Logger.
     */
    private static final Logger log = Logger.getLogger("net.trajano.app");

    /**
     * Logs.
     * 
     * @param ctx
     *            context
     * @return return value
     * @throws Throwable
     */
    @AroundInvoke
    public Object log(final InvocationContext ctx) throws Throwable {
        log.warning("around invoke " + ctx.getMethod());
        try {
            return ctx.proceed();
        } catch (final Throwable e) {
            log.severe("Error calling ctx.proceed in " + ctx.getMethod());
            throw e;
        }
    }
}
