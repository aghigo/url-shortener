package br.com.uol.pagseguro.urlshortener.service;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uol.pagseguro.urlshortener.exception.ShortUrlException;
import br.com.uol.pagseguro.urlshortener.generator.ShortUrlAliasGenerator;
import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrl;
import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrlStatistics;
import br.com.uol.pagseguro.urlshortener.repository.ShortUrlRepository;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {
	private ShortUrlRepository shortUrlRepository;
	private ShortUrlStatisticsService shortUrlStatisticsService;
	private ShortUrlAliasGenerator shortUrlAliasGenerator;

	@Autowired
	public ShortUrlServiceImpl(ShortUrlRepository shortUrlRepository, ShortUrlStatisticsService shortUrlStatisticsService, ShortUrlAliasGenerator shortUrlAliasGenerator) {
		super();
		this.shortUrlRepository = shortUrlRepository;
		this.shortUrlStatisticsService = shortUrlStatisticsService;
		this.shortUrlAliasGenerator = shortUrlAliasGenerator;
	}

	@Override
	public ShortUrl shortUrl(String longUrl) throws ShortUrlException {
		String sanitizedLongUrl = longUrl.trim().replace(" ", "");
		
		UrlValidator urlValidator = new UrlValidator();
		if(!urlValidator.isValid(sanitizedLongUrl)) {
			throw new ShortUrlException("Invalid URL", new MalformedURLException(sanitizedLongUrl));
		}
		
		Optional<ShortUrl> currentShortUrl = shortUrlRepository.findByLongUrl(sanitizedLongUrl);
		if(currentShortUrl.isPresent()) {
			return currentShortUrl.get();
		}
		
		String alias = shortUrlAliasGenerator.generate(sanitizedLongUrl);
		
		ShortUrlStatistics statistics = shortUrlStatisticsService.createNewStatistics();
		
		ShortUrl shortUrl = ShortUrl.builder()
				.alias(alias)
				.longUrl(sanitizedLongUrl)
				.creationDate(new Date())
				.statistics(statistics)
				.build();
		
		return shortUrlRepository.save(shortUrl);
	}

	@Override
	public Optional<ShortUrl> getShortUrlByAlias(String alias) {
		return shortUrlRepository.findByAlias(alias);
	}
}
