package com.naman.lms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class IssuedBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int loanId;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Members card;


    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;


    @CreationTimestamp
    private LocalDate transactionDate;


	

}
