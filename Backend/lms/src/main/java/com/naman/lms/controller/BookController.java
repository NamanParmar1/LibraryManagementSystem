package com.naman.lms.controller;

import com.naman.lms.entity.Genre;
import com.naman.lms.exception.BookException;
import com.naman.lms.exception.NotFoundException;
import com.naman.lms.model.BookInputModel;
import com.naman.lms.model.BookOutputModel;
import com.naman.lms.serviceImplementation.BookServiceImplementation;
import com.naman.lms.serviceImplementation.MemberServiceImplementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {
	Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    BookServiceImplementation bookServiceImplementation;
   

    @PostMapping("/addBook")
    public ResponseEntity<BookOutputModel> createBook(@RequestBody BookInputModel bookInputModel) throws BookException {
    	logger.info("Adding a Book");
        BookOutputModel book = bookServiceImplementation.createBook(bookInputModel);
        logger.info("Book Added");
        return new ResponseEntity<BookOutputModel>(book, HttpStatus.CREATED);

    }


    @GetMapping("/searchBooks")
    public ResponseEntity<List<BookOutputModel>> getBooks(@RequestParam(value = "genre", required = false) Genre genre,
                                                          @RequestParam(value = "available", required = false,defaultValue  ="true") boolean available,
                                                          @RequestParam(value = "author", required = false) String author,
                                                          @RequestParam(value = "isbn", required = false) String isbn) throws NotFoundException, BookException {
    	logger.info("Searching Book");
        List<BookOutputModel> bookList = bookServiceImplementation.getBooks(isbn, genre, available, author);
        logger.info("Search Completed");
        return new ResponseEntity<List<BookOutputModel>>(bookList, HttpStatus.OK);

    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<BookOutputModel> deleteBook(@RequestParam(value = "isbn", required = false) String isbn,
                                                      @RequestParam(value = "book_id", required = false) Integer bookId) throws BookException {
    	logger.info("Deleting a Book");
        BookOutputModel book = bookServiceImplementation.deleteBook(isbn, bookId);
        logger.info("Book Successfully searched");
        return new ResponseEntity<BookOutputModel>(book, HttpStatus.ACCEPTED);

    }

}
