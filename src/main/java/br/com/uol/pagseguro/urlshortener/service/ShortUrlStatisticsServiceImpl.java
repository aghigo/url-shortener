package br.com.uol.pagseguro.urlshortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrlStatistics;
import br.com.uol.pagseguro.urlshortener.repository.ShortUrlStatisticsRepository;

@Service
public class ShortUrlStatisticsServiceImpl implements ShortUrlStatisticsService {
	private ShortUrlStatisticsRepository shortUrlStatisticsRepository;
	
	@Autowired
	public ShortUrlStatisticsServiceImpl(ShortUrlStatisticsRepository shortUrlStatisticsRepository) {
		super();
		this.shortUrlStatisticsRepository = shortUrlStatisticsRepository;
	}

	@Override
	public void incrementTotalAccess(ShortUrlStatistics statistics) {
		statistics.incrementTotalAccess();
		shortUrlStatisticsRepository.save(statistics);
	}

	@Override
	public ShortUrlStatistics createNewStatistics() {
		ShortUrlStatistics statistics = ShortUrlStatistics.builder()
				.totalAccess(0L)
				.build();
	
		return shortUrlStatisticsRepository.save(statistics);
	}
}
