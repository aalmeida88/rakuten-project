package com.rakuten.categories.context;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rakuten.categories.api.exception.AlreadyExistsException;
import com.rakuten.categories.api.exception.CategoryWithChildrenException;
import com.rakuten.categories.api.exception.NotExistsException;
import com.rakutten.utils.api.ApiError;
import com.rakutten.utils.context.ExceptionControllerAdviceConfigurer;

@ControllerAdvice
public class ExceptionControllerAdvice extends ExceptionControllerAdviceConfigurer {
	
	@ExceptionHandler(AlreadyExistsException.class)
	public ResponseEntity<ApiError> handleAlreadyExistsException(HttpServletRequest request, Exception ex) {
		return handleSpecificStatus(request, ex, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(NotExistsException.class)
	public ResponseEntity<ApiError> handleNotExistsException(HttpServletRequest request, Exception ex) {
		return handleSpecificStatus(request, ex, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CategoryWithChildrenException.class)
	public ResponseEntity<ApiError> handleWithChildrenException(HttpServletRequest request, Exception ex) {
		return handleSpecificStatus(request, ex, HttpStatus.CONFLICT);
	}

}
