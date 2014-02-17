Java EE 7 Application
=====================

This is a Java EE 7 application that demonstrates as many of the Java EE 7
technologies as possible while promoting best practices.  It avoids using
any proprietary libraries for the server component (client side may use
the latest UI technologies such as Angular and Twitter bootstrap).

It takes advantage of Selenium to do functional testing and Sonar to do code
quality analysis.  Cargo is used to run the server for functional testing.

It provides a REST API and web service client and server implementation along
with a web app.

It avoids implementing authentication that is delegated to the container.
Not it is up to the container to implement the SSO if needed.

The application attempts to remove scalability barriers like HTTP Sessions.
There should be no use of the `jsessionid` cookie.  Instead it uses websockets
to manage the sessions.

JSF will *not* be used, the UI technology is delegated to plain HTML with
REST or web socket communication.

This tries to lump everything into one WAR file rather than breaking things
apart into separate modules.  For most projects, there is no real reason to
separate and keeping them lumped together makes things easier to refactor.

The only thing I can think of for separating is the web service implementation
which may need to be a different module in order for it to run on a different
virtual host for service level agreement targets.  Otherwise, it isn't really
too beneficial.

There is no use of RMI or remote EJBs.  Those are very Java and container
specific, SOAP is used as the remoting protocol for external clients.

Although XSDs are used WSDL files are not.  The WSDL is generated from the
Java code as it discusses calling semantics which are pretty mechanical
unlike XSDs which talk about data semantics which are very business oriented
and would have more detailed validations than that of Java.
