package br.com.uol.pagseguro.urlshortener.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import br.com.uol.pagseguro.urlshortener.application.UrlShortenerApplication;
import br.com.uol.pagseguro.urlshortener.model.dto.ShortUrlDTO;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

/**
 * Integration tests of Short URL Rest API endpoints
 * 
 * @see ShortUrlController
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = UrlShortenerApplication.class)
@TestMethodOrder(OrderAnnotation.class)
public class ShortUrlControllerIntegrationTest {
	@LocalServerPort
    private int port;
	
	private RestTemplate restTemplate;
	
	@BeforeEach
	private void configureRestAssured() {
		RestAssured.port = this.port;
		RestAssured.defaultParser = Parser.JSON;
	}
	
	@BeforeEach
	private void configureRestTemplate() {
		this.restTemplate = new RestTemplate();
	}
	
	@Test
    @Order(1)    
    public void test_loaded_configurations() {
        assertTrue(port > 0);
        assertEquals(this.port, RestAssured.port);
        assertNotNull(restTemplate);
    }

	@Test
	@Order(2)
	public void short_long_url_passing_no_url_should_return_bad_request_error () {
		given().
		when().
			post("/").
		then().
			statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@Order(3)
	public void short_long_url_passing_invalid_long_url_should_return_bad_request_error () {
		String invalidLongUrl = "DGUIHDJFHDF";
		
		given().
			formParam("longUrl", invalidLongUrl).
		when().
			post("/").
		then().
			statusCode(HttpStatus.BAD_REQUEST.value()).
				and().
			body("status", equalTo(HttpStatus.BAD_REQUEST.value())).and().
			body("error", equalTo(HttpStatus.BAD_REQUEST.getReasonPhrase())).and().
			body("message", equalTo("Invalid URL"));
	}
	
	@Test
	@Order(4)
	public void short_long_url_passing_valid_long_url_should_create_new_short_url_and_return_short_url () {
		String longUrl = "https://pagseguro.uol.com.br/";
		String alias = "laFvIy";
		
		given().
			formParam("longUrl", longUrl).
		when().
			post("/").
		then().
			statusCode(HttpStatus.OK.value()).
			body("alias", equalTo(alias)).and().
			body("longUrl", equalTo(longUrl));
	}
	
	@Test
	@Order(4)
	public void redirect_to_original_url_passing_non_existing_short_url_alias_should_return_not_found_error () {
		String invalidShortUrlId = "24u04u2309udidchinvxcklnvcxcvc";
		
		given().
		when().
			get("/" + invalidShortUrlId).
		then().
			statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	@Order(5)
	public void redirect_to_original_url_passing_valid_short_url_alias_and_original_url_found_should_redirect_to_long_url () {
		String longUrl = "https://www.google.com";
		
		String longUrlResponseBody = restTemplate.getForObject(longUrl, String.class);
		
		String googleImageElementHtml = "<img alt=\"Google\"";
		
		assertTrue(longUrlResponseBody.contains(googleImageElementHtml));

		ShortUrlDTO shortUrl = given().
								formParam("longUrl", longUrl).
							when()
								.post("/")
							.body().as(ShortUrlDTO.class);
		
		given().
		when()
			.get("/" + shortUrl.getAlias()).
		then().
			body(stringContainsInOrder(googleImageElementHtml));
	}
	
	@Test
	@Order(6)
	public void get_short_url_statistics_passing_non_existing_alias_should_return_not_found_error () {
		String alias = "xxx";
		
		given().
			header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).
		when().
			get("/" + alias + "/statistics").
		then().
			statusCode(HttpStatus.NOT_FOUND.value()).
				and().
			body("status", equalTo(HttpStatus.NOT_FOUND.value())).and().
			body("error", equalTo(HttpStatus.NOT_FOUND.getReasonPhrase())).and().
			body("message", equalTo("Short URL not found"));
	}
	
	@Test
	@Order(7)
	public void get_short_url_statistics_passing_existing_alias_should_return_statistics_data () {
		String longUrl = "https://www.example.com";
		
		ShortUrlDTO shortUrl = given().
				formParam("longUrl", longUrl).
			when()
				.post("/")
			.body().as(ShortUrlDTO.class);
		
		given().
		when()
			.get("/" + shortUrl.getAlias());
		
		given().
		when()
			.get("/" + shortUrl.getAlias());
		
		given().
			header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).
		when().
			get("/" + shortUrl.getAlias() + "/statistics").
		then().
			statusCode(HttpStatus.OK.value()).
				and().
			body("totalAccess", equalTo(2));
	}
}
