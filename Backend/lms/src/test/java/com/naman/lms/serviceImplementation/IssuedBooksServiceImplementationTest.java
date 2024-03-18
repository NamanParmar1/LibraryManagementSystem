package com.naman.lms.serviceImplementation;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.naman.lms.entity.Book;
import com.naman.lms.entity.IssuedBooks;
import com.naman.lms.entity.Members;
import com.naman.lms.exception.BookException;
import com.naman.lms.exception.MemberException;
import com.naman.lms.exception.NotFoundException;
import com.naman.lms.model.BookOutputModel;
import com.naman.lms.model.IssueBooksOutputModel;
import com.naman.lms.model.MemberOutputModel;
import com.naman.lms.repository.BookRepository;
import com.naman.lms.repository.IssuedBooksRepository;
import com.naman.lms.repository.MemberRepository;

class IssuedBooksServiceImplementationTest {

    @Mock
    private IssuedBooksRepository issuedBooksRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private IssuedBooksServiceImplementation issuedBooksService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMembers_ValidBookId_ReturnsMemberOutputModelList() throws NotFoundException, MemberException, BookException {
        // Arrange
        int bookId = 1;

        Book book = new Book();
        book.setBookId(bookId);
        book.setName("The Great Gatsby");

        Members member1 = new Members();
        member1.setCardId(1);
        member1.setName("John Doe");

        Members member2 = new Members();
        member2.setCardId(2);
        member2.setName("Jane Smith");

        List<Members> membersList = new ArrayList<>();
        membersList.add(member1);
        membersList.add(member2);

        when(bookRepository.findById(eq(bookId))).thenReturn(Optional.of(book));
        when(issuedBooksRepository.findMembersByBook(eq(bookId))).thenReturn(membersList);

        // Act
        List<MemberOutputModel> result = issuedBooksService.getMembers(bookId);

        // Assert
        verify(bookRepository).findById(eq(bookId));
        verify(issuedBooksRepository).findMembersByBook(eq(bookId));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());

        MemberOutputModel outputModel1 = result.get(0);
        Assertions.assertEquals(member1.getCardId(), outputModel1.getCardId());
        Assertions.assertEquals(member1.getName(), outputModel1.getName());

