package com.rakutten.utils;

import java.util.EnumSet;
import java.util.TimeZone;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.rakutten.utils.context.JettyServerFactory;
import com.rakutten.utils.context.springfox.SpringfoxUrlFilter;

public abstract class WebServerContract {
	
	private static final String GMT = "GMT";
    private static final String DEFAULT_CONTEXT_PATH = "/";
    private static final Integer DEFAULT_PORT = 8080;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(WebServerContract.class);

    protected final Server server;

    public WebServerContract(String[] args) {
    	this.server = this.createNewServer(args);
    }

    private Server createNewServer(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
        String contextPath = args.length > 1 ? args[1] : DEFAULT_CONTEXT_PATH;

        TimeZone.setDefault(TimeZone.getTimeZone(GMT));

        GzipHandler handler = this.buildWebAppContext(args, contextPath);

        return JettyServerFactory.buildServer(handler, port);
    }

    protected void run() throws Exception {
        this.server.start();
        LOGGER.info("Server Started!");
        this.server.join();
    }

    protected GzipHandler buildWebAppContext(String[] args, String contextPath) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(contextClazz());

        ServletContextHandler handler = this.buildContextHandler(contextPath);

        this.appendSpringDispatcherServlet(applicationContext, handler);
        this.addListeners(applicationContext, handler);
        this.appendFilters(handler);

        GzipHandler gzipHandler = new GzipHandler();
        gzipHandler.setHandler(handler);

        applicationContext.close();
        return gzipHandler;
    }

    protected ServletContextHandler buildContextHandler(String contextPath) {
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        handler.setContextPath(contextPath);
        return handler;
    }

    protected void addListeners(AnnotationConfigWebApplicationContext applicationContext, ServletContextHandler handler) {
        handler.addEventListener(new ContextLoaderListener(applicationContext) {
            @Override
            public void contextInitialized(javax.servlet.ServletContextEvent event) {
                super.contextInitialized(event);
            }
        });
    }

    protected void appendSpringDispatcherServlet(AnnotationConfigWebApplicationContext applicationContext, ServletContextHandler handler) {
        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
        dispatcherServlet.setDispatchOptionsRequest(true);
        ServletHolder servletHolder = new ServletHolder(dispatcherServlet);
        servletHolder.setName("spring");
        servletHolder.setInitOrder(1);
        handler.addServlet(servletHolder, "/*");
    }

    protected void appendFilters(ServletContextHandler handler) {
        FilterHolder filterHolder = new FilterHolder(new SpringfoxUrlFilter(appContextPath()));
        handler.addFilter(filterHolder, "/*", EnumSet.of(DispatcherType.REQUEST));

        FilterHolder characterEncodingFilterHolder = new FilterHolder(new CharacterEncodingFilter());
        handler.addFilter(characterEncodingFilterHolder, "/*", EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR));
    }
    
    //abstract methods
    protected abstract String appName();
    protected abstract Class<?> contextClazz();
    protected abstract String appContextPath();

}
