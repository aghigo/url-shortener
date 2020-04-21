package br.com.uol.pagseguro.urlshortener.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
	@Tag("UnitTest")
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
	@Tag("UnitTest")
	public void createNewStatistics_should_return_new_initialized_statistics_data () {
		ArgumentCaptor<ShortUrlStatistics> argumentCaptor = ArgumentCaptor.forClass(ShortUrlStatistics.class);
		
		shortUrlStatisticsService.createNewStatistics();
		
		verify(shortUrlStatisticsRepository).save(argumentCaptor.capture());
		
		assertEquals(0L, argumentCaptor.getValue().getId());
		assertEquals(0L, argumentCaptor.getValue().getTotalAccess());
	}
}
