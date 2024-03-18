package com.naman.lms.model;

import java.sql.Date;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.naman.lms.entity.Book;
import com.naman.lms.entity.Genre;
import com.naman.lms.entity.Members;
import com.naman.lms.entity.Transaction;
import com.naman.lms.entity.TransactionStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionOutputModel {
	
	private Integer card;
    private int fineAmount;
    private LocalDate returnDate;
    private Integer book;
    private TransactionStatus transactionStatus;
    private LocalDate transactionDate;
    
    
   public  TransactionOutputModel(Transaction t) {
	   this.book=  t.getBook().getBookId();
	   this.card = t.getCard().getCardId();
	   this.fineAmount = t.getFineAmount();
	   this.returnDate = t.getReturnDate();
	   this.transactionStatus = t.getTransactionStatus();
	   this.transactionDate = t.getTransactionDate();
   }

}
