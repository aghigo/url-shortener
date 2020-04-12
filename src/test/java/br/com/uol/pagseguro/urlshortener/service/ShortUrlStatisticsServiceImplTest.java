package br.com.uol.pagseguro.urlshortener.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrl;
import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrlStatistics;
import br.com.uol.pagseguro.urlshortener.repository.ShortUrlStatisticsRepository;

/**
 * Unit tests for {@link ShortUrlStatisticsServiceImpl}
 * 
 * @see ShortUrlStatisticsService
 *
 */
public class ShortUrlStatisticsServiceImplTest {
	@Mock
	private ShortUrlStatisticsRepository shortUrlStatisticsRepository;
	
	private ShortUrlStatisticsService shortUrlStatisticsService;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.shortUrlStatisticsService = new ShortUrlStatisticsServiceImpl(shortUrlStatisticsRepository);
	}
	
	@Test
	public void getStatisticsByShortUrl_passing_invalid_short_url_should_return_empty_optional() {
		ShortUrl shortUrl = null;
		
		doReturn(Optional.empty()).when(shortUrlStatisticsRepository).findByShortUrl(eq(shortUrl));
		
		Optional<ShortUrlStatistics> shortUrlStatistics = shortUrlStatisticsService.getStatisticsByShortUrl(shortUrl);
		
		assertFalse(shortUrlStatistics.isPresent());
		
		verify(shortUrlStatisticsRepository, times(1)).findByShortUrl(eq(shortUrl));
	}
	
	@Test
	public void getStatisticsByShortUrl_passing_valid_short_url_should_return_short_url_wrapped_by_optional () {
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.id(1L)
				.totalAccess(1)
				.build();
		
		ShortUrl shortUrl = ShortUrl.builder()
				.alias("123")
				.creationDate(new Date())
				.longUrl("http://www.example.com")
				.statistics(statistics)
				.build();
		
		statistics.setShortUrl(shortUrl);
		
		doReturn(Optional.ofNullable(statistics)).when(shortUrlStatisticsRepository).findByShortUrl(eq(shortUrl));
		
		Optional<ShortUrlStatistics> shortUrlStatistics = shortUrlStatisticsService.getStatisticsByShortUrl(shortUrl);
		
		assertTrue(shortUrlStatistics.isPresent());
		assertEquals(statistics, shortUrlStatistics.get());
		
		verify(shortUrlStatisticsRepository, times(1)).findByShortUrl(eq(shortUrl));
	}
	
	@Test
	public void incrementTotalAccess_should_increment_total_access_value_by_one () {
		int totalAccess = 1;
		
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.id(1L)
				.totalAccess(totalAccess)
				.build();
		
		shortUrlStatisticsService.incrementTotalAccess(statistics);
		
		assertEquals(statistics.getTotalAccess(), totalAccess + 1);
	}
	
	@Test
	public void createNewStatistics_should_return_new_initialized_statistics_data () {
		ArgumentCaptor<ShortUrlStatistics> argumentCaptor = ArgumentCaptor.forClass(ShortUrlStatistics.class);
		
		shortUrlStatisticsService.createNewStatistics();
		
		verify(shortUrlStatisticsRepository).save(argumentCaptor.capture());
		
		assertEquals(0L, argumentCaptor.getValue().getId());
		assertEquals(0L, argumentCaptor.getValue().getTotalAccess());
	}
}
