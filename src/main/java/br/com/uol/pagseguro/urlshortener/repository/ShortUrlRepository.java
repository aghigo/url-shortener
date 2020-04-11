package br.com.uol.pagseguro.urlshortener.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrl;

/**
 * Repository for short URL persistence
 */
public interface ShortUrlRepository extends CrudRepository<ShortUrl, String> {
	/**
	 * Finds a short URL by unique alias
	 * 
	 * @param alias short URL alias
	 * 
	 * @return short URL with the alias. {@link Optional#empty} if not found
	 */
	Optional<ShortUrl> findByAlias(String alias);

	/**
	 * Finds a short URL from its corresponding long URL
	 * 
	 * @param longUrl original long URL
	 * 
	 * @return Short URL from the original long URL. {@link Optional#empty()} if not found.
	 */
	Optional<ShortUrl> findByLongUrl(String longUrl);
}