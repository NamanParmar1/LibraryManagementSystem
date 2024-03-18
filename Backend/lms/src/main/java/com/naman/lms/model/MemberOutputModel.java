package com.naman.lms.model;

import com.naman.lms.entity.CardStatus;
import com.naman.lms.entity.Members;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberOutputModel {

	private Integer cardId;
    private String name;
    private String emailId;
    private String phoneNumber;
    private int booksIssued;
    private CardStatus cardStatus;


    public MemberOutputModel(Members member) {
    	this.cardId = member.getCardId();
        this.name = member.getName();
        this.emailId = member.getEmailId();
        this.phoneNumber = member.getPhoneNumber();
        if (member.getBooks() == null) {
            this.booksIssued = 0;
        } else {
            this.booksIssued = member.getBooks().size();
        }

        this.cardStatus = member.getCardStatus();
    }
}
