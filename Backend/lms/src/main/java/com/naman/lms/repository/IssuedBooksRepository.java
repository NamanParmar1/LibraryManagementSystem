package com.naman.lms.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.naman.lms.entity.Book;
import com.naman.lms.entity.IssuedBooks;
import com.naman.lms.entity.Members;


@Repository
public interface IssuedBooksRepository extends JpaRepository<IssuedBooks, Integer> {

    @Query("select b from IssuedBooks b where b.card.cardId=:cardId and b.book.bookId=:bookId")
    public IssuedBooks findTransactionByCard_Book(@Param("cardId") int cardId, @Param("bookId") int bookId);


    @Query("select b.card from IssuedBooks b where b.book.bookId=:bookId")
    public List<Members> findMembersByBook(@Param("bookId") int bookId);

    @Query("select b.book from IssuedBooks b where b.card.cardId=:cardId")
    public List<Book> findBooksByCard(@Param("cardId") int cardId);
    
    @Query("select b from IssuedBooks b where b.transactionDate=:date")
    public List<IssuedBooks> findBooksByDate(@Param("date") LocalDate date);


}


