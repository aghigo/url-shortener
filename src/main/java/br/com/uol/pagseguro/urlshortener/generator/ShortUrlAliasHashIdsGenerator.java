package br.com.uol.pagseguro.urlshortener.generator;

import org.hashids.Hashids;

/**
 * Generates short URL aliases using hashids library
 * 
 * <p><a href="https://hashids.org/java/" target="_blank">https://hashids.org/java/</a>
 */
public class ShortUrlAliasHashIdsGenerator implements ShortUrlAliasGenerator {
	@Override
	public String generate(String longUrl) {
		return new Hashids(longUrl).encode(1, 2, 3);
	}
}
