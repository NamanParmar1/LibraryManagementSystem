package com.naman.lms.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.naman.lms.exception.BookException;
import com.naman.lms.exception.MemberException;
import com.naman.lms.exception.NotFoundException;
import com.naman.lms.exception.WrongDateException;
import com.naman.lms.model.BookOutputModel;
import com.naman.lms.model.IssueBooksOutputModel;
import com.naman.lms.model.MemberOutputModel;
import com.naman.lms.serviceImplementation.IssuedBooksServiceImplementation;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class IssuedBooksController {
	
	Logger logger = LoggerFactory.getLogger(IssuedBooksController.class);

    @Autowired
    IssuedBooksServiceImplementation issuedBooksServiceImplementation;


    @GetMapping("/searchmembers_bookId/{bookId}")
    public ResponseEntity<List<MemberOutputModel>> getMembers(@PathVariable(value = "bookId", required = false) Integer bookId) throws NotFoundException, MemberException, BookException {
    	logger.info("Searching Members who have issued bookId:{}",bookId);
        List<MemberOutputModel> List = issuedBooksServiceImplementation.getMembers(bookId);
        logger.info("Search Completed");
        return new ResponseEntity<List<MemberOutputModel>>(List, HttpStatus.OK);

    }

    @GetMapping("/searchbooks_memberId/{cardId}")
    public ResponseEntity<List<BookOutputModel>> getBooks(@PathVariable(value = "cardId", required = false) Integer cardId) throws NotFoundException, MemberException {
    	logger.info("Searching Books issued by MemberId:{}",cardId);
        List<BookOutputModel> List = issuedBooksServiceImplementation.getBooks(cardId);
        logger.info("Search Completed");
        return new ResponseEntity<List<BookOutputModel>>(List, HttpStatus.OK);

    }
    
    @GetMapping("/booksissuedbydate/{date}")
    public ResponseEntity<List<IssueBooksOutputModel>> getBooks(@PathVariable(value = "date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) throws NotFoundException,WrongDateException {
    	logger.info("Searching Books issued on{}",date);
        List<IssueBooksOutputModel> List = issuedBooksServiceImplementation.getBooks(date);
        logger.info("Search Completed");
        return new ResponseEntity<List<IssueBooksOutputModel>>(List, HttpStatus.OK);

    }

}
