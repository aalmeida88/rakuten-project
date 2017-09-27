package com.rakutten.utils.context;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rakutten.utils.api.ApiError;
import com.rakutten.utils.api.exception.ApiException;

public class ExceptionControllerAdviceConfigurer extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionControllerAdviceConfigurer.class);
		
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handlerUnknownException(HttpServletRequest request, Exception ex) {
		String message = ex.getMessage() != null ? ex.getMessage() : ex.toString();
		return this.handle(request, ex, HttpStatus.INTERNAL_SERVER_ERROR, message);
	}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiError> handleApiException(HttpServletRequest request, ApiException ex) {

		String message = ex.getMessage() != null ? ex.getMessage() : ex.toString();

		ApiError body = new ApiError();
		body.setCode(ex.getCode());
		body.setMessage(message);
		body.setCauses(ex.getCauses());

		this.injectStacktraceIfDebugging(request.getParameter("d"), ex, body);

		if (HttpStatus.valueOf(body.getCode()).is4xxClientError()) {
			if (ex.getCause() == null) {
				LOGGER.warn(message);
			} else {
				LOGGER.warn(message, ex);
			}
		} else {
			LOGGER.error(message, ex);
		}

		MultiValueMap<String, String> headers = null;
		if (ex.getHttpResponseHeaders() != null) {
			headers = new HttpHeaders();
			headers.putAll(ex.getHttpResponseHeaders());
		}
		return new ResponseEntity<>(body, headers, HttpStatus.valueOf(ex.getHttpStatusCode()));
	}

	protected void injectStacktraceIfDebugging(String debugParameterValue, Exception ex, ApiError body) {
		if (debugParameterValue != null && "1".equals(debugParameterValue)) {
			StringWriter w = new StringWriter();
			PrintWriter writer = new PrintWriter(w);
			ex.printStackTrace(writer);
			String stacktrace = w.toString();

			body.setStacktrace(stacktrace);
		}
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute("javax.servlet.error.exception", ex, RequestAttributes.SCOPE_REQUEST);
		}

		String message = ex.getMessage() != null ? ex.getMessage() : ex.toString();
		if (status.is4xxClientError()) {
			LOGGER.warn(message, ex);
		} else {
			LOGGER.error(message, ex);
		}

		ApiError newBody = new ApiError(status.value(), message, new ArrayList<String>(1));
		this.injectStacktraceIfDebugging(request.getParameter("d"), ex, newBody);
		return new ResponseEntity<>(newBody, headers, status);
	}

	protected ResponseEntity<ApiError> handle(HttpServletRequest request, Exception ex, HttpStatus status,
			String message) {
		ApiError body = new ApiError();
		body.setCode(status.value());
		body.setMessage(message);
		List<String> causes = new ArrayList<String>(1);
		causes.add(ex.getMessage());
		body.setCauses(causes);

		this.injectStacktraceIfDebugging(request.getParameter("d"), ex, body);

		if (status.is5xxServerError()) {
			LOGGER.error(ex.getMessage(), ex);
		} else {
			LOGGER.warn(ex.getMessage(), ex);
		}

		return new ResponseEntity<ApiError>(body, status);
	}
	
	protected ResponseEntity<ApiError> handleSpecificStatus(HttpServletRequest request, Exception ex, HttpStatus status){
		ApiError body = new ApiError();
		body.setCode(status.value());
		body.setMessage(ex.getMessage());
		return new ResponseEntity<>(body, status);
	}
	
	
}
