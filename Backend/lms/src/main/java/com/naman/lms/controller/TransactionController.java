package com.naman.lms.controller;

import java.sql.Date;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.naman.lms.entity.Book;
import com.naman.lms.entity.Transaction;
import com.naman.lms.entity.TransactionStatus;
import com.naman.lms.exception.NotFoundException;
import com.naman.lms.model.TransactionOutputModel;
import com.naman.lms.serviceImplementation.TransactionServiceImplementation;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {
	
	Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	TransactionServiceImplementation transactionServiceImplementation;

	// what i need ideally is card_id and book_id

	@PostMapping("/issueBook/{cardId}/{bookId}")
	public ResponseEntity<Book> issueBook(@PathVariable(value = "cardId") int cardId,
			@PathVariable("bookId") int bookId) throws Exception {
		logger.info("Issuing bookId {} to memberId {}",bookId,cardId);
		Book book = transactionServiceImplementation.issueBooks(cardId, bookId);
		logger.info("Book Successfully issued");
		return new ResponseEntity<Book>(book, HttpStatus.OK);

	}

	@PostMapping("/returnBook/{cardId}/{bookId}")
	public ResponseEntity<TransactionOutputModel> returnBook(@PathVariable("cardId") int cardId, @PathVariable("bookId") int bookId)
			throws Exception {
		logger.info("Returning bookId {} by memberId {}",bookId,cardId);
		TransactionOutputModel book = transactionServiceImplementation.returnBooks(cardId, bookId);
		logger.info("Book Successfully returned");
		return new ResponseEntity<TransactionOutputModel>(book, HttpStatus.OK);

	}

	@GetMapping("/searchtransaction/")
	public ResponseEntity<List<TransactionOutputModel>> search(
			@RequestParam(value = "cardId", required = false) Integer cardId,
			@RequestParam(value = "bookId", required = false) Integer bookId,
			@RequestParam(value = "status", required = false) TransactionStatus status) throws Exception {
		logger.info("Searching Transaction");
		List<TransactionOutputModel> list = transactionServiceImplementation.searchtransaction(cardId, bookId,status);
		logger.info("Book Successfully searched");
		return new ResponseEntity<List<TransactionOutputModel>>(list, HttpStatus.OK);

	}

	@GetMapping("/searchtransactionbydate/")
	public ResponseEntity<List<TransactionOutputModel>> search(@RequestParam(value = "date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
			@RequestParam(value = "status", required = false) TransactionStatus status)
			throws NotFoundException {
		logger.info("Searching Transaction by date");
		List<TransactionOutputModel> List = transactionServiceImplementation.getBooks(date,status);
		logger.info("Book Successfully searched");
		return new ResponseEntity<List<TransactionOutputModel>>(List, HttpStatus.OK);

	}

}