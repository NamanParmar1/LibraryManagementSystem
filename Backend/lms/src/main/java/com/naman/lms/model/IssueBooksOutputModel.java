package com.naman.lms.model;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.naman.lms.entity.Book;
import com.naman.lms.entity.IssuedBooks;
import com.naman.lms.entity.Members;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueBooksOutputModel {
	
	
	    private int loanId;
	    private Integer card;
	    private String memberName; 
	    private Integer book;
	    private String bookName;
	    
	    public IssueBooksOutputModel(IssuedBooks b) {
	    	this.loanId = b.getLoanId();
	    	this.book= b.getBook().getBookId();
	    	this.bookName = b.getBook().getName();
	    	this.card = b.getCard().getCardId();
	    	this.memberName = b.getCard().getName();
	    }

}
