package br.com.uol.pagseguro.urlshortener.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ShortUrlStatistics}
 * 
 * @author ShortUrlStatistics
 *
 */
public class ShortUrlStatisticsTest {
	@Test
	public void testNoArgsConstructor() {
		ShortUrlStatistics shortUrlStatistics = new ShortUrlStatistics();
		
		String alias = "213";
		Date creationDate = new Date();
		String longUrl = "http://www.example.com";
		ShortUrl shortUrl = ShortUrl.builder()
				.alias(alias)
				.creationDate(creationDate)
				.longUrl(longUrl)
				.build();
		
		long id = 0L;
		shortUrlStatistics.setId(id);

		shortUrlStatistics.setShortUrl(shortUrl);
		
		long totalAccess = 0L;
		shortUrlStatistics.setTotalAccess(totalAccess);
		
		assertEquals(id, shortUrlStatistics.getId());
		assertEquals(shortUrl, shortUrlStatistics.getShortUrl());
		assertEquals(totalAccess, shortUrlStatistics.getTotalAccess());
	}
}
