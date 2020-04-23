package dev.andreghigo.urlshortener.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan( basePackages = {
		"dev.andreghigo.urlshortener.application",
		"dev.andreghigo.urlshortener.controller",
		"dev.andreghigo.urlshortener.exception",
		"dev.andreghigo.urlshortener.model.dto",
		"dev.andreghigo.urlshortener.model.entity",
		"dev.andreghigo.urlshortener.repository",
		"dev.andreghigo.urlshortener.config",
		"dev.andreghigo.urlshortener.logger",
		"dev.andreghigo.urlshortener.service",
		"dev.andreghigo.urlshortener.validator"
		})
@EntityScan(basePackages = "dev.andreghigo.urlshortener.model.entity")
@EnableJpaRepositories("dev.andreghigo.urlshortener.repository")
@PropertySource(value = "classpath:application.properties")
public class UrlShortenerApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(UrlShortenerApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(UrlShortenerApplication.class);
	}
}
