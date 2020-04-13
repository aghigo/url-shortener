package br.com.uol.pagseguro.urlshortener.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Short URL representation of a long URL
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrl implements Serializable {
	private static final long serialVersionUID = 989434892875033570L;

	@Id
	private String alias;
	
	@Column
	private String longUrl;
	
	@Column
	private String shortUrl;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@OneToOne
	private ShortUrlStatistics statistics;
	
	/**
	 * Gets the short URL's unique reference alias. This alias can be used to compose with the service domain host URL in order to solve the absolute short URL among different domains.
	 * 
	 * <p>Note: The host should be resolved dynamically because of several instances of the same service that can be deployed on different domain servers (e.g. cloud, load balance, gateway, etc).
	 * <p>
	 * <p>Example:
	 * 
	 * <p>Long URL = https://www.example.com
	 * <p>Short URL alias = 123
	 * <p>Short URL Service host URL: http://service.com/
	 * <p>Absolute short URL: http://service.com/123
	 * 
	 * @return short URL's unique alias
	 */
	public String getAlias() {
		return alias;
	}
	
	/**
	 * Gets the original long URL from this short URL
	 * 
	 * @return long URL
	 */
	public String getLongUrl() {
		return longUrl;
	}
	
	/**
	 * Gets the date of the moment when this original long URL was shortened
	 * 
	 * @return date of the moment when this original long URL was shortened
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	
	/**
	 * Gets summary statistics about this short URL
	 * 
	 * @return statistics about this short URL
	 */
	public ShortUrlStatistics getStatistics() {
		return statistics;
	}
}