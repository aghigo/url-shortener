package br.com.uol.pagseguro.urlshortener.model.dto;

import java.io.Serializable;

import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrl;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for short URLs
 * 
 * @see ShortUrl
 */
@Data
@Builder
public final class ShortUrlDTO implements Serializable {
	private static final long serialVersionUID = -1349703406800640400L;

	private final String alias;
	private final String longUrl;
	private final String shortUrl;
	private final String timestamp;
	
	public static ShortUrlDTO of(ShortUrl shortUrl) {
		return ShortUrlDTO.builder()
					.alias(shortUrl.getAlias())
					.shortUrl("http://localhost:8080/" + shortUrl.getAlias())
					.longUrl(shortUrl.getLongUrl())
					.timestamp(shortUrl.getCreationDate().toString())
					.build();
	}
}