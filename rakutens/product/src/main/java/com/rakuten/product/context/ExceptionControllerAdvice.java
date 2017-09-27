package com.rakuten.product.context;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rakuten.product.exception.AlreadyExistsException;
import com.rakuten.product.exception.MandatoryFieldException;
import com.rakuten.product.exception.NotExistsException;
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
	
	@ExceptionHandler(MandatoryFieldException.class)
	public ResponseEntity<ApiError> handleMandatoryFieldException(HttpServletRequest request, Exception ex) {
		return handleSpecificStatus(request, ex, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiError> handleIllegalArgumentException(HttpServletRequest request, Exception ex) {
		return handleSpecificStatus(request, ex, HttpStatus.BAD_REQUEST);
	}

}
