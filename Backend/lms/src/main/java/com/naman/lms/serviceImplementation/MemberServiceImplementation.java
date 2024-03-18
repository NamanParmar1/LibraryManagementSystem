package com.naman.lms.serviceImplementation;

import com.naman.lms.entity.CardStatus;
import com.naman.lms.entity.Members;
import com.naman.lms.exception.BookException;
import com.naman.lms.exception.MemberException;
import com.naman.lms.exception.NotFoundException;
import com.naman.lms.exception.ValidationException;
import com.naman.lms.model.MemberInputModel;
import com.naman.lms.model.MemberOutputModel;
import com.naman.lms.repository.MemberRepository;
import com.naman.lms.serviceInterface.MemberService;
import com.naman.lms.util.MapInputMembers;
import com.naman.lms.validation.MemberValidation;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImplementation implements MemberService {

    

    @Autowired
    MemberRepository memberRepository;
    
    @Autowired
    MemberValidation memberValidation;

    @Transactional
    public Members createMember(MemberInputModel memberInputModel) throws ValidationException, NotFoundException {
    	
    	if(!memberValidation.checkName(memberInputModel.getName())) {
    		throw new ValidationException("Name Validation Error");
    	}
    	if(!memberValidation.checkEmail(memberInputModel.getEmailId())) {
    		throw new ValidationException("Email Validation Error");
    	}
    	if(!memberValidation.validatePassword(memberInputModel.getPassword())) {
    		throw new ValidationException("Password Validation Error");
    	}
    	if(!memberRepository.find_by_mail(memberInputModel.getEmailId()).isEmpty()) {
    		throw new NotFoundException("This email id is already registered");
    	}

        Members member = MapInputMembers.map(memberInputModel);

        memberRepository.save(member);
        return member;
    }

   
    @Transactional
    public List<MemberOutputModel> getMember(Integer id, String email, String phone) throws NotFoundException,MemberException {

        List<Members> member = null;
        if (id != null) {
            member = memberRepository.find_by_id(id);
            if (member.isEmpty()) {
                throw new MemberException("No member found with this card");
            }
            
        } else if (email != null && phone != null) {
            member = memberRepository.find_by_mail_phone(email, phone);
            if (member.isEmpty()) {
                throw new MemberException("No member found with this email Or phone");
            }
           
        } else if (email != null) {
            member = (memberRepository.find_by_mail(email));
            
            if (member.isEmpty()) {
                throw new MemberException("No member found with this email");
            }
           
        } else if (phone != null) {
            member = memberRepository.find_by_phone(phone);
            if (member.isEmpty()) {
                throw new MemberException("No member found with this phone");
            }
        }else {
        	throw new NotFoundException("No input given");
        }
        List<MemberOutputModel> list = new ArrayList<>();
        for(Members m : member) {
        	list.add(new MemberOutputModel(m));
        }
        return list ;
    }


    @Transactional
    public Members activate_deactivateMember(int card_id) throws MemberException {
        Members m = memberRepository.findById(card_id).orElse(null);
        
        if(m ==null) {
        	throw new MemberException("No Member Found with given cardid");
        }
        if(m.getBooks()!=null && m.getBooks().size()>0) {
        	throw new MemberException("Member has some books currently issued");
        }
        CardStatus c = m.getCardStatus();
        if (c == CardStatus.ACTIVATED) {
            memberRepository.setCard(card_id, CardStatus.DEACTIVATED);
        } else {
            memberRepository.setCard(card_id, CardStatus.ACTIVATED);
        }
        
        return m;


    }

    @Transactional
    public List<MemberOutputModel> getMember(CardStatus cd) throws NotFoundException {
        List<Members> mlist = memberRepository.find_by_status(cd);
        if(mlist.isEmpty()) {
        	throw new NotFoundException("NO MEMBER FOUND");
        }
        List<MemberOutputModel> list = new ArrayList<>();

        for (Members m : mlist) {
            MemberOutputModel mo = new MemberOutputModel(m);
            list.add(mo);

        }

        return list;
    }


	public Members updateMember(MemberInputModel memberInputModel) throws NotFoundException, ValidationException {
		if(!memberValidation.checkName(memberInputModel.getName())) {
    		throw new ValidationException("Name Validation Error");
    	}
    	if(!memberValidation.checkEmail(memberInputModel.getEmailId())) {
    		throw new ValidationException("Email Validation Error");
    	}
//    	if(!memberValidation.validatePassword(memberInputModel.getPassword())) {
//    		throw new ValidationException("Password Validation Error");
//    	}
    	if(memberRepository.find_by_mail(memberInputModel.getEmailId()).isEmpty()) {
    		throw new NotFoundException("This email id is not registered");
    	}
    	List<Members> list = memberRepository.find_by_mail(memberInputModel.getEmailId());
    	Members m = list.get(0);
    	System.out.println(m.getPassword()+" "+ memberInputModel.getPassword());
    	if(m.getPassword().equals(memberInputModel.getPassword())) {
    		m.setEmailId(memberInputModel.getEmailId());
        	m.setName(memberInputModel.getName());
        	m.setPhoneNumber(memberInputModel.getPhoneNumber());
        	m.setPassword(memberInputModel.getPassword());
        	memberRepository.save(m);
    	}else {
    		throw new ValidationException("WRONG PASSWORD");
    	}
    	
		return m;
	}

}