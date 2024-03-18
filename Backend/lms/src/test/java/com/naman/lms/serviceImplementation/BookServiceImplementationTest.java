package com.naman.lms.serviceImplementation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.naman.lms.entity.Book;
import com.naman.lms.entity.Genre;
import com.naman.lms.exception.BookException;
import com.naman.lms.exception.NotFoundException;
import com.naman.lms.model.BookInputModel;
import com.naman.lms.model.BookOutputModel;
import com.naman.lms.repository.BookRepository;
import com.naman.lms.repository.IssuedBooksRepository;
import com.naman.lms.util.MapInputBooks;

class BookServiceImplementationTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private IssuedBooksRepository issuedBooksRepository;

    @InjectMocks
    private BookServiceImplementation bookService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBook_ValidInputModel_ReturnsBookOutputModel() throws BookException {
        // Arrange
        BookInputModel inputModel = new BookInputModel();
        inputModel.setIsbn("1234567890");
        inputModel.setName("Test Book");
        inputModel.setAuthor("John Doe");

        Book book = MapInputBooks.map(inputModel);

        when(bookRepository.findBooksByISBN(eq(inputModel.getIsbn()), eq(true))).thenReturn(null);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Act
        BookOutputModel result = bookService.createBook(inputModel);

        // Assert
        verify(bookRepository).findBooksByISBN(eq(inputModel.getIsbn()), eq(true));
        verify(bookRepository).save(any(Book.class));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(book.getBookId(), result.getBookId());
        Assertions.assertEquals(book.getIsbn(), result.getISBN());
        Assertions.assertEquals(book.getName(), result.getBookName());
        Assertions.assertEquals(book.getAuthor(), result.getAuthor());
    }

    @Test
    void testCreateBook_ExistingISBN_ThrowsBookException() throws BookException {
        // Arrange
        BookInputModel inputModel = new BookInputModel();
        inputModel.setIsbn("1234567890");
        inputModel.setName("Test Book");
        inputModel.setAuthor("John Doe");

        when(bookRepository.findBooksByISBN(eq(inputModel.getIsbn()), eq(true))).thenReturn(new Book());

        // Act & Assert
        Assertions.assertThrows(BookException.class, () -> bookService.createBook(inputModel));

        verify(bookRepository).findBooksByISBN(eq(inputModel.getIsbn()), eq(true));
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void testDeleteBook_WithBookId_ReturnsBookOutputModel() throws BookException {
        // Arrange
        int bookId = 1;
        Book book = new Book();
        book.setBookId(bookId);

        when(bookRepository.findBooksById(eq(bookId))).thenReturn(book);
        when(issuedBooksRepository.findById(eq(bookId))).thenReturn(null);
       

        // Act
        BookOutputModel result = bookService.deleteBook(null, bookId);

        // Assert
        verify(bookRepository).findBooksById(eq(bookId));
        verify(issuedBooksRepository).findById(eq(bookId));
        verify(bookRepository).deleteById(eq(bookId));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(book.getBookId(), result.getBookId());
    }

    @Test
    void testDeleteBook_WithISBN_ReturnsBookOutputModel() throws BookException {
        // Arrange
        String isbn = "1234567890";
        int bookId = 1;
        Book book = new Book();
        book.setBookId(bookId);

        when(bookRepository.findBookIdByISBN(eq(isbn))).thenReturn(bookId);
        when(bookRepository.findBooksById(eq(bookId))).thenReturn(book);
        when(issuedBooksRepository.findById(eq(bookId))).thenReturn(null);
        

        // Act
        BookOutputModel result = bookService.deleteBook(isbn, null);

        // Assert
        verify(bookRepository).findBookIdByISBN(eq(isbn));
        verify(bookRepository).findBooksById(eq(bookId));
        verify(issuedBooksRepository).findById(eq(bookId));
        verify(bookRepository).deleteById(eq(bookId));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(book.getBookId(), result.getBookId());
    }

    @Test
    void testDeleteBook_NullInput_ThrowsBookException() throws BookException {
        // Act & Assert
        Assertions.assertThrows(BookException.class, () -> bookService.deleteBook(null, null));

        verify(bookRepository, never()).findBooksById(anyInt());
        verify(issuedBooksRepository, never()).findById(anyInt());
        verify(bookRepository, never()).deleteById(anyInt());
    }

    @Test
    void testDeleteBook_BookNotFound_ThrowsBookException() throws BookException {
        // Arrange
        int bookId = 1;

        when(bookRepository.findBooksById(eq(bookId))).thenReturn(null);

        // Act & Assert
        Assertions.assertThrows(BookException.class, () -> bookService.deleteBook(null, bookId));

        verify(bookRepository).findBooksById(eq(bookId));
        verify(issuedBooksRepository, never()).findById(anyInt());
        verify(bookRepository, never()).deleteById(anyInt());
    }

    @Test
    void testDeleteBook_BookNotReturned_ThrowsBookException() throws BookException {
        // Arrange
        int bookId = 1;
        Book book = new Book();
        book.setBookId(bookId);

        when(bookRepository.findBooksById(eq(bookId))).thenReturn(book);
        //when(issuedBooksRepository.findById(eq(bookId))).thenReturn(new IssuedBooks());

        // Act & Assert
        Assertions.assertThrows(BookException.class, () -> bookService.deleteBook(null, bookId));

        verify(bookRepository).findBooksById(eq(bookId));
        verify(issuedBooksRepository).findById(eq(bookId));
        verify(bookRepository, never()).deleteById(anyInt());
    }

    @Test
    void testGetBooks_ByISBN_ReturnsBookOutputModelList() throws NotFoundException, BookException {
        // Arrange
        String isbn = "1234567890";
        boolean isAvailable = true;
        Book book = new Book();
        book.setBookId(1);
        book.setIsbn(isbn);

        when(bookRepository.findBooksByISBN(eq(isbn), eq(isAvailable))).thenReturn(book);

        // Act
        List<BookOutputModel> result = bookService.getBooks(isbn, null, isAvailable, null);

        // Assert
        verify(bookRepository).findBooksByISBN(eq(isbn), eq(isAvailable));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());

        BookOutputModel outputModel = result.get(0);
        Assertions.assertEquals(book.getBookId(), outputModel.getBookId());
        Assertions.assertEquals(book.getIsbn(), outputModel.getISBN());
        Assertions.assertEquals(book.getName(), outputModel.getBookName());
        Assertions.assertEquals(book.getAuthor(), outputModel.getAuthor());
    }

    @Test
    void testGetBooks_ByGenreAndAuthor_ReturnsBookOutputModelList() throws NotFoundException, BookException {
        // Arrange
        Genre genre = Genre.FICTIONAL;
        String author = "John Doe";
        boolean isAvailable = true;
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setBookId(1);
        book.setGenre(genre);
        book.setAuthor(author);
        books.add(book);

        when(bookRepository.findBooksByGenre_Author(eq(genre), eq(author), eq(isAvailable))).thenReturn(books);

        // Act
        List<BookOutputModel> result = bookService.getBooks(null, genre, isAvailable, author);

        // Assert
        verify(bookRepository).findBooksByGenre_Author(eq(genre), eq(author), eq(isAvailable));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());

        BookOutputModel outputModel = result.get(0);
        Assertions.assertEquals(book.getBookId(), outputModel.getBookId());
        Assertions.assertEquals(book.getIsbn(), outputModel.getISBN());
        Assertions.assertEquals(book.getName(), outputModel.getBookName());
        Assertions.assertEquals(book.getAuthor(), outputModel.getAuthor());
    }

    @Test
    void testGetBooks_ByGenre_ReturnsBookOutputModelList() throws NotFoundException, BookException {
        // Arrange
        Genre genre = Genre.FICTIONAL;
        boolean isAvailable = true;
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setBookId(1);
        book.setGenre(genre);
        books.add(book);

        when(bookRepository.findBooksByGenre(eq(genre), eq(isAvailable))).thenReturn(books);

        // Act
        List<BookOutputModel> result = bookService.getBooks(null, genre, isAvailable, null);

        // Assert
        verify(bookRepository).findBooksByGenre(eq(genre), eq(isAvailable));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());

        BookOutputModel outputModel = result.get(0);
        Assertions.assertEquals(book.getBookId(), outputModel.getBookId());
        Assertions.assertEquals(book.getIsbn(), outputModel.getISBN());
        Assertions.assertEquals(book.getName(), outputModel.getBookName());
        Assertions.assertEquals(book.getAuthor(), outputModel.getAuthor());
    }

    @Test
    void testGetBooks_ByAuthor_ReturnsBookOutputModelList() throws NotFoundException, BookException {
        // Arrange
        String author = "John Doe";
        boolean isAvailable = true;
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setBookId(1);
        book.setAuthor(author);
        books.add(book);

        when(bookRepository.findBooksByAuthor(eq(author), eq(isAvailable))).thenReturn(books);

        // Act
        List<BookOutputModel> result = bookService.getBooks(null, null, isAvailable, author);

        // Assert
        verify(bookRepository).findBooksByAuthor(eq(author), eq(isAvailable));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());

        BookOutputModel outputModel = result.get(0);
        Assertions.assertEquals(book.getBookId(), outputModel.getBookId());
        Assertions.assertEquals(book.getIsbn(), outputModel.getISBN());
        Assertions.assertEquals(book.getName(), outputModel.getBookName());
        Assertions.assertEquals(book.getAuthor(), outputModel.getAuthor());
    }

    @Test
    void testGetBooks_ByAvailability_ReturnsBookOutputModelList() throws NotFoundException, BookException {
        // Arrange
        boolean isAvailable = true;
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setBookId(1);
        book.setAvailable(isAvailable);
        books.add(book);

        when(bookRepository.findBooksByAvailability(eq(isAvailable))).thenReturn(books);

        // Act
        List<BookOutputModel> result = bookService.getBooks(null, null, isAvailable, null);

        // Assert
        verify(bookRepository).findBooksByAvailability(eq(isAvailable));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());

        BookOutputModel outputModel = result.get(0);
        Assertions.assertEquals(book.getBookId(), outputModel.getBookId());
        Assertions.assertEquals(book.getIsbn(), outputModel.getISBN());
        Assertions.assertEquals(book.getName(), outputModel.getBookName());
        Assertions.assertEquals(book.getAuthor(), outputModel.getAuthor());
    }

    @Test
    void testGetBooks_NoParameterSpecified_ThrowsNotFoundException() throws NotFoundException, BookException {
        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> bookService.getBooks(null, null, false, null));

        verify(bookRepository, never()).findBooksByISBN(anyString(), anyBoolean());
        verify(bookRepository, never()).findBooksByGenre_Author(any(Genre.class), anyString(), anyBoolean());
        verify(bookRepository, never()).findBooksByGenre(any(Genre.class), anyBoolean());
        verify(bookRepository, never()).findBooksByAuthor(anyString(), anyBoolean());
        verify(bookRepository, never()).findBooksByAvailability(anyBoolean());
    }

    @Test
    void testGetBooks_BookNotFound_ThrowsBookException() throws NotFoundException, BookException {
        // Arrange
        String isbn = "1234567890";
        boolean isAvailable = true;

        when(bookRepository.findBooksByISBN(eq(isbn), eq(isAvailable))).thenReturn(null);

        // Act & Assert
        Assertions.assertThrows(BookException.class, () -> bookService.getBooks(isbn, null, isAvailable, null));

        verify(bookRepository).findBooksByISBN(eq(isbn), eq(isAvailable));
    }
}
