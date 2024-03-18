package com.naman.lms.serviceImplementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naman.lms.entity.Book;
import com.naman.lms.entity.IssuedBooks;
import com.naman.lms.entity.Members;
import com.naman.lms.exception.BookException;
import com.naman.lms.exception.MemberException;
import com.naman.lms.exception.NotFoundException;
import com.naman.lms.model.BookOutputModel;
import com.naman.lms.model.IssueBooksOutputModel;
import com.naman.lms.model.MemberOutputModel;
import com.naman.lms.repository.BookRepository;
import com.naman.lms.repository.IssuedBooksRepository;
import com.naman.lms.repository.MemberRepository;
import com.naman.lms.serviceInterface.IssuedBooksService;

import jakarta.transaction.Transactional;

@Service
public class IssuedBooksServiceImplementation implements IssuedBooksService {

    @Autowired
    IssuedBooksRepository issuedBooksRepository;
    
    @Autowired
    BookRepository bookRepository;
    
    @Autowired
    MemberRepository memberRepository;

    @Transactional
    public List<MemberOutputModel> getMembers(Integer bookId) throws NotFoundException, MemberException, BookException {
    	if(bookRepository.findById(bookId)==null) {
    		throw new BookException("Book Dosen't exist");
    	}
        List<Members> list = issuedBooksRepository.findMembersByBook(bookId);
        if(list.isEmpty()) {
        	throw new MemberException("Not issued to any member");
        }
        List<MemberOutputModel> rlist = new ArrayList<>();
        for (Members m : list) {
            rlist.add(new MemberOutputModel(m));
        }
        return rlist;
    }

    @Transactional
    public List<BookOutputModel> getBooks(Integer cardId) throws NotFoundException, MemberException {
    	if(memberRepository.findById(cardId) ==null){
    		throw new MemberException("member dosen't exist");
    	}
        List<Book> list = issuedBooksRepository.findBooksByCard(cardId);
        if(list.isEmpty()) {
        	throw new NotFoundException("No books issued");
        }
        List<BookOutputModel> rlist = new ArrayList<>();
        for (Book m : list) {
            rlist.add(new BookOutputModel(m));
        }
        return rlist;
        //return null;
    }

    @Transactional
	public List<IssueBooksOutputModel> getBooks(LocalDate date) throws NotFoundException {
		List<IssuedBooks> list = null;
		if(date != null) {
			list = issuedBooksRepository.findBooksByDate(date);
		}else {
			throw new NotFoundException("No date entered");
		}
		if(list.size()==0) {
			throw new NotFoundException("No transactions found");
		}
		List<IssueBooksOutputModel> rlist = new ArrayList<>();
		for(IssuedBooks b : list) {
			rlist.add(new IssueBooksOutputModel(b));
		}
		return rlist;
	}


}
