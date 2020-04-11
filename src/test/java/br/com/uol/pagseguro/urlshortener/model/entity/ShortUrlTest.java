package br.com.uol.pagseguro.urlshortener.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;

/**
 * Unit test of {@link ShortUrl}
 * 
 * @see ShortUrl
 *
 */
public class ShortUrlTest {
	@Test
	public void testNoArgsConstructor() {
		ShortUrl shortUrl = new ShortUrl();
		
		String alias = "123";
		shortUrl.setAlias(alias);
		
		Date creationDate = new Date();
		shortUrl.setCreationDate(creationDate);
		
		String longUrl = "http://www.example.com";
		shortUrl.setLongUrl(longUrl);
		
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.id(1L)
				.totalAccess(1L)
				.build();
		
		shortUrl.setStatistics(statistics);
		
		assertEquals(alias, shortUrl.getAlias());
		assertEquals(creationDate, shortUrl.getCreationDate());
		assertEquals(longUrl, shortUrl.getLongUrl());
		assertEquals(statistics, shortUrl.getStatistics());
	}
	
	@Test
	public void testAllArgsConstructor() {
		String alias = "123";
		Date creationDate = new Date();
		String longUrl = "http://www.example.com";
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.id(1L)
				.totalAccess(1L)
				.build();
		
		ShortUrl shortUrl = new ShortUrl(alias, longUrl, creationDate, statistics);
		
		assertEquals(alias, shortUrl.getAlias());
		assertEquals(creationDate, shortUrl.getCreationDate());
		assertEquals(longUrl, shortUrl.getLongUrl());
		assertEquals(statistics, shortUrl.getStatistics());
	}
	
	@Test
	public void testBuilder() {
		String alias = "123";
		Date creationDate = new Date();
		String longUrl = "http://www.example.com";
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.id(1L)
				.totalAccess(1L)
				.build();
		
		ShortUrl shortUrl = ShortUrl.builder()
				.alias(alias)
				.creationDate(creationDate)
				.longUrl(longUrl)
				.statistics(statistics)
				.build();
		
		assertEquals(alias, shortUrl.getAlias());
		assertEquals(creationDate, shortUrl.getCreationDate());
		assertEquals(longUrl, shortUrl.getLongUrl());
		assertEquals(statistics, shortUrl.getStatistics());
	}
	
	@Test
	public void testEquals() {
		String alias = "123";
		Date creationDate = new Date();
		String longUrl = "http://www.example.com";
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.id(1L)
				.totalAccess(1L)
				.build();
		
		ShortUrl shortUrl = ShortUrl.builder()
				.alias(alias)
				.creationDate(creationDate)
				.longUrl(longUrl)
				.statistics(statistics)
				.build();
		
		assertTrue(shortUrl.equals(shortUrl));

		assertFalse(shortUrl.equals(null));
	}
	
	@Test
	public void testHashCode() {
		String alias = "123";
		String longUrl = "http://www.example.com";
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.id(1L)
				.totalAccess(1L)
				.build();
		
		ShortUrl shortUrl = ShortUrl.builder()
				.alias(alias)
				.longUrl(longUrl)
				.statistics(statistics)
				.build();
		
		int expected = -65860250;
		
		assertEquals(expected, shortUrl.hashCode());
	}
	
	@Test
	public void testToString() {
		String alias = "123";
		Date creationDate = new Date();
		String longUrl = "http://www.example.com";
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.id(1L)
				.totalAccess(1L)
				.build();
		
		ShortUrl shortUrl = ShortUrl.builder()
				.alias(alias)
				.creationDate(creationDate)
				.longUrl(longUrl)
				.statistics(statistics)
				.build();
		
		String expected = "ShortUrl(alias=" + alias + ", longUrl=" + longUrl + ", creationDate=" + creationDate + ", statistics=" + statistics + ")";
		
		assertEquals(expected, shortUrl.toString());
	}
}
