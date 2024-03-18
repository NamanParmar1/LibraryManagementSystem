package com.naman.lms.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.naman.lms.exception.BookException;
import com.naman.lms.exception.MemberException;
import com.naman.lms.exception.NotFoundException;
import com.naman.lms.exception.WrongDateException;
import com.naman.lms.model.BookOutputModel;
import com.naman.lms.model.IssueBooksOutputModel;
import com.naman.lms.model.MemberOutputModel;
import com.naman.lms.serviceImplementation.IssuedBooksServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class IssuedBooksControllerTest {

    @Mock
    private IssuedBooksServiceImplementation issuedBooksServiceImplementation;

    @InjectMocks
    private IssuedBooksController issuedBooksController;

    @Test
    public void testGetMembers() throws NotFoundException, MemberException, BookException {
        // Prepare
        int bookId = 1;
        List<MemberOutputModel> membersList = new ArrayList<>();
        // Add mock behavior to the service implementation
        when(issuedBooksServiceImplementation.getMembers(bookId)).thenReturn(membersList);

        // Execute
        ResponseEntity<List<MemberOutputModel>> response = issuedBooksController.getMembers(bookId);

        // Assert
        assertEquals(membersList, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetBooks() throws NotFoundException, MemberException {
        // Prepare
        int cardId = 1;
        List<BookOutputModel> bookList = new ArrayList<>();
        // Add mock behavior to the service implementation
        when(issuedBooksServiceImplementation.getBooks(cardId)).thenReturn(bookList);

        // Execute
        ResponseEntity<List<BookOutputModel>> response = issuedBooksController.getBooks(cardId);

        // Assert
        assertEquals(bookList, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetBooksByDate() throws NotFoundException, WrongDateException {
        // Prepare
        LocalDate date = LocalDate.now();
        List<IssueBooksOutputModel> bookList = new ArrayList<>();
        // Add mock behavior to the service implementation
        when(issuedBooksServiceImplementation.getBooks(date)).thenReturn(bookList);

        // Execute
        ResponseEntity<List<IssueBooksOutputModel>> response = issuedBooksController.getBooks(date);

        // Assert
        assertEquals(bookList, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
