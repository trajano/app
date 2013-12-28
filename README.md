Java EE 7 Application
=====================

This is a Java EE 7 application that demonstrates as many of the Java EE 7
technologies as possible while promoting best practices.  It avoids using
any propriertary libraries for the server component (client side may use
the latest UI technologies such as Angular and Twitter bootstrap).

It takes advantage of Selenium to do unit testing and Sonar to do code
quality analysis.

It provides a REST API and web service client and server implementation along
with a web app.

It avoids implementing authentication that is delegated to the container.
Not it is up to the container to implement the SSO if needed.

The application attempts to remove scalability barriers like HTTP Sessions.
There should be no jsessionid cookie.
