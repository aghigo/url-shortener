package br.com.uol.pagseguro.urlshortener.application;

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
		"br.com.uol.pagseguro.urlshortener.application",
		"br.com.uol.pagseguro.urlshortener.controller",
		"br.com.uol.pagseguro.urlshortener.exception",
		"br.com.uol.pagseguro.urlshortener.model.dto",
		"br.com.uol.pagseguro.urlshortener.model.entity",
		"br.com.uol.pagseguro.urlshortener.repository",
		"br.com.uol.pagseguro.urlshortener.config",
		"br.com.uol.pagseguro.urlshortener.logger",
		"br.com.uol.pagseguro.urlshortener.config.security",
		"br.com.uol.pagseguro.urlshortener.service",
		"br.com.uol.pagseguro.urlshortener.validator"
		})
@EntityScan(basePackages = "br.com.uol.pagseguro.urlshortener.model.entity")
@EnableJpaRepositories("br.com.uol.pagseguro.urlshortener.repository")
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
