package br.com.uol.pagseguro.urlshortener.service;

import java.util.Optional;

import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrl;
import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrlStatistics;

/**
 * Service with operations related to short URL's statistics
 */
public interface ShortUrlStatisticsService {
	/**
	 * Finds statistics data of a given short URL
	 * 
	 * @param shortUrl short URL
	 * 
	 * @return statistics of the short URL
	 */
	Optional<ShortUrlStatistics> getStatisticsByShortUrl(ShortUrl shortUrl);
	
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
