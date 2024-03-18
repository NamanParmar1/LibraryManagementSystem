package com.naman.lms.serviceInterface;

import com.naman.lms.entity.CardStatus;
import com.naman.lms.entity.Members;
import com.naman.lms.exception.BookException;
import com.naman.lms.exception.MemberException;
import com.naman.lms.exception.NotFoundException;
import com.naman.lms.exception.ValidationException;
import com.naman.lms.model.MemberInputModel;
import com.naman.lms.model.MemberOutputModel;

import java.util.List;

public interface MemberService {

    public Members createMember(MemberInputModel memberInputModel) throws ValidationException, NotFoundException;

    public List<MemberOutputModel> getMember(Integer id, String email, String phone) throws NotFoundException, MemberException;

    public Members activate_deactivateMember(int card_id) throws MemberException;

    public List<MemberOutputModel> getMember(CardStatus cd) throws NotFoundException;


}
