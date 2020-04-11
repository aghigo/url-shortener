package br.com.uol.pagseguro.urlshortener.model.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for general API errors
 */
@Data
@Builder
public final class ApiErrorDTO implements Serializable {
	private static final long serialVersionUID = -64214781441384287L;
	
	private final Integer status;
	private final Long timestamp;
	private final String message;
	private final String error;
}
