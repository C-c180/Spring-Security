package com.dfsx.standby.webapi;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.WebApplicationInitializer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WebServer {
    private ApplicationContext _parentApplicationContext;
    private Server _server;

    public WebServer(ApplicationContext parentContext) {
        _parentApplicationContext = parentContext;
    }

    public void start() throws Exception {
        Properties properties = new Properties();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
        properties.load(inputStream);
        String host = properties.getProperty("webapi.host");
        int port = Integer.parseInt(properties.getProperty("webapi.port"));

        inputStream.close();

        _server = new Server();
        ServerConnector httpConnector = new ServerConnector(_server);
        if (!StringUtils.isEmpty(host)) {
            httpConnector.setHost(host);
        }
        httpConnector.setPort(port);
        _server.addConnector(httpConnector);

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setServer(_server);
        webAppContext.setContextPath("/");
        webAppContext.setDisplayName(WebServer.class.getPackage().getName());
        webAppContext.setBaseResource(Resource.newResource(createTempDir(WebServer.class.getPackage().getName() + "-" + port)));
        webAppContext.setAttribute(AnnotationConfiguration.CLASS_INHERITANCE_MAP, createClassMap());
        webAppContext.setConfigurations(new Configuration[]{new AnnotationConfiguration()});
        webAppContext.getServletContext().setAttribute("parentApplicationContext", _parentApplicationContext);

        HandlerCollection handlers = new HandlerCollection();
        handlers.setHandlers(new Handler[]{webAppContext});
        _server.setHandler(handlers);

        _server.start();

        System.out.println("web server is running at port: " + port);
    }

    public void stop() {
        try {
            _server.stop();
            _server.join();
        } catch (Exception ex) {

        }
        _server = null;
        System.out.println("web server is stopped");
    }

    protected File createTempDir(String prefix) throws IOException {
        File tempDir = File.createTempFile(prefix + ".", ".tmp");
        tempDir.delete();
        tempDir.mkdir();
        tempDir.deleteOnExit();
        return tempDir;
    }

    protected void addDefaultServlet(WebAppContext context) {
        ServletHolder holder = new ServletHolder();
        holder.setName("default");
        holder.setClassName("org.eclipse.jetty.servlet.DefaultServlet");
        holder.setInitParameter("dirAllowed", "false");
        holder.setInitOrder(1);
        context.getServletHandler().addServletWithMapping(holder, "/");
        context.getServletHandler().getServletMapping("/").setDefault(true);
    }

    protected static AnnotationConfiguration.ClassInheritanceMap createClassMap() {
        AnnotationConfiguration.ClassInheritanceMap classMap = new AnnotationConfiguration.ClassInheritanceMap();
        ConcurrentHashSet<String> set = new ConcurrentHashSet<String>();
        set.add(WebInitializer.class.getName());
        set.add(SecurityWebInitializer.class.getName());
        classMap.put(WebApplicationInitializer.class.getName(), set);
        return classMap;
    }
}