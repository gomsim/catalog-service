package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CatalogServiceApplicationTests {

	private static final String ISBN = "1234567890";
	private static final String TITLE = "Hamlet";
	private static final String AUTHOR = "William Shakespeare";
	private static final double PRICE = 2.49;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void post() {
		Book expectedBook = new Book(ISBN, TITLE, AUTHOR, PRICE);

		webTestClient
				.post()
				.uri("/books")
				.bodyValue(expectedBook)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Book.class).value(actualBook -> {
					assertNotNull(actualBook);
					assertEquals(expectedBook.isbn(), actualBook.isbn());
				});
	}

}
