package dev.andreghigo.urlshortener.repository;

import org.springframework.data.repository.CrudRepository;

import dev.andreghigo.urlshortener.model.entity.ShortUrlStatistics;

/**
 * Repository for short URL statistics data persistence
 * 
 * @see ShortUrlStatistics
 */
public interface ShortUrlStatisticsRepository extends CrudRepository<ShortUrlStatistics, Long> {}