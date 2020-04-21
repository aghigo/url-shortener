package br.com.uol.pagseguro.urlshortener.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import br.com.uol.pagseguro.urlshortener.exception.ShortUrlException;
import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrlDomain;
import br.com.uol.pagseguro.urlshortener.repository.ShortUrlDomainRepository;

@Service
public class ShortUrlDomainServiceImpl implements ShortUrlDomainService {
	private Environment environment;
	private ShortUrlDomainRepository shortUrlDomainRepository;
	
	@Autowired
	public ShortUrlDomainServiceImpl(Environment environment, ShortUrlDomainRepository shortUrlDomainRepository) {
		super();
		this.environment = environment;
		this.shortUrlDomainRepository = shortUrlDomainRepository;
	}
	
	@Override
	public ShortUrlDomain getDefaultDomain() throws ShortUrlException {
		Optional<ShortUrlDomain> defaultDomain = shortUrlDomainRepository.findByName("default");
		if(defaultDomain.isPresent()) {
			return defaultDomain.get();
		} else {
			ShortUrlDomain domain = ShortUrlDomain.builder()
					.baseUrl(environment.getProperty("application.domain.base-url"))
					.name("default")
					.build();
			
			return shortUrlDomainRepository.save(domain);
		}
	}
}
