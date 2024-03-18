package com.naman.lms.serviceInterface;

import com.naman.lms.entity.Book;
import com.naman.lms.exception.BookException;
import com.naman.lms.exception.MemberException;
import com.naman.lms.exception.NotFoundException;
import com.naman.lms.model.TransactionOutputModel;

public interface TransactionService {

    public Book issueBooks(int cardId, int bookId) throws BookException, NotFoundException, MemberException;

    public TransactionOutputModel returnBooks(int cardId, int bookId) throws BookException, NotFoundException, MemberException;

}
