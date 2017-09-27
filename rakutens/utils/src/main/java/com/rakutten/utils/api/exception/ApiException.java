package com.rakutten.utils.api.exception;

import java.util.List;
import java.util.Map;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int code;
	private List<String> causes;
	private Map<String, List<String>> httpResponseHeaders;

	public ApiException(int code) {
		this(code, DefaultErrorMessages.get(code));
	}

	public ApiException(int code, String message) {
		super(message);
		this.code = code;
	}

	public ApiException(int code, String message, Throwable source) {
		super(message, source);
		this.code = code;
	}

	public ApiException(int code, String message, List<String> causes) {
		super(message);
		this.code = code;
		this.causes = causes;
	}

	public int getCode() {
		return this.code;
	}

	public int getHttpStatusCode() {
		if (this.code < 1000) {
			return this.code;
		} else {
			return Integer.parseInt(String.valueOf(this.code).substring(0, 3));
		}
	}

	public List<String> getCauses() {
		return this.causes;
	}

	public Map<String, List<String>> getHttpResponseHeaders() {
		return this.httpResponseHeaders;
	}

	public void setHttpResponseHeaders(Map<String, List<String>> httpResponseHeaders) {
		this.httpResponseHeaders = httpResponseHeaders;
	}
}