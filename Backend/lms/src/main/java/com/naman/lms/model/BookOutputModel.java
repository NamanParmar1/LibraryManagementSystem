package com.naman.lms.model;

import com.naman.lms.entity.Book;
import com.naman.lms.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookOutputModel {


    private Integer bookId;
    private String bookName;
    private String ISBN;
    private String author;
    private Integer copiesLeft;
    private Genre genre;


    public BookOutputModel(Book book) {
        this.bookId = book.getBookId();
        this.bookName = book.getName();
        this.author = book.getAuthor();
        this.ISBN = book.getIsbn();
        this.genre = book.getGenre();
		if(book.getMembers()==null) {
			this.copiesLeft=6;
		}else {
			this.copiesLeft=6-book.getMembers().size();
		}
        
    }

}
