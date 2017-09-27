package com.rakutten.utils.context.springfox;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.server.Request;

import springfox.documentation.swagger2.web.Swagger2Controller;

public class SpringfoxUrlFilter implements Filter {

	private static final String SPRINGFOX_UI_SEARCH_TOKEN = "swagger-ui";
	private static final String SPRINGFOX_RESOURCES_SEARCH_TOKEN = "swagger-resources";
	private final String path;

	public SpringfoxUrlFilter(String path) {
		this.path = path;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Request jettyRequest = (Request) request;
		HttpURI httpURI = jettyRequest.getHttpURI();
		String uriPath = httpURI.getPath();
		if (uriPath.contains(SPRINGFOX_UI_SEARCH_TOKEN) || uriPath.contains(SPRINGFOX_RESOURCES_SEARCH_TOKEN)
				|| uriPath.endsWith(Swagger2Controller.DEFAULT_URL)) {
			httpURI.setPath(uriPath.replaceFirst(this.path, ""));
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	public void destroy() {
	}

}