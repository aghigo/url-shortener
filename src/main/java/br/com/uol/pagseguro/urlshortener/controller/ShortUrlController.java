package br.com.uol.pagseguro.urlshortener.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.uol.pagseguro.urlshortener.exception.ShortUrlException;
import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrl;
import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrlStatistics;
import br.com.uol.pagseguro.urlshortener.service.ShortUrlService;
import br.com.uol.pagseguro.urlshortener.service.ShortUrlStatisticsService;

@RestController
@RequestMapping("/")
public class ShortUrlController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ShortUrlController.class);

	private ShortUrlService shortUrlService;
	private ShortUrlStatisticsService shortUrlStatisticsService;
	
	@Autowired
	public ShortUrlController(ShortUrlService shortUrlService, ShortUrlStatisticsService shortUrlStatisticsService) {
		super();
		this.shortUrlService = shortUrlService;
		this.shortUrlStatisticsService = shortUrlStatisticsService;
	}
	
	@PostMapping
	public ResponseEntity<ShortUrl> shortUrl(@RequestParam("longUrl") String longUrl) {
		LOGGER.info("Request: {} / longUrl = {}", HttpMethod.POST, longUrl);
		
		try {
			ShortUrl shortUrl = shortUrlService.shortUrl(longUrl);
			
			LOGGER.info("Response: {} {}", HttpStatus.OK, shortUrl);
			return ResponseEntity.ok(shortUrl);
		} catch (ShortUrlException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}
	
	@GetMapping("/{alias}")
	public ResponseEntity<Object> redirectToOriginalUrl(@PathVariable("alias") String alias) {
		LOGGER.info("{} /{}", HttpMethod.GET, alias);
		
		Optional<ShortUrl> shortUrl = shortUrlService.getShortUrlByAlias(alias);
		
		if(shortUrl.isPresent()) {
			shortUrlStatisticsService.incrementTotalAccess(shortUrl.get().getStatistics());
			
			String longUrl = shortUrl.get().getLongUrl();
			LOGGER.info("Redirect to {}", longUrl);
			return ResponseEntity.status(HttpStatus.SEE_OTHER).header(HttpHeaders.LOCATION, longUrl).build();
		} else {
			LOGGER.info("Long URL not found");
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/{alias}/statistics")
	public ResponseEntity<Object> getShortUrlStatistics(@PathVariable("alias") String alias) {
		LOGGER.info("Request: {} /{}/statistics", HttpMethod.GET, alias);
		
		Optional<ShortUrl> shortUrl = shortUrlService.getShortUrlByAlias(alias);
		
		if(shortUrl.isPresent()) {
			ShortUrlStatistics statistics = shortUrl.get().getStatistics();
			
			LOGGER.info("Response: {} {}", HttpStatus.OK, statistics);
			return ResponseEntity.ok().body(statistics);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Short URL not found");
		}
	}
}
