package com.polarbookshop.catalogservice.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testng.Assert.assertEquals;

public class BookValidationTests {

    private static final String ISBN_CORRECT = "1234567890";
    private static final String TITLE_CORRECT = "Hamlet";
    private static final String AUTHOR_CORRECT = "William Shakespeare";
    private static final double PRICE_CORRECT = 2.49;

    private static final String ISBN_CONTAINS_NON_NUMBER = "a234567890";
    private static final String ISBN_WRONG_LENGTH = "123457890";
    private static final String TITLE_EMPTY = "";
    private static final String TITLE_BLANK = " ";
    private static final String AUTHOR_EMPTY = "";
    private static final String AUTHOR_BLANK = " ";
    private static final double PRICE_NEGATIVE = -2.49;
    private static final double PRICE_ZERO = 0;

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void allFieldsCorrect(){
        Book book = new Book(ISBN_CORRECT, TITLE_CORRECT, AUTHOR_CORRECT, PRICE_CORRECT);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertTrue(violations.isEmpty());
    }

    @DataProvider
    public Object[][] dataProvider(){
        return new Object[][] {
                {ISBN_CONTAINS_NON_NUMBER,  TITLE_CORRECT,  AUTHOR_CORRECT, PRICE_CORRECT},
                {ISBN_WRONG_LENGTH,         TITLE_CORRECT,  AUTHOR_CORRECT, PRICE_CORRECT},
                {ISBN_CORRECT,              TITLE_EMPTY,    AUTHOR_CORRECT, PRICE_CORRECT},
                {ISBN_CORRECT,              TITLE_BLANK,    AUTHOR_CORRECT, PRICE_CORRECT},
                {ISBN_CORRECT,              TITLE_CORRECT,  AUTHOR_EMPTY,   PRICE_CORRECT},
                {ISBN_CORRECT,              TITLE_CORRECT,  AUTHOR_BLANK,   PRICE_CORRECT},
                {ISBN_CORRECT,              TITLE_CORRECT,  AUTHOR_CORRECT, PRICE_NEGATIVE},
                {ISBN_CORRECT,              TITLE_CORRECT,  AUTHOR_CORRECT, PRICE_ZERO},
        };
    }

    @Test(dataProvider = "dataProvider")
    public void incorrectFields(String isbn, String title, String author, double price){
        Book book = new Book(isbn, title, author, price);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals(violations.size(), 1);
    }

}
