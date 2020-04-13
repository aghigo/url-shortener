package br.com.uol.pagseguro.urlshortener.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrlStatistics;

/**
 * Repository for short URL statistics data persistence
 */
public interface ShortUrlStatisticsRepository extends CrudRepository<ShortUrlStatistics, Long> {}