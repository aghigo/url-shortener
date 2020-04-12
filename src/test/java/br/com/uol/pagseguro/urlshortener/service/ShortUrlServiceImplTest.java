package br.com.uol.pagseguro.urlshortener.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.uol.pagseguro.urlshortener.exception.ShortUrlException;
import br.com.uol.pagseguro.urlshortener.generator.ShortUrlAliasGenerator;
import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrl;
import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrlStatistics;
import br.com.uol.pagseguro.urlshortener.repository.ShortUrlRepository;

/**
 * Unit tests for {@link ShortUrlServiceImpl}
 * 
 * @see ShortUrlService
 *
 */
public class ShortUrlServiceImplTest {
	@Mock
	private ShortUrlRepository shortUrlRepository;
	
	@Mock
	private ShortUrlStatisticsService shortUrlStatisticsService;
	
	@Mock
	private ShortUrlAliasGenerator shortUrlAliasGenerator;
	
	private ShortUrlService shortUrlService;
	
	@BeforeEach
	public void setup () {
		MockitoAnnotations.initMocks(this);
		this.shortUrlService = new ShortUrlServiceImpl(shortUrlRepository, shortUrlStatisticsService, shortUrlAliasGenerator);
	}
	
	@Test
	public void shortUrl_passing_invalid_url_should_throw_ShortUrlException () {
		String longUrl = "123123123123xxxx";
		
		try {
			shortUrlService.shortUrl(longUrl);
			fail("should throw ShortUrlException");
		} catch (ShortUrlException e) {
			assertEquals("Invalid URL", e.getMessage());
		}
	}
	
	@Test
	public void shortUrl_passing_valid_url_but_still_not_shorted_should_generate_new_alias_and_persist_and_return_shorted_url () throws ShortUrlException {
		String longUrl = "http://www.example.com";
		String alias = "123";
		Date creationDate = new Date();
		
		doReturn(Optional.empty()).when(shortUrlRepository).findByLongUrl(eq(longUrl));
		
		doReturn(alias).when(shortUrlAliasGenerator).generate(any(String.class));
		
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.id(1L)
				.totalAccess(0L)
				.build();
		doReturn(statistics).when(shortUrlStatisticsService).createNewStatistics();
		
		
		ShortUrl expectedShortUrl = ShortUrl.builder()
				.alias(alias)
				.creationDate(creationDate)
				.longUrl(longUrl)
				.statistics(statistics)
				.build();
		
		statistics.setShortUrl(expectedShortUrl);
		
		doReturn(expectedShortUrl).when(shortUrlRepository).save(any(ShortUrl.class));
		
		ShortUrl shortUrl = shortUrlService.shortUrl(longUrl);
		
		assertEquals(alias, shortUrl.getAlias());
		assertEquals(longUrl, shortUrl.getLongUrl());
		assertEquals(shortUrl.getCreationDate(), shortUrl.getCreationDate());
		assertEquals(statistics.getId(), shortUrl.getStatistics().getId());
		assertEquals(statistics.getShortUrl(), shortUrl.getStatistics().getShortUrl());
		assertEquals(statistics.getTotalAccess(), shortUrl.getStatistics().getTotalAccess());
		
		verify(shortUrlRepository, times(1)).findByLongUrl(eq(longUrl));
		verify(shortUrlAliasGenerator, times(1)).generate(any(String.class));
		verify(shortUrlStatisticsService, times(1)).createNewStatistics();
		verify(shortUrlRepository, times(1)).save(any(ShortUrl.class));
	}
	
	@Test
	public void shortUrl_passing_valid_url_already_shorted_should_return_shorted_url_directly_without_generation_new_alias_and_without_persisting () throws ShortUrlException {
		String longUrl = "http://www.example.com";
		String alias = "123";
		Date creationDate = new Date();
		
		doReturn(alias).when(shortUrlAliasGenerator).generate(any(String.class));
		
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.id(1L)
				.totalAccess(0L)
				.build();
		doReturn(statistics).when(shortUrlStatisticsService).createNewStatistics();
		
		
		ShortUrl expectedShortUrl = ShortUrl.builder()
				.alias(alias)
				.creationDate(creationDate)
				.longUrl(longUrl)
				.statistics(statistics)
				.build();
		
		statistics.setShortUrl(expectedShortUrl);
		
		doReturn(Optional.ofNullable(expectedShortUrl)).when(shortUrlRepository).findByLongUrl(eq(longUrl));
		
		doReturn(expectedShortUrl).when(shortUrlRepository).save(any(ShortUrl.class));
		
		ShortUrl shortUrl = shortUrlService.shortUrl(longUrl);
		
		assertEquals(alias, shortUrl.getAlias());
		assertEquals(longUrl, shortUrl.getLongUrl());
		assertEquals(shortUrl.getCreationDate(), shortUrl.getCreationDate());
		assertEquals(statistics.getId(), shortUrl.getStatistics().getId());
		assertEquals(statistics.getShortUrl(), shortUrl.getStatistics().getShortUrl());
		assertEquals(statistics.getTotalAccess(), shortUrl.getStatistics().getTotalAccess());
		
		verify(shortUrlRepository, times(1)).findByLongUrl(eq(longUrl));
		verifyNoInteractions(shortUrlAliasGenerator);
		verifyNoInteractions(shortUrlStatisticsService);
		verify(shortUrlRepository, times(0)).save(any(ShortUrl.class));
	}
	
	@Test
	public void getShortUrlByAlias_passing_invalid_alias_should_return_empty_optional () {
		String alias = "xxxx";
		
		doReturn(Optional.empty()).when(shortUrlRepository).findByAlias(eq(alias));
		
		Optional<ShortUrl> shortUrl = shortUrlService.getShortUrlByAlias(alias);
		
		assertFalse(shortUrl.isPresent());
		
		verify(shortUrlRepository, times(1)).findByAlias(eq(alias));
	}
	
	@Test
	public void getShortUrlByAlias_passing_valid_alias_should_return_shortUrl() {
		String alias = "123";
		Date creationDate = new Date();
		String longUrl = "http://www.example.com";
		
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.id(1L)
				.totalAccess(0L)
				.build();
		
		ShortUrl shortUrl = ShortUrl.builder()
				.alias(alias)
				.creationDate(creationDate)
				.longUrl(longUrl)
				.statistics(statistics)
				.build();
		
		statistics.setShortUrl(shortUrl);
		
		doReturn(Optional.ofNullable(shortUrl)).when(shortUrlRepository).findByAlias(eq(alias));
		
		Optional<ShortUrl> expectedShortUrl = shortUrlService.getShortUrlByAlias(alias);
		
		assertTrue(expectedShortUrl.isPresent());
		assertEquals(shortUrl, expectedShortUrl.get());
		
		verify(shortUrlRepository, times(1)).findByAlias(eq(alias));
	}
}
