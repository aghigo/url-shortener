package br.com.uol.pagseguro.urlshortener.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class ShortUrlAliasHashIdsGeneratorTest {
	private ShortUrlAliasGenerator shortUrlAliasGenerator;
	
	@BeforeEach
	public void setup() {
		this.shortUrlAliasGenerator = new ShortUrlAliasHashIdsGenerator();
	}
	
	@Test
	@Tag("UnitTest")
	public void generate_when_long_URL_is_valid_should_return_generated_alias_hash() {
		String longUrl = "http://www.example.com";
		
		String alias = shortUrlAliasGenerator.generate(longUrl);
		
		assertEquals("KLCLIx", alias);
	}
}
