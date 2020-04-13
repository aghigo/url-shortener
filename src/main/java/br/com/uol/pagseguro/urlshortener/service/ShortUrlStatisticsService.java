package br.com.uol.pagseguro.urlshortener.service;

import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrlStatistics;

/**
 * Service with operations related to short URL's statistics
 */
public interface ShortUrlStatisticsService {
	/**
	 * Increments total access data from a short URL's statistics
	 * 
	 * @param statistics short URL's statistics
	 */
	void incrementTotalAccess(ShortUrlStatistics statistics);
	
	/**
	 * Creates new initialized statistics data for a new short URL
	 * 
	 * @return initialized statistics for a new short URL
	 */
	ShortUrlStatistics createNewStatistics();
}
