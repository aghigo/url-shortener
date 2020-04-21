package br.com.uol.pagseguro.urlshortener.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.uol.pagseguro.urlshortener.exception.ShortUrlException;
import br.com.uol.pagseguro.urlshortener.generator.ShortUrlAliasGenerator;
import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrl;
import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrlDomain;
import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrlStatistics;
import br.com.uol.pagseguro.urlshortener.repository.ShortUrlRepository;

@Service
@CacheConfig(cacheNames = "shortUrlCache")
public class ShortUrlServiceImpl implements ShortUrlService {
	private ShortUrlRepository shortUrlRepository;
	private ShortUrlStatisticsService shortUrlStatisticsService;
	private ShortUrlDomainService shortUrlDomainService;
	private ShortUrlAliasGenerator shortUrlAliasGenerator;

	@Autowired
	public ShortUrlServiceImpl(ShortUrlRepository shortUrlRepository, ShortUrlStatisticsService shortUrlStatisticsService, ShortUrlDomainService shortUrlDomainService, ShortUrlAliasGenerator shortUrlAliasGenerator) {
		super();
		this.shortUrlRepository = shortUrlRepository;
		this.shortUrlStatisticsService = shortUrlStatisticsService;
		this.shortUrlDomainService = shortUrlDomainService;
		this.shortUrlAliasGenerator = shortUrlAliasGenerator;
	}
	
	@Cacheable(condition = "#p1 != null")
	@Override
	public ShortUrl shortUrl(String longUrl) throws ShortUrlException {
		URL url = null;
		try {
			url = new URL(longUrl);
		} catch (MalformedURLException e) {
			throw new ShortUrlException("Invalid URL", e);
		}
		
		String formattedUrl = url.toString();

		Optional<ShortUrl> currentShortUrl = shortUrlRepository.findByLongUrlOrShortUrl(formattedUrl);
		if(currentShortUrl.isPresent()) {
			return currentShortUrl.get();
		}
		
		String alias = shortUrlAliasGenerator.generate(formattedUrl);
		
		ShortUrlStatistics statistics = shortUrlStatisticsService.createNewStatistics();
		
		ShortUrlDomain domain = shortUrlDomainService.getDefaultDomain();
		
		ShortUrl shortUrl = ShortUrl.builder()
				.alias(alias)
				.longUrl(formattedUrl)
				.creationDate(new Date())
				.statistics(statistics)
				.domain(domain)
				.build();
		
		return shortUrlRepository.save(shortUrl);
	}
	
	@Cacheable(condition = "#p1 != null")
	@Override
	public Optional<ShortUrl> getShortUrlByAlias(String alias) {
		return shortUrlRepository.findByAlias(alias);
	}
}
