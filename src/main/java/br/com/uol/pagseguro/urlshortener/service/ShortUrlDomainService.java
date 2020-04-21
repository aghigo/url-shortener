package br.com.uol.pagseguro.urlshortener.service;

import br.com.uol.pagseguro.urlshortener.exception.ShortUrlException;
import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrlDomain;

public interface ShortUrlDomainService {
	/**
	 * Gets the application default domain
	 * 
	 * @return application default domain
	 * 
	 * @throws ShortUrlException if domain not found
	 */
	ShortUrlDomain getDefaultDomain() throws ShortUrlException;
}
