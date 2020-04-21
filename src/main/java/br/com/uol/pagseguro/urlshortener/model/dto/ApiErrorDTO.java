package br.com.uol.pagseguro.urlshortener.model.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public final class ApiErrorDTO implements Serializable {
	private static final long serialVersionUID = 3815698689078918046L;

	private final int status;
	
	private final String message;
	private final String error;
	private final Date timestamp;
}
