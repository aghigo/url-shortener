package br.com.uol.pagseguro.urlshortener.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit test of {@link ShortUrlException}
 * 
 * @see ShortUrlException
 *
 */
public class ShortUrlExceptionTest {
	@Test
	public void messageArgConstructor() {
		String message = "testing exception";
		
		ShortUrlException exception = new ShortUrlException(message);
		
		assertEquals(message, exception.getMessage());
	}
	
	@Test
	public void causeArgConstructor() {
		Exception cause = new Exception();
		
		ShortUrlException exception = new ShortUrlException(cause);
		
		assertEquals(cause, exception.getCause());
	}
	
	@Test
	public void messageAndCauseArgsConstructor() {
		String message = "testing exception";
		Exception cause = new Exception();
		
		ShortUrlException exception = new ShortUrlException(message, cause);
		
		assertEquals(message, exception.getMessage());
		assertEquals(cause, exception.getCause());
	}
}
