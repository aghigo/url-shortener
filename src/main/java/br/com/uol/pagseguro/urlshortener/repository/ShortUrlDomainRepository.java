package br.com.uol.pagseguro.urlshortener.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrlDomain;

public interface ShortUrlDomainRepository extends CrudRepository<ShortUrlDomain, Long> {
	Optional<ShortUrlDomain> findByName(String string);
}
