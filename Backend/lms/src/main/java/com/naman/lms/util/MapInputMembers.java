package com.naman.lms.util;

import com.naman.lms.entity.CardStatus;
import com.naman.lms.entity.Members;
import com.naman.lms.model.MemberInputModel;

public class MapInputMembers {
	
	public static Members map(MemberInputModel memberinputmodel) {
		
		Members member = new Members();
        member.setName(memberinputmodel.getName());
        member.setPassword(memberinputmodel.getPassword());
        member.setPhoneNumber(memberinputmodel.getPhoneNumber());
        member.setEmailId(memberinputmodel.getEmailId());
        member.setCardStatus(CardStatus.ACTIVATED);
		return member;
		
	}

}
