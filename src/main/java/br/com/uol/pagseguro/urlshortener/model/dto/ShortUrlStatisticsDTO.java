package br.com.uol.pagseguro.urlshortener.model.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrlStatistics;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public final class ShortUrlStatisticsDTO implements Serializable {
	private static final long serialVersionUID = -2734391922924226350L;

	private final long totalAccess;
	private final Date lastAccess;
	
	public static ShortUrlStatisticsDTO of(ShortUrlStatistics statistics) {
		return ShortUrlStatisticsDTO.builder()
				.totalAccess(statistics.getTotalAccess())
				.lastAccess(statistics.getLastAccess())
				.build();
	}
}
