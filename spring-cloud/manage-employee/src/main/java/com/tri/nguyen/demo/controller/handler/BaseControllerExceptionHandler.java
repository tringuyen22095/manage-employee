package com.tri.nguyen.demo.controller.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tri.nguyen.demo.controller.exception.CustomException;
import com.tri.nguyen.demo.models.contants.ErrorMessages;
import com.tri.nguyen.demo.models.res.ErrorRes;

@RestControllerAdvice
public class BaseControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<FieldError> lstFieldError = exception.getBindingResult().getFieldErrors();
		String fields = lstFieldError.stream()
				.map(FieldError::getField)
				.distinct()
				.collect(Collectors.joining(", ", " ", "."));
		return ResponseEntity
				.badRequest()
				.body(new ErrorRes(ErrorMessages.INVALID_INFORMATION + fields));
	}

	@ExceptionHandler(value = { CustomException.class })
	public ResponseEntity<ErrorRes> handleCustomException(CustomException exception) {
		return ResponseEntity
				.badRequest()
				.body(new ErrorRes(exception.getMessage()));
	}

}
