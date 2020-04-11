package br.com.uol.pagseguro.urlshortener.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.uol.pagseguro.urlshortener.exception.ShortUrlException;
import br.com.uol.pagseguro.urlshortener.model.dto.ApiErrorDTO;

/**
 * Handles error responses for the API 
 */
@ControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(RestControllerExceptionHandler.class);

	private static final String RESPONSE_BODY = "Response Body: {}";
	private static final String RESPONSE_STATUS = "Response Status: {} {}";
	
	@ExceptionHandler(value = { ShortUrlException.class })
	public ResponseEntity<ApiErrorDTO> handleShortUrlException(ShortUrlException ex) {
		LOGGER.error(ex.getMessage(), ex);
		
		ApiErrorDTO body = ApiErrorDTO.builder()
				.error(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.message(ex.getMessage())
				.status(HttpStatus.BAD_REQUEST.value())
				.timestamp(new Date().getTime())
				.build();
		
		LOGGER.error(RESPONSE_STATUS, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
		LOGGER.error(RESPONSE_BODY, body);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(body);
	}
	
	@ExceptionHandler(value = { ResponseStatusException.class })
	public ResponseEntity<ApiErrorDTO> handleResponseStatusException(ResponseStatusException ex) {
		LOGGER.error(ex.getMessage(), ex);
		
		ApiErrorDTO body = ApiErrorDTO.builder()
				.error(ex.getStatus().getReasonPhrase())
				.message(ex.getReason())
				.status(ex.getStatus().value())
				.timestamp(new Date().getTime())
				.build();
		
		LOGGER.error(RESPONSE_STATUS, ex.getStatus().value(), ex.getStatus().getReasonPhrase());
		LOGGER.error(RESPONSE_BODY, body);
		
		return ResponseEntity.status(ex.getStatus()).contentType(MediaType.APPLICATION_JSON).body(body);
	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<ApiErrorDTO> handleGenericException(Exception ex) {
		LOGGER.error(ex.getMessage(), ex);
		
		ApiErrorDTO body = ApiErrorDTO.builder()
				.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
				.message(ex.getMessage())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.timestamp(new Date().getTime())
				.build();
		
		LOGGER.error(RESPONSE_STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		LOGGER.error(RESPONSE_BODY, body);
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(body);
	}
}

