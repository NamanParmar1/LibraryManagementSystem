package com.naman.lms.serviceInterface;

import com.naman.lms.entity.Genre;
import com.naman.lms.exception.BookException;
import com.naman.lms.exception.NotFoundException;
import com.naman.lms.model.BookInputModel;
import com.naman.lms.model.BookOutputModel;

import java.util.List;

public interface BookService {

    public BookOutputModel createBook(BookInputModel bookinputmodel) throws BookException;

    public BookOutputModel deleteBook(String Isbn, Integer bookId) throws BookException;

    public List<BookOutputModel> getBooks(String isbn, Genre genre, boolean isAvailable, String author) throws NotFoundException, BookException;
}
