package net.trajano.app;

import static java.lang.String.format;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.nio.file.Path;
import java.security.DigestOutputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import com.google.common.io.CountingOutputStream;

/**
 * Tpl serving servlet. It takes the template HTML files and combines them into
 * a single JS file that is served by the servlet. It sends it in compressed
 * form as well. This would work in a clustered environment as long as the file
 * contents are the same across all the servers.
 * <p>
 * It is configured as follows
 *
 * <pre>
 *     &lt;servlet>
 *         &lt;servlet-name>TplProvider&lt;/servlet-name>
 *         &lt;servlet-class>net.trajano.app.TplServlet&lt;/servlet-class>
 *         &lt;init-param>
 *             &lt;param-name>admin-app&lt;/param-name>
 *             &lt;param-value>/modules/admin-app&lt;/param-value>
 *         &lt;/init-param>
 *         &lt;load-on-startup>1&lt;/load-on-startup>
 *     &lt;/servlet>
 *     &lt;servlet-mapping>
 *         &lt;servlet-name>TplProvider&lt;/servlet-name>
 *         &lt;url-pattern>/adminapp.tpl.js&lt;/url-pattern>
 *     &lt;/servlet-mapping>
 * </pre>
 *
 * @author Archimedes
 *
 */
@SuppressWarnings("serial")
public class TplServlet extends HttpServlet {

    /**
     * Content length for the tpl file if it were uncompressed.
     */
    private long contentLength;

    /**
     * Digest value.
     */
    private String digest;

    /**
     * Generated tpl file. The tpl file itself is compressed. It is created in
     * {@link #init()} and deleted in {@link #destroy()};
     */
    private File tplFile;

    /**
     * Creates the cache file.
     *
     * @param accumulator
     *            accumulating map
     * @throws IOException
     */
    private void createCacheFile(
            final Map<String, Map<String, String>> accumulator)
                    throws IOException, GeneralSecurityException {

        tplFile = File.createTempFile("tplServlet", ".tpl.js.gz");
        final CountingOutputStream countingOutputStream = new CountingOutputStream(
                new FileOutputStream(tplFile));
        final DigestOutputStream digestOutputStream = new DigestOutputStream(
                new GZIPOutputStream(countingOutputStream),
                MessageDigest.getInstance("MD5"));
        final PrintWriter writer = new PrintWriter(digestOutputStream);
        final String prologue = "(function() { 'use strict';";
        writer.print(prologue);

        for (final String moduleName : accumulator.keySet()) {
            final Map<String, String> cacheEntries = accumulator
                    .get(moduleName);
            if (cacheEntries.isEmpty()) {
                continue;
            }
            final String modulePrologue = format(
                    "angular.module('%s').run(['$templateCache', function ($templateCache) {",
                    moduleName);
            writer.print(modulePrologue);

            for (final String key : cacheEntries.keySet()) {
                final String moduleEntry = format(
                        "$templateCache.put('%s','%s');", key,
                        cacheEntries.get(key));
                writer.print(moduleEntry);
            }

            final String moduleEpilogue = "}]);";
            writer.print(moduleEpilogue);

        }
        final String epilogue = "}());";
        writer.print(epilogue);
        writer.close();
        contentLength = countingOutputStream.getCount();
        digest = DatatypeConverter.printHexBinary(digestOutputStream
                .getMessageDigest().digest());
    }

    /**
     * Remove the file if it exists.
     */
    @Override
    public void destroy() {
        if (tplFile != null) {
            tplFile.delete();
        }
    }

    /**
     * Streams the data to the response. {@inheritDoc}
     */
    @Override
    protected void doGet(final HttpServletRequest req,
            final HttpServletResponse resp) throws ServletException,
            IOException {
        if (digest.equals(req.getHeader("If-None-Match"))) {
            resp.sendError(HttpURLConnection.HTTP_NOT_MODIFIED);
            return;
        }
        resp.setContentType("text/javascript");
        resp.setHeader("Content-Encoding", "gzip");
        final long cacheAge = 60 * 60;
        final long expiry = System.currentTimeMillis() + cacheAge * 1000;
        resp.setDateHeader("Expires", expiry);
        resp.setHeader("ETag", digest);
        resp.setHeader("Cache-Control", "max-age=" + cacheAge);
        resp.setContentLength((int) contentLength);
        final ServletOutputStream os = resp.getOutputStream();
        final BufferedInputStream is = new BufferedInputStream(
                new FileInputStream(tplFile));
        int c = is.read();
        while (c != -1) {
            os.write(c);
            c = is.read();
        }
        is.close();
    }

    /**
     * Does nothing as GET would always work unless there's a problem with
     * initialization. {@inheritDoc}
     */
    @Override
    protected void doHead(final HttpServletRequest req,
            final HttpServletResponse resp) throws ServletException,
            IOException {
        // does nothing
    }

    /**
     * TreeMaps are used to ensure that the order will be kept the same across
     * different JVMs. {@inheritDoc}
     */
    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        final Map<String, Map<String, String>> accumulator = new TreeMap<>();
        final Enumeration<String> moduleNames = config.getInitParameterNames();
        try {
            while (moduleNames.hasMoreElements()) {
                final String moduleName = moduleNames.nextElement();
                final String[] paths = config.getInitParameter(moduleName)
                        .split(",");
                final Map<String, String> cacheEntries = new TreeMap<>();
                final Path contextRootPath = new File(getServletContext()
                        .getRealPath("/")).toPath();
                for (final String path : paths) {
                    final File realPathFile = new File(getServletContext()
                            .getRealPath(path));
                    if (realPathFile.isDirectory()) {
                        for (final File tplHtmlFile : realPathFile.listFiles()) {
                            if (tplHtmlFile.isFile()) {
                                registerTemplate(contextRootPath, tplHtmlFile,
                                        cacheEntries);
                            }
                        }
                    } else if (realPathFile.isFile()) {
                        registerTemplate(contextRootPath, realPathFile,
                                cacheEntries);
                    }
                }
                accumulator.put(moduleName, cacheEntries);
            }
            createCacheFile(accumulator);
        } catch (final IOException | GeneralSecurityException e) {
            throw new ServletException(e);
        }
    }

    /**
     * Register a template to a map.
     *
     * @param contextRootPath
     *            context root path
     * @param tplHtmlFile
     *            tpl file
     * @param cacheEntries
     *            map to populate
     * @throws IOException
     */
    private void registerTemplate(final Path contextRootPath,
            final File tplHtmlFile, final Map<String, String> cacheEntries)
            throws IOException {
        final String tplPathKey = contextRootPath
                .relativize(tplHtmlFile.toPath()).toString().replace('\\', '/');
        final BufferedReader reader = new BufferedReader(new FileReader(
                tplHtmlFile));
        final StringBuilder b = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            b.append(line.replace("'", "\\'"));
            b.append("\\n");
            line = reader.readLine();
        }
        reader.close();
        cacheEntries.put(tplPathKey, b.toString());
    }
}
