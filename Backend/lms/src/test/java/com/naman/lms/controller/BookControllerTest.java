package com.naman.lms.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.naman.lms.entity.Book;
import com.naman.lms.entity.Genre;
import com.naman.lms.exception.BookException;
import com.naman.lms.exception.NotFoundException;
import com.naman.lms.model.BookInputModel;
import com.naman.lms.model.BookOutputModel;
import com.naman.lms.serviceImplementation.BookServiceImplementation;

class BookControllerTest {

    @Mock
    private BookServiceImplementation bookService;

    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookController = new BookController();
        bookController.bookServiceImplementation = bookService;
    }

    @Test
    void testCreateBook() throws BookException {
        // Arrange
        BookInputModel bookInputModel = new BookInputModel("Book 1", "Author 1", "ISBN123", Genre.FICTIONAL);
        Book book = new Book("Book 1", Genre.FICTIONAL, "ISBN123", "Author 1");
        BookOutputModel expectedOutput = new BookOutputModel(book);

        when(bookService.createBook(bookInputModel)).thenReturn(expectedOutput);

        // Act
        ResponseEntity<BookOutputModel> response = bookController.createBook(bookInputModel);

        // Assert
        verify(bookService, times(1)).createBook(bookInputModel);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedOutput, response.getBody());
    }

    @Test
    void testGetBooks() throws NotFoundException, BookException {
        // Arrange
        Genre genre = Genre.FICTIONAL;
        boolean isAvailable = true;
        String author = "Author 1";
        String isbn = "ISBN123";
        List<BookOutputModel> expectedOutput = new ArrayList<>();
        Book book = new Book("Book 1", genre, author, isbn);
        expectedOutput.add(new BookOutputModel(book));

        when(bookService.getBooks(isbn, genre, isAvailable, author)).thenReturn(expectedOutput);

        // Act
        ResponseEntity<List<BookOutputModel>> response = bookController.getBooks(genre, isAvailable, author, isbn);

        // Assert
        verify(bookService, times(1)).getBooks(isbn, genre, isAvailable, author);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedOutput, response.getBody());
    }

    @Test
    void testDeleteBook_WithValidBookId() throws BookException {
        // Arrange
        String isbn = "ISBN123";
        int bookId = 1;
        BookOutputModel expectedOutput = new BookOutputModel(new Book("Book 1", Genre.FICTIONAL, "Author 1", isbn));

        when(bookService.deleteBook(isbn, bookId)).thenReturn(expectedOutput);

        // Act
        ResponseEntity<BookOutputModel> response = bookController.deleteBook(isbn, bookId);

        // Assert
        verify(bookService, times(1)).deleteBook(isbn, bookId);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(expectedOutput, response.getBody());
    }

    @Test
    void testDeleteBook_WithNullBookId() throws BookException {
        // Arrange
        String isbn = "ISBN123";
        Integer bookId = null;
        Book book = new Book("Book 1", Genre.FICTIONAL, "Author 1", isbn);
        BookOutputModel expectedOutput = new BookOutputModel(book);

        when(bookService.deleteBook(isbn, bookId)).thenReturn(expectedOutput);

        // Act
        ResponseEntity<BookOutputModel> response = bookController.deleteBook(isbn, bookId);

        // Assert
        verify(bookService, times(1)).deleteBook(isbn, bookId);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(expectedOutput, response.getBody());
    }
}
