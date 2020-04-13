package br.com.uol.pagseguro.urlshortener.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import br.com.uol.pagseguro.urlshortener.exception.ShortUrlException;
import br.com.uol.pagseguro.urlshortener.generator.ShortUrlAliasGenerator;
import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrl;
import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrlStatistics;
import br.com.uol.pagseguro.urlshortener.repository.ShortUrlRepository;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {
	private Environment environment;
	private ShortUrlRepository shortUrlRepository;
	private ShortUrlStatisticsService shortUrlStatisticsService;
	private ShortUrlAliasGenerator shortUrlAliasGenerator;

	@Autowired
	public ShortUrlServiceImpl(Environment environment, ShortUrlRepository shortUrlRepository, ShortUrlStatisticsService shortUrlStatisticsService, ShortUrlAliasGenerator shortUrlAliasGenerator) {
		super();
		this.environment = environment;
		this.shortUrlRepository = shortUrlRepository;
		this.shortUrlStatisticsService = shortUrlStatisticsService;
		this.shortUrlAliasGenerator = shortUrlAliasGenerator;
	}

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
		
		ShortUrl shortUrl = ShortUrl.builder()
				.alias(alias)
				.longUrl(formattedUrl)
				.shortUrl(resolveShortUrl(alias))
				.creationDate(new Date())
				.statistics(statistics)
				.build();
		
		return shortUrlRepository.save(shortUrl);
	}

	@Override
	public Optional<ShortUrl> getShortUrlByAlias(String alias) {
		return shortUrlRepository.findByAlias(alias);
	}
	
	private String resolveShortUrl(String alias) {
		String domainUrlTemplate = environment.getProperty("application.domain.short-url.template");
		return String.format(domainUrlTemplate, alias);
	}
}
