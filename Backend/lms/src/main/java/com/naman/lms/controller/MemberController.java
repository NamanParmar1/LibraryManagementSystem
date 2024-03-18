package com.naman.lms.controller;

import com.naman.lms.entity.CardStatus;
import com.naman.lms.entity.Members;
import com.naman.lms.exception.BookException;
import com.naman.lms.exception.MemberException;
import com.naman.lms.exception.NotFoundException;
import com.naman.lms.exception.ValidationException;
import com.naman.lms.model.MemberInputModel;
import com.naman.lms.model.MemberOutputModel;
import com.naman.lms.serviceImplementation.MemberServiceImplementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {
    @Autowired
    MemberServiceImplementation memberServiceImplementation;
    Logger logger = LoggerFactory.getLogger(MemberController.class);
    //@PutMapping("/updateMember")


    @PostMapping("/addMember")
    public ResponseEntity<Members> createMember(@RequestBody MemberInputModel memberInputModel) throws ValidationException, NotFoundException {
    	logger.info("Adding member");
        Members member = memberServiceImplementation.createMember(memberInputModel);
        logger.info("member added");
        return new ResponseEntity<Members>(member, HttpStatus.CREATED);

    }
    @PutMapping("/updateMember")
    public ResponseEntity<Members> updateMember(@RequestBody MemberInputModel memberInputModel) throws ValidationException, NotFoundException {
    	logger.info("Updating member");
        Members member = memberServiceImplementation.updateMember(memberInputModel);
        logger.info("member updated");
        return new ResponseEntity<Members>(member, HttpStatus.CREATED);

    }


    @PutMapping("/activate_deactivateMember/{id}")
    public ResponseEntity activate_deactivate(@PathVariable("id") Integer id) throws BookException, MemberException {
    	logger.info("Activating-Deativating Card");
        memberServiceImplementation.activate_deactivateMember(id);
        logger.info("Operation Successfull");
        return new ResponseEntity("Operation Successfull", HttpStatus.OK);
    }


    @GetMapping("/searchmember")
    ResponseEntity<List<MemberOutputModel>> getMembers(@RequestParam(value = "email", required = false) String email,
                                                        @RequestParam(value = "phone", required = false) String phone,
                                                        @RequestParam(value = "id", required = false) Integer id
    ) throws NotFoundException, MemberException {
    	logger.info("Searching member");
    	List<MemberOutputModel> list = memberServiceImplementation.getMember(id, email, phone);
    	logger.info("Search Completed");
    	return new ResponseEntity<List<MemberOutputModel>>(list, HttpStatus.OK);
    }


    @GetMapping("/allmembers/{CardStatus}")
    public ResponseEntity<List<MemberOutputModel>> getMember(@PathVariable(value = "CardStatus", required = false) CardStatus status) throws NotFoundException {
    	logger.info("Searching member by CardStatus");
        List<MemberOutputModel> list = memberServiceImplementation.getMember(status);
        logger.info("Search Completed");
        return new ResponseEntity<List<MemberOutputModel>>(list, HttpStatus.OK);
    }


}
