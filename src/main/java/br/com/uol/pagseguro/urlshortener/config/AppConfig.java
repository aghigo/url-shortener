package br.com.uol.pagseguro.urlshortener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.uol.pagseguro.urlshortener.generator.ShortUrlAliasGenerator;
import br.com.uol.pagseguro.urlshortener.generator.ShortUrlAliasHashIdsGenerator;

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
