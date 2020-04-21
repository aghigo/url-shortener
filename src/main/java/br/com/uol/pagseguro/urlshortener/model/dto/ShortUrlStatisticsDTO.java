package br.com.uol.pagseguro.urlshortener.model.dto;

import java.io.Serializable;

import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrlStatistics;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ShortUrlStatisticsDTO implements Serializable {
	private static final long serialVersionUID = -2734391922924226350L;

	private final long totalAccess;
	
	public static ShortUrlStatisticsDTO of(ShortUrlStatistics statistics) {
		return ShortUrlStatisticsDTO.builder()
				.totalAccess(statistics.getTotalAccess())
				.build();
	}
}
