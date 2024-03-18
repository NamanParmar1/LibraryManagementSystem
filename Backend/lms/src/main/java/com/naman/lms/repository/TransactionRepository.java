package com.naman.lms.repository;

import com.naman.lms.entity.Transaction;
import com.naman.lms.entity.TransactionStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Transactional
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	@Query("select t from Transaction t where t.card.id=:card_id and t.book.id=:book_id and t.transactionStatus=:status")
	public List<Transaction> findByCard_Book(@Param("card_id") int card_id, @Param("book_id") int book_id,
			@Param("status") TransactionStatus status
	);
	
	@Query("select t from Transaction t where t.card.id=:card_id and t.book.id=:book_id")
	public List<Transaction> findByCard_Book(@Param("card_id") int card_id, @Param("book_id") int book_id
			
	);

	@Query("select t from Transaction t where t.card.id=:card_id and t.transactionStatus=:status")
	public List<Transaction> findByCard(@Param("card_id") int card_id,@Param("status") TransactionStatus status

	);
	
	@Query("select t from Transaction t where t.card.id=:card_id")
	public List<Transaction> findByCard(@Param("card_id") int card_id

	);

	@Query("select t from Transaction t where t.book.id=:book_id and t.transactionStatus=:status")
	public List<Transaction> findByBook(@Param("book_id") int book_id,@Param("status") TransactionStatus status

	);
	@Query("select t from Transaction t where t.book.id=:book_id")
	public List<Transaction> findByBook(@Param("book_id") int book_id

	);
	
	@Query("select t from Transaction t where (t.transactionDate=:date or t.returnDate=:date) and t.transactionStatus=:status")
	public List<Transaction> findByDate(@Param("date") LocalDate date,@Param("status") TransactionStatus status

	);
	@Query("select t from Transaction t where t.transactionDate=:date Or t.returnDate=:date")
	public List<Transaction> findByDate(@Param("date") LocalDate date

	);
	@Query("select t from Transaction t where t.transactionStatus=:status")
	public List<Transaction> findBystatus(@Param("status") TransactionStatus status

	);

}
