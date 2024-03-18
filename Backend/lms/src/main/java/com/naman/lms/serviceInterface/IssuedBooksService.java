package com.naman.lms.serviceInterface;

import com.naman.lms.exception.BookException;
import com.naman.lms.exception.MemberException;
import com.naman.lms.exception.NotFoundException;
import com.naman.lms.model.BookOutputModel;
import com.naman.lms.model.MemberOutputModel;

import java.util.List;

public interface IssuedBooksService {

    public List<MemberOutputModel> getMembers(Integer bookId) throws NotFoundException, MemberException, BookException;

    public List<BookOutputModel> getBooks(Integer cardId) throws NotFoundException, MemberException;

}