        MemberOutputModel outputModel2 = result.get(1);
        Assertions.assertEquals(member2.getCardId(), outputModel2.getCardId());
        Assertions.assertEquals(member2.getName(), outputModel2.getName());
    }

    @Test
    void testGetMembers_InvalidBookId_ThrowsBookException() throws NotFoundException, MemberException, BookException {
        // Arrange
        int bookId = 1;

        when(bookRepository.findById(eq(bookId))).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(BookException.class, () -> issuedBooksService.getMembers(bookId));

        verify(bookRepository).findById(eq(bookId));
    }

    @Test
    void testGetMembers_BookNotIssued_ThrowsMemberException() throws NotFoundException, MemberException, BookException {
        // Arrange
        int bookId = 1;

        Book book = new Book();
        book.setBookId(bookId);
        book.setName("The Great Gatsby");

        when(bookRepository.findById(eq(bookId))).thenReturn(Optional.of(book));
        when(issuedBooksRepository.findMembersByBook(eq(bookId))).thenReturn(Collections.emptyList());

        // Act & Assert
        Assertions.assertThrows(MemberException.class, () -> issuedBooksService.getMembers(bookId));

        verify(bookRepository).findById(eq(bookId));
        verify(issuedBooksRepository).findMembersByBook(eq(bookId));
    }

    @Test
    void testGetBooks_ValidCardId_ReturnsBookOutputModelList() throws NotFoundException, MemberException {
        // Arrange
        int cardId = 1;

        Members member = new Members();
        member.setCardId(cardId);
        member.setName("John Doe");

        Book book1 = new Book();
        book1.setBookId(1);
        book1.setName("Book 1");

        Book book2 = new Book();
        book2.setBookId(2);
        book2.setName("Book 2");

        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);

        when(memberRepository.findById(eq(cardId))).thenReturn(Optional.of(member));
        when(issuedBooksRepository.findBooksByCard(eq(cardId))).thenReturn(bookList);

        // Act
        List<BookOutputModel> result = issuedBooksService.getBooks(cardId);

        // Assert
        verify(memberRepository).findById(eq(cardId));
        verify(issuedBooksRepository).findBooksByCard(eq(cardId));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());

        BookOutputModel outputModel1 = result.get(0);
        Assertions.assertEquals(book1.getBookId(), outputModel1.getBookId());
        Assertions.assertEquals(book1.getBookId(), outputModel1.getBookName());

        BookOutputModel outputModel2 = result.get(1);
        Assertions.assertEquals(book2.getBookId(), outputModel2.getBookId());
        Assertions.assertEquals(book2.getName(), outputModel2.getBookName());
    }

    @Test
    void testGetBooks_InvalidCardId_ThrowsMemberException() throws NotFoundException, MemberException {
        // Arrange
        int cardId = 1;

        when(memberRepository.findById(eq(cardId))).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(MemberException.class, () -> issuedBooksService.getBooks(cardId));

        verify(memberRepository).findById(eq(cardId));
    }

    @Test
    void testGetBooks_NoBooksIssued_ThrowsNotFoundException() throws NotFoundException, MemberException {
        // Arrange
        int cardId = 1;

        Members member = new Members();
        member.setCardId(cardId);
        member.setName("John Doe");

        when(memberRepository.findById(eq(cardId))).thenReturn(Optional.of(member));
        when(issuedBooksRepository.findBooksByCard(eq(cardId))).thenReturn(Collections.emptyList());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> issuedBooksService.getBooks(cardId));

        verify(memberRepository).findById(eq(cardId));
        verify(issuedBooksRepository).findBooksByCard(eq(cardId));
    }

    @Test
    void testGetBooks_ValidDate_ReturnsIssueBooksOutputModelList() throws NotFoundException {
        // Arrange
        LocalDate date = LocalDate.of(2022, 1, 1);

        IssuedBooks issuedBooks1 = new IssuedBooks();
        issuedBooks1.setLoanId(1);
        issuedBooks1.setTransactionDate(date);
        

        IssuedBooks issuedBooks2 = new IssuedBooks();
        issuedBooks2.setLoanId(2);
        issuedBooks2.setTransactionDate(date);
        

        List<IssuedBooks> issuedBooksList = new ArrayList<>();
        issuedBooksList.add(issuedBooks1);
        issuedBooksList.add(issuedBooks2);

        when(issuedBooksRepository.findBooksByDate(eq(date))).thenReturn(issuedBooksList);

        // Act
        List<IssueBooksOutputModel> result = issuedBooksService.getBooks(date);

        // Assert
        verify(issuedBooksRepository).findBooksByDate(eq(date));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());

        IssueBooksOutputModel outputModel1 = result.get(0);
        Assertions.assertEquals(issuedBooks1.getLoanId(), outputModel1.getLoanId());
        
       

        IssueBooksOutputModel outputModel2 = result.get(1);
        Assertions.assertEquals(issuedBooks2.getLoanId(), outputModel2.getLoanId());
       
       
    }

    @Test
    void testGetBooks_NullDate_ThrowsNotFoundException() throws NotFoundException {
        // Arrange
        LocalDate date = null;

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> issuedBooksService.getBooks(date));

        verify(issuedBooksRepository).findBooksByDate(eq(date));
    }

    @Test
    void testGetBooks_NoTransactionsFound_ThrowsNotFoundException() throws NotFoundException {
        // Arrange
        LocalDate date = LocalDate.of(2022, 1, 1);

        when(issuedBooksRepository.findBooksByDate(eq(date))).thenReturn(Collections.emptyList());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> issuedBooksService.getBooks(date));

        verify(issuedBooksRepository).findBooksByDate(eq(date));
    }
}
