package dev.andreghigo.urlshortener.service;

import dev.andreghigo.urlshortener.exception.ShortUrlException;
import dev.andreghigo.urlshortener.model.entity.ShortUrlDomain;

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
