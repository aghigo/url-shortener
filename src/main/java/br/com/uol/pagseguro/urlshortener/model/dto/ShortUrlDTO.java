package br.com.uol.pagseguro.urlshortener.model.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrl;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public final class ShortUrlDTO implements Serializable {
	private static final long serialVersionUID = -1349703406800640400L;

	private final String alias;
	private final String shortUrl;
	private final String longUrl;
	private final String creationDate;
	
	public static ShortUrlDTO of(ShortUrl shortUrl) {
		return ShortUrlDTO.builder()
				.alias(shortUrl.getAlias())
				.shortUrl(shortUrl.getShortUrl())
				.longUrl(shortUrl.getLongUrl())
				.creationDate(shortUrl.getCreationDate().toString())
				.build();
	}
}
