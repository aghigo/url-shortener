package dev.andreghigo.urlshortener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.andreghigo.urlshortener.generator.ShortUrlAliasGenerator;
import dev.andreghigo.urlshortener.generator.ShortUrlAliasHashIdsGenerator;

/**
 * Application general configurations
 */
@Configuration
public class AppConfig {
	@Bean
	public ShortUrlAliasGenerator shortUrlAliasGenerator() {
		return new ShortUrlAliasHashIdsGenerator();
	}
}
