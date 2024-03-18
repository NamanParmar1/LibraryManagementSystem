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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MemberControllerTest {

    @Mock
    private MemberServiceImplementation memberServiceImplementation;

    @InjectMocks
    private MemberController memberController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateMember_Success() throws ValidationException, NotFoundException {
        // Prepare
        MemberInputModel memberInputModel = new MemberInputModel();
        Members member = new Members();
        when(memberServiceImplementation.createMember(memberInputModel)).thenReturn(member);

        // Execute
        ResponseEntity<Members> response = memberController.createMember(memberInputModel);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(member, response.getBody());
        verify(memberServiceImplementation, times(1)).createMember(memberInputModel);
    }

    @Test
    public void testActivateDeactivateMember_Success() throws BookException, MemberException {
        // Prepare
        int memberId = 1;

        // Execute
        ResponseEntity response = memberController.activate_deactivate(memberId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Operation Successfull", response.getBody());
        verify(memberServiceImplementation, times(1)).activate_deactivateMember(memberId);
    }

    @Test
    public void testGetMembers_Success() throws NotFoundException, MemberException {
        // Prepare
        String email = "test@example.com";
        String phone = "1234567890";
        Integer id = 1;
        List<MemberOutputModel> memberList = new ArrayList<>();
        when(memberServiceImplementation.getMember(id, email, phone)).thenReturn(memberList);

        // Execute
        ResponseEntity<List<MemberOutputModel>> response =
                memberController.getMembers(email, phone, id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(memberList, response.getBody());
        verify(memberServiceImplementation, times(1)).getMember(id, email, phone);
    }

    @Test
    public void testGetMemberByStatus_Success() throws NotFoundException {
        // Prepare
        CardStatus cardStatus = CardStatus.ACTIVATED;
        List<MemberOutputModel> memberList = new ArrayList<>();
        when(memberServiceImplementation.getMember(cardStatus)).thenReturn(memberList);

        // Execute
        ResponseEntity<List<MemberOutputModel>> response =
                memberController.getMember(cardStatus);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(memberList, response.getBody());
        verify(memberServiceImplementation, times(1)).getMember(cardStatus);
    }
}
