package com.facultad.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;


@RestControllerAdvice
public class ControllerException {

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, Object> handleValidateExceptions(MethodArgumentNotValidException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put("statusCode", HttpStatus.BAD_REQUEST.value());
		errors.put("message", "There should be no empty fields");
		return errors;
	}
	
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(SecurityException.class)
	public Map<String, Object> handleValidateExceptionJwt(SecurityException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put("statusCode", HttpStatus.UNAUTHORIZED.value());
		errors.put("message", "UNAUTHORIZED");
		return errors;
	}
	
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(ExpiredJwtException.class)
	public Map<String, Object> handleValidateExceptionJwt(ExpiredJwtException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put("statusCode", HttpStatus.UNAUTHORIZED.value());
		errors.put("message", "Token has expired");
		return errors;
	}
	
}