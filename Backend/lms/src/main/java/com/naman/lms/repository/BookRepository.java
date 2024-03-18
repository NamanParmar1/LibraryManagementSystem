package com.naman.lms.repository;

import com.naman.lms.entity.Book;
import com.naman.lms.entity.Genre;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Transactional
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Modifying
    @Query("update Book b set b.available=:#{#book?.available} where b.bookId=:#{#book?.bookId}")
    int updateBook(@Param("book") Book book);


    @Query("select b from Book b where b.genre=:genre and b.available=:isAvailable and b.author=:author")
    List<Book> findBooksByGenre_Author(@Param("genre") Genre genre, @Param("author") String author, @Param("isAvailable") boolean isAvailable);

    @Query("select b from Book b where b.genre=:genre and b.available=:isAvailable")
    List<Book> findBooksByGenre(@Param("genre") Genre genre, @Param("isAvailable") boolean isAvailable);

    @Query("select b from Book b where b.available=:isAvailable and b.author=:author")
    List<Book> findBooksByAuthor(@Param("author") String author, @Param("isAvailable") boolean isAvailable);

    @Query("select b from Book b where b.available=:isAvailable")
    List<Book> findBooksByAvailability(@Param("isAvailable") boolean isAvailable);

    @Query("select b from Book b where b.bookId=:id")
    Book findBooksById(@Param("id") int id);

    @Query("select b from Book b where b.isbn=:isbn and b.available=:isAvailable")
    Book findBooksByISBN(@Param("isbn") String isbn, @Param("isAvailable") boolean isAvailable);

    @Query("select b.bookId from Book b where b.isbn=:isbn")
    Integer findBookIdByISBN(@Param("isbn") String isbn);

}
