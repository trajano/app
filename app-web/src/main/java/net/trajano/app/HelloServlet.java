package net.trajano.app;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.trajano.app.domain.TemporalRecords;

/**
 * Hello world servlet.
 */
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 6782169459286299897L;

	@EJB
	private TemporalRecords temporalRecords;

	@Override
	protected void doGet(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {
		resp.getWriter().print(
				"<div id='text'>Hello servlet on " + new Date() + "</div>");
		resp.getWriter()
				.print("<div id='text'>Hello servlet on " + temporalRecords
						+ "</div>");
		temporalRecords.save("Hello world");

	}
}
