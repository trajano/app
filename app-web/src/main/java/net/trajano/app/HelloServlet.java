package net.trajano.app;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

import net.webservicex.AccelerationUnit;
import net.webservicex.AccelerationUnitSoap;
import net.webservicex.Accelerations;

/**
 * Hello world servlet.
 */
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 6782169459286299897L;

    /**
     * Web Service client reference.
     */
    @WebServiceRef(AccelerationUnit.class)
    private AccelerationUnitSoap accelerationUnit;

    @Override
    protected void doGet(final HttpServletRequest req,
            final HttpServletResponse resp) throws ServletException,
            IOException {
        resp.getWriter().print(
                "<div id='text'>Hello servlet on " + new Date() + "</div>");
        resp.getWriter().print(
                "<div id='accel'>1 G is "
                        + accelerationUnit.changeAccelerationUnit(1.00,
                                Accelerations.GRAV,
                                Accelerations.METER_PERSQUARESECOND)
                        + "m/s^2</div>");
    }
}
