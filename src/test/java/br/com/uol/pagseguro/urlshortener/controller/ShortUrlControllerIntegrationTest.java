package br.com.uol.pagseguro.urlshortener.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
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
import org.springframework.web.client.RestTemplate;

import br.com.uol.pagseguro.urlshortener.application.UrlShortenerApplication;
import br.com.uol.pagseguro.urlshortener.model.entity.ShortUrl;
import io.restassured.RestAssured;

/**
 * Integration tests of Short URL Rest API endpoints
 * 
 * @see ShortUrlController
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = UrlShortenerApplication.class)
@TestMethodOrder(OrderAnnotation.class)
public class ShortUrlControllerIntegrationTest {
	private static final String HTTP_LOCALHOST = "http://localhost";
	
	@LocalServerPort
    private int port;
	
	private RestTemplate restTemplate;
	
	@BeforeEach
	private void configureRestAssured() {
		RestAssured.port = this.port;
	}
	
	@BeforeEach
	private void configureRestTemplate() {
		this.restTemplate = new RestTemplate();
	}
	
	private String getBaseUrl() {
		return RestAssured.baseURI + ":" + RestAssured.port;
	}
	
	@Test
    @Order(1)    
    public void test_loaded_configurations() {
        assertTrue(port > 0);
        assertEquals(this.port, RestAssured.port);
        assertEquals(HTTP_LOCALHOST, RestAssured.baseURI);
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
			statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@Order(4)
	public void short_long_url_passing_valid_long_url_should_create_new_short_url_and_return_short_url () {
		String longUrl = "https://www.google.com.br/";
		String shortUrlId = "123";
		
		given().
			formParam("longUrl", longUrl).
		when().
			post("/").
		then().
			statusCode(HttpStatus.OK.value()).
			body(equalTo("{\"id\":\"" + shortUrlId + "\",\"longUrl\":\"" + longUrl + "\"}"));
	}
	
	@Test
	@Order(4)
	public void redirect_to_original_url_passing_valid_short_url_id_but_original_url_not_found_should_return_not_found_error () {
		String invalidShortUrlId = "24u04u2309udidchinvxcklnvcxcvc";
		
		given().
		when().
			get("/" + invalidShortUrlId).
		then().
			statusCode(HttpStatus.BAD_REQUEST.value()).
			body(contains("short URL does not exist"));
	}
	
	@Test
	@Order(5)
	public void redirect_to_original_url_passing_valid_short_url_id_and_original_url_found_should_redirect_to_original_url () {
		String originalUrl = "https://www.google.com/";

		ShortUrl shortUrl = given().
								formParam("longUrl", originalUrl).
							when()
								.post("/")
							.body().as(ShortUrl.class);

		given().
		when().
			get(getBaseUrl() + "/" + shortUrl.getAlias()).
		then().
			statusCode(HttpStatus.OK.value()).
			header(HttpHeaders.LOCATION, originalUrl);
	}
}
