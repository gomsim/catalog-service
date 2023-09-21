package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTests {

    private static final String ISBN = "1234567890";
    private static final String TITLE = "Hamlet";
    private static final String AUTHOR = "William Shakespeare";
    private static final double PRICE = 2.49;

    private static final Book book = new Book(ISBN, TITLE, AUTHOR, PRICE);

    @Autowired
    private JacksonTester<Book> json;

    @Test
    public void serialize() throws Exception {
        JsonContent<Book> jsonContent = json.write(book);

        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn").isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author").isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price").isEqualTo(book.price());
    }

    @Test
    public void deserialize() throws Exception {
        String content =
                """
                {
                    "isbn":"%s",
                    "title":"%s",
                    "author":"%s",
                    "price":"%f"
                }
                """.formatted(ISBN, TITLE, AUTHOR, PRICE).replaceFirst("(?<=([0-9])),(?=([0-9]))", ".");
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(book);
    }

}
