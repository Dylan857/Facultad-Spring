package com.facultad.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
	
}