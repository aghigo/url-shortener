package br.com.uol.pagseguro.urlshortener.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Summary statistics about a short URL, such as total access, history, referrer.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlStatistics implements Serializable {
	private static final long serialVersionUID = -702947345942970404L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	@Column
	private long totalAccess;
	
	/**
	 * Gets the total number of times the short URL was accessed
	 * 
	 * @return the total number of times the short URL was accessed
	 */
	public long getTotalAccess() {
		return totalAccess;
	}

	/**
	 * Increments the current total access value by one
	 */
	public void incrementTotalAccess() {
		totalAccess++;
	}
}
