package br.com.uol.pagseguro.urlshortener.model.dto;

import java.io.Serializable;

import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrlStatistics;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for short URL statistics
 * 
 * @see ShortUrlStatistics
 */
@Data
@Builder
public final class ShortUrlStatisticsDTO implements Serializable {
	private static final long serialVersionUID = -2734391922924226350L;

	private final long totalAccess;
	
	public static ShortUrlStatisticsDTO of(ShortUrlStatistics shortUrlStatistics) {
		return ShortUrlStatisticsDTO.builder()
				.totalAccess(shortUrlStatistics.getTotalAccess())
				.build();
	}
}
