package net.trajano.app;

import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * @author Archimedes
 */
@BusinessMethod
@Interceptor
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
