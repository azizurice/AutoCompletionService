package com.ag.autocom.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ag.autocom.errors.ApiError;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	// 400
	
	@ExceptionHandler(value = { ApiRequestException.class })
	public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;

		ApiError apiError = new ApiError(badRequest, e.getMessage(), e);
		return new ResponseEntity<>(apiError, badRequest);
	}

	@ExceptionHandler(value = { NullPointerException.class })
	public ResponseEntity<Object> handleApiRequestException(NullPointerException e) {
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;

		String message = "Query parameter 'latitude' and 'longitude' must not be null.";
		ApiError apiError = new ApiError(badRequest, message, e);
		return new ResponseEntity<>(apiError, badRequest);
	}

	@ExceptionHandler(value = { NumberFormatException.class })
	public ResponseEntity<Object> handleApiRequestException(NumberFormatException e) {
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		String message = "Query parameter 'latitude' and 'longitude' must be parsable double number.";
		ApiError apiError = new ApiError(badRequest, message, e);
		return new ResponseEntity<>(apiError, badRequest);
	}

	// 500

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
		logger.info(ex.getClass().getName());
		logger.error("error", ex);
		
		String message ="Error occurred";
		final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
				"error occurred",ex);
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}
}
