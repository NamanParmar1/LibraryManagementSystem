package com.naman.lms.serviceImplementation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naman.lms.entity.Book;
import com.naman.lms.entity.CardStatus;
import com.naman.lms.entity.IssuedBooks;
import com.naman.lms.entity.Members;
import com.naman.lms.entity.Transaction;
import com.naman.lms.entity.TransactionStatus;
import com.naman.lms.exception.BookException;
import com.naman.lms.exception.MemberException;
import com.naman.lms.exception.NotFoundException;
import com.naman.lms.model.TransactionOutputModel;
import com.naman.lms.repository.BookRepository;
import com.naman.lms.repository.IssuedBooksRepository;
import com.naman.lms.repository.MemberRepository;
import com.naman.lms.repository.TransactionRepository;
import com.naman.lms.serviceInterface.TransactionService;

import jakarta.transaction.Transactional;

@Service
public class TransactionServiceImplementation implements TransactionService {

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	BookRepository bookRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	IssuedBooksRepository issuedBooksRepository;

	int max_allowed_books = 4;

	int max_days_allowed = 1;

	int fine_per_day = 50;

	int no_of_copies = 6;

	@Transactional
	public Book issueBooks(int cardId, int bookId) throws BookException, NotFoundException, MemberException {

		Book book = bookRepository.findBooksById(bookId);

		if (book == null || !book.isAvailable()) {
			throw new BookException("Book is either unavailable or not present!!");
		}

		Members member = memberRepository.findById(cardId).orElse(null);

		if (member == null || member.getCardStatus() == CardStatus.DEACTIVATED) {
			throw new MemberException("Card is invalid!!");
		}
		if (member.getBooks() != null && member.getBooks().size() == max_allowed_books) {
			throw new BookException("Book limit reached for this card!!");
		}
		// list of all member who issued this book
		List<Integer> members;

		if (book.getMembers() == null) {
			members = new ArrayList<>();
		} else {
			members = book.getMembers();
		}

		if (members.contains(cardId)) {
			throw new BookException("Book is already issued!!");
		}

		members.add(member.getCardId());
		book.setMembers(members);

		book.setAvailable(members.size() < no_of_copies);

		// list of all books a member has
		List<Integer> books;

		if (member.getBooks() == null) {
			books = new ArrayList<>();
		} else {
			books = member.getBooks();
		}

		books.add(book.getBookId());
		member.setBooks(books);

		memberRepository.save(member);

		bookRepository.save(book);

		IssuedBooks ctransaction = new IssuedBooks();
		ctransaction.setBook(book);
		ctransaction.setCard(member);
		issuedBooksRepository.save(ctransaction);

		Transaction tran = new Transaction();
		tran.setBook(book);
		tran.setCard(member);
		tran.setTransactionStatus(TransactionStatus.ISSUED);
		transactionRepository.save(tran);

		return book;
	}

	@Transactional
	public TransactionOutputModel returnBooks(int cardId, int bookId)
			throws NotFoundException, MemberException, BookException {
		Book book = bookRepository.findBooksById(bookId);

		if (book == null) {
			throw new BookException("Book dosen't exist");
		}

		Members member = memberRepository.findById(cardId).orElse(null);

		if (member == null || member.getCardStatus() == CardStatus.DEACTIVATED) {
			throw new MemberException("Card is invalid!!");
		}

		IssuedBooks transaction = issuedBooksRepository.findTransactionByCard_Book(cardId, bookId);

		if (transaction == null) {
			throw new NotFoundException("Book is not issued");
		}

		List<Transaction> tranlist = transactionRepository.findByCard_Book(cardId, bookId, TransactionStatus.ISSUED);

		if (tranlist.size() == 0 || tranlist.size() > 1) {
			throw new BookException("More than one book issued to same card");
		}
		Transaction tran = tranlist.get(0);

		LocalDate issueDate = transaction.getTransactionDate();
		LocalDateTime issueDateTime = issueDate.atStartOfDay();
		long issueTime = Math.abs(ChronoUnit.MILLIS.between(issueDateTime, LocalDateTime.now()));

		long number_of_days_passed = TimeUnit.DAYS.convert(issueTime, TimeUnit.MILLISECONDS);

		int fine = 0;

		if (number_of_days_passed > max_days_allowed) {
			fine = (int) Math.abs(number_of_days_passed - max_days_allowed) * fine_per_day;
		}

		List<Integer> cards = book.getMembers();
		cards.remove(member.getCardId());
		book.setMembers(cards);

		List<Integer> books = member.getBooks();
		books.remove(book.getBookId());
		member.setBooks(books);

		memberRepository.save(member);

		book.setAvailable(cards.size() < no_of_copies);

		issuedBooksRepository.delete(transaction);

		bookRepository.updateBook(book);
		tran.setFineAmount(fine);
		tran.setReturnDate(LocalDate.now());
		tran.setTransactionStatus(TransactionStatus.RETURNED);
		transactionRepository.save(tran);

		return new TransactionOutputModel(tran);

	}

	@Transactional
	public List<TransactionOutputModel> searchtransaction(Integer cardId, Integer bookId, TransactionStatus status)
			throws NotFoundException {
		List<Transaction> list = null;
		if (status != null) {
			if (cardId != null && bookId != null) {
				list = transactionRepository.findByCard_Book((int) cardId, bookId, status);
				if (list.isEmpty()) {
					throw new NotFoundException("No Transaction Found");
				}
			} else if (cardId != null) {
				list = transactionRepository.findByCard(cardId, status);
				if (list.isEmpty()) {
					throw new NotFoundException("No Transaction Found");
				}
			} else if (bookId != null) {
				list = transactionRepository.findByBook(bookId, status);
				if (list.isEmpty()) {
					throw new NotFoundException("No Transaction Found");
				}
			} else {
				list = transactionRepository.findBystatus(status);
				if (list.isEmpty()) {
					throw new NotFoundException("No Transaction Found");
				}
			}
		} else {
			if (cardId != null && bookId != null) {
				list = transactionRepository.findByCard_Book((int) cardId, bookId);
				if (list.isEmpty()) {
					throw new NotFoundException("No Transaction Found");
				}

			} else if (cardId != null) {
				list = transactionRepository.findByCard(cardId);
				if (list.isEmpty()) {
					throw new NotFoundException("No Transaction Found");
				}
			} else if (bookId != null) {
				list = transactionRepository.findByBook(bookId);
				if (list.isEmpty()) {
					throw new NotFoundException("No Transaction Found");
				}
			} else {
				list = transactionRepository.findAll();
				if (list.isEmpty()) {
					throw new NotFoundException("No Transaction Found");
				}
				//throw new NotFoundException("Please enter parameters to search");
			}
		}
		List<TransactionOutputModel> rlist = new ArrayList<>();
		for (Transaction t : list) {
			rlist.add(new TransactionOutputModel(t));
		}
		return rlist;
	}
 
	@Transactional
	public List<TransactionOutputModel> getBooks(LocalDate date, TransactionStatus status) throws NotFoundException {
		List<Transaction> list = null;
		if (date != null && status != null) {
			list = transactionRepository.findByDate(date, status);
		} else if (date != null) {
			list = transactionRepository.findByDate(date);
		}else {
			throw new NotFoundException("No date entered");
		}
		if(list == null || list.isEmpty()) {
			throw new NotFoundException("No transactions found");
		}
		List<TransactionOutputModel> rlist = new ArrayList<>();
		for (Transaction t : list) {
			rlist.add(new TransactionOutputModel(t));
		}
		return rlist;
	}

}