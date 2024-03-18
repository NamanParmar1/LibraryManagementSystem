package com.naman.lms.serviceImplementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naman.lms.entity.Book;
import com.naman.lms.entity.Genre;
import com.naman.lms.exception.BookException;
import com.naman.lms.exception.NotFoundException;
import com.naman.lms.model.BookInputModel;
import com.naman.lms.model.BookOutputModel;
import com.naman.lms.repository.BookRepository;
import com.naman.lms.repository.IssuedBooksRepository;
import com.naman.lms.serviceInterface.BookService;
import com.naman.lms.util.MapInputBooks;

import jakarta.transaction.Transactional;

@Service
public class BookServiceImplementation implements BookService {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	IssuedBooksRepository issuedbooksRepository;

	public BookOutputModel createBook(BookInputModel bookinputmodel) throws BookException {
		Book book = MapInputBooks.map(bookinputmodel);
		if (bookRepository.findBooksByISBN(bookinputmodel.getIsbn(), true) != null) {
			throw new BookException("Book with this ISBN is already present");
		}
		return new BookOutputModel(bookRepository.save(book));
	}

	@Transactional
	public BookOutputModel deleteBook(String Isbn, Integer bookId) throws BookException {
		if (bookId == null) {
			if (Isbn == null) {
				throw new BookException("Please give input");
			}
			if (bookRepository.findBookIdByISBN(Isbn) == null) {
				throw new BookException("Book doesn't exist");
			}
			bookId = bookRepository.findBookIdByISBN(Isbn);
			
		} else if (bookRepository.findBooksById(bookId) == null) {
			throw new BookException("Book doesn't exist");
		}
		if (!issuedbooksRepository.findMembersByBook(bookId).isEmpty()) {
			throw new BookException("This book is not yet returned");
		}
		BookOutputModel book = new BookOutputModel(bookRepository.findBooksById(bookId));
		bookRepository.deleteById(bookId);
		return book;

	}

	@Transactional
	public List<BookOutputModel> getBooks(String isbn, Genre genre, boolean isAvailable, String author)
			throws NotFoundException, BookException {
		List<Book> book;

		if (isbn != null) {
			book = new ArrayList<>();
			Book obj = bookRepository.findBooksByISBN(isbn, isAvailable);
			if (obj != null) {
				book.add(obj);
			} else {
				throw new BookException("The Book with given ISBN dosen't exist.");
			}

		} else if (genre != null && author != null) {

			book = bookRepository.findBooksByGenre_Author(genre, author, isAvailable);
			if (book.isEmpty()) {
				throw new BookException("The Book with given Genre and Author dosen't exist.");
			}

		} else if (genre != null) {

			book = bookRepository.findBooksByGenre(genre, isAvailable);

			if (book.isEmpty()) {
				throw new BookException("The Book with given Genre dosen't exist.");
			}

		} else if (author != null) {

			book = bookRepository.findBooksByAuthor(author, isAvailable);

			if (book.isEmpty()) {
				throw new BookException("The Book with given Author dosen't exist.");
			}

		} else if (isAvailable || !isAvailable) {
			book = bookRepository.findBooksByAvailability(isAvailable);
			if (book.isEmpty()) {
				throw new BookException("No such book exists");
			}

		} else {
			throw new NotFoundException("NO PARAMETER SPECIFIED");
		}

		List<BookOutputModel> rbook = new ArrayList<>();

		for (Book b : book) {
			rbook.add(new BookOutputModel(b));
		}

		return rbook;
	}

}
