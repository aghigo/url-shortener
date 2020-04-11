package br.com.uol.pagseguro.urlshortener.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrl;
import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrlStatistics;

/**
 * Repository for short URL statistics data persistence
 */
public interface ShortUrlStatisticsRepository extends CrudRepository<ShortUrlStatistics, Long> {
	/**
	 * Find statistics of the given short URL
	 * 
	 * @param shortUrl short URL
	 * 
	 * @return statistics of the given short URL
	 */
	Optional<ShortUrlStatistics> findByShortUrl(ShortUrl shortUrl);
}