package com.naman.lms.util;

import com.naman.lms.entity.Book;
import com.naman.lms.model.BookInputModel;

public class MapInputBooks {
	
	public static Book map(BookInputModel bookinputmodel) {
		
		Book book = new Book();
        book.setAuthor(bookinputmodel.getAuthor());
        book.setGenre(bookinputmodel.getGenre());
        book.setIsbn(bookinputmodel.getIsbn());
        book.setName(bookinputmodel.getName());
        book.setAvailable(true);
		return book;
		
	}

}
