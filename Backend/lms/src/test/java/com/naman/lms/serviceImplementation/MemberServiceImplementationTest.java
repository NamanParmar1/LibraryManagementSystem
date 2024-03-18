package com.naman.lms.serviceImplementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.naman.lms.entity.Book;
import com.naman.lms.entity.CardStatus;
import com.naman.lms.entity.Members;
import com.naman.lms.exception.BookException;
import com.naman.lms.exception.MemberException;
import com.naman.lms.exception.NotFoundException;
import com.naman.lms.exception.ValidationException;
import com.naman.lms.model.MemberInputModel;
import com.naman.lms.model.MemberOutputModel;
import com.naman.lms.repository.MemberRepository;
import com.naman.lms.validation.MemberValidation;

class MemberServiceImplementationTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberValidation memberValidation;

    @InjectMocks
    private MemberServiceImplementation memberServiceImplementation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMember_ValidInputModel_ShouldCreateMember() throws ValidationException, NotFoundException {
        // Arrange
        MemberInputModel inputModel = new MemberInputModel();
        inputModel.setName("John Doe");
        inputModel.setEmailId("john@example.com");
        inputModel.setPhoneNumber("1234567890");
        inputModel.setPassword("password123");

        Members expectedMember = new Members();
        expectedMember.setName("John Doe");
        expectedMember.setEmailId("john@example.com");
        expectedMember.setPhoneNumber("1234567890");
        expectedMember.setPassword("password123");
    

        when(memberValidation.checkName("John Doe")).thenReturn(true);
        when(memberValidation.checkEmail("john@example.com")).thenReturn(true);
        when(memberValidation.validatePassword("password123")).thenReturn(true);
        when(memberRepository.find_by_mail("john@example.com")).thenReturn(null);
        when(memberRepository.save(any(Members.class))).thenReturn(expectedMember);

        // Act
        Members createdMember = memberServiceImplementation.createMember(inputModel);

        // Assert
        assertNotNull(createdMember);
        assertEquals(expectedMember.getEmailId(), createdMember.getEmailId());
        verify(memberValidation).checkName("John Doe");
        verify(memberValidation).checkEmail("john@example.com");
        verify(memberValidation).validatePassword("password123");
        verify(memberRepository).find_by_mail("john@example.com");
        verify(memberRepository).save(any(Members.class));
    }

    @Test
    void createMember_InvalidName_ShouldThrowValidationException() throws ValidationException, NotFoundException {
        // Arrange
        MemberInputModel inputModel = new MemberInputModel();
        inputModel.setName("");
        inputModel.setEmailId("john@example.com");
        inputModel.setPassword("password123");

        when(memberValidation.checkName("")).thenReturn(false);

        // Act & Assert
        assertThrows(ValidationException.class, () -> memberServiceImplementation.createMember(inputModel));
        verify(memberValidation).checkName("");
        verify(memberRepository, never()).find_by_mail(anyString());
        verify(memberRepository, never()).save(any(Members.class));
    }

    @Test
    void createMember_InvalidEmail_ShouldThrowValidationException() throws ValidationException, NotFoundException {
        // Arrange
        MemberInputModel inputModel = new MemberInputModel();
        inputModel.setName("John Doe");
        inputModel.setEmailId("invalid_email");
        inputModel.setPassword("password123");

        when(memberValidation.checkName("John Doe")).thenReturn(true);
        when(memberValidation.checkEmail("invalid_email")).thenReturn(false);

        // Act & Assert
        assertThrows(ValidationException.class, () -> memberServiceImplementation.createMember(inputModel));
        verify(memberValidation).checkName("John Doe");
        verify(memberValidation).checkEmail("invalid_email");
        verify(memberRepository, never()).find_by_mail(anyString());
        verify(memberRepository, never()).save(any(Members.class));
    }

    @Test
    void createMember_InvalidPassword_ShouldThrowValidationException() throws ValidationException, NotFoundException {
        // Arrange
        MemberInputModel inputModel = new MemberInputModel();
        inputModel.setName("John Doe");
        inputModel.setEmailId("john@example.com");
        inputModel.setPassword("pass");

        when(memberValidation.checkName("John Doe")).thenReturn(true);
        when(memberValidation.checkEmail("john@example.com")).thenReturn(true);
        when(memberValidation.validatePassword("pass")).thenReturn(false);

        // Act & Assert
        assertThrows(ValidationException.class, () -> memberServiceImplementation.createMember(inputModel));
        verify(memberValidation).checkName("John Doe");
        verify(memberValidation).checkEmail("john@example.com");
        verify(memberValidation).validatePassword("pass");
        verify(memberRepository, never()).find_by_mail(anyString());
        verify(memberRepository, never()).save(any(Members.class));
    }

    @Test
    void createMember_AlreadyRegisteredEmail_ShouldThrowNotFoundException() throws ValidationException, NotFoundException {
        // Arrange
        MemberInputModel inputModel = new MemberInputModel();
        inputModel.setName("John Doe");
        inputModel.setEmailId("john@example.com");
        inputModel.setPassword("password123");

        when(memberValidation.checkName("John Doe")).thenReturn(true);
        when(memberValidation.checkEmail("john@example.com")).thenReturn(true);
        when(memberValidation.validatePassword("password123")).thenReturn(true);
        when(memberRepository.find_by_mail("john@example.com")).thenReturn(Arrays.asList(new Members()));

        // Act & Assert
        assertThrows(NotFoundException.class, () -> memberServiceImplementation.createMember(inputModel));
        verify(memberValidation).checkName("John Doe");
        verify(memberValidation).checkEmail("john@example.com");
        verify(memberValidation).validatePassword("password123");
        verify(memberRepository).find_by_mail("john@example.com");
        verify(memberRepository, never()).save(any(Members.class));
    }

    

    @Test
    void getMember_ByCardId_ValidId_ShouldReturnMember() throws NotFoundException, MemberException {
        // Arrange
        int memberId = 1;
        Members member = new Members();
        member.setCardId(memberId);
        member.setName("John Doe");

        List<Members> memberList = new ArrayList<>();
        memberList.add(member);

        when(memberRepository.find_by_id(memberId)).thenReturn(memberList);

        // Act
        List<MemberOutputModel> result = memberServiceImplementation.getMember(memberId, null, null);

        // Assert
        assertEquals(1, result.size());
        assertEquals(memberId, result.get(0).getCardId());
        assertEquals("John Doe", result.get(0).getName());
        verify(memberRepository).find_by_id(memberId);
    }

    @Test
    void getMember_ByCardId_InvalidId_ShouldThrowNotFoundException() {
        // Arrange
        int memberId = 1;

        when(memberRepository.find_by_id(memberId)).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> memberServiceImplementation.getMember(memberId, null, null));
        verify(memberRepository).find_by_id(memberId);
    }

    @Test
    void getMember_ByEmailAndPhone_ValidEmailAndPhone_ShouldReturnMember() throws NotFoundException, MemberException {
        // Arrange
        String email = "john@example.com";
        String phone = "1234567890";
        Members member = new Members();
        member.setCardId(1);
        member.setName("John Doe");

        List<Members> memberList = new ArrayList<>();
        memberList.add(member);

        when(memberRepository.find_by_mail_phone(email, phone)).thenReturn(memberList);

        // Act
        List<MemberOutputModel> result = memberServiceImplementation.getMember(null, email, phone);

        // Assert
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getCardId());
        assertEquals("John Doe", result.get(0).getName());
        verify(memberRepository).find_by_mail_phone(email, phone);
    }

    @Test
    void getMember_ByEmailAndPhone_InvalidEmailOrPhone_ShouldThrowNotFoundException() {
        // Arrange
        String email = "john@example.com";
        String phone = "1234567890";

        when(memberRepository.find_by_mail_phone(email, phone)).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> memberServiceImplementation.getMember(null, email, phone));
        verify(memberRepository).find_by_mail_phone(email, phone);
    }

    @Test
    void getMember_ByCardStatus_ValidStatus_ShouldReturnMembersWithStatus() throws NotFoundException {
        // Arrange
        CardStatus cardStatus = CardStatus.ACTIVATED;
        Members member1 = new Members();
        member1.setCardId(1);
        member1.setName("John Doe");
        member1.setCardStatus(cardStatus);

        Members member2 = new Members();
        member2.setCardId(2);
        member2.setName("Jane Smith");
        member2.setCardStatus(cardStatus);

        List<Members> memberList = new ArrayList<>();
        memberList.add(member1);
        memberList.add(member2);

        when(memberRepository.find_by_status(cardStatus)).thenReturn(memberList);

        // Act
        List<MemberOutputModel> result = memberServiceImplementation.getMember(cardStatus);

        // Assert
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getCardId());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals(2, result.get(1).getCardId());
        assertEquals("Jane Smith", result.get(1).getName());
        verify(memberRepository).find_by_status(cardStatus);
    }

    @Test
    void getMember_ByCardStatus_InvalidStatus_ShouldThrowNotFoundException() {
        // Arrange
        CardStatus cardStatus = CardStatus.DEACTIVATED;

        when(memberRepository.find_by_status(cardStatus)).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> memberServiceImplementation.getMember(cardStatus));
        verify(memberRepository).find_by_status(cardStatus);
    }


    @Test
    void activate_deactivateMember_MemberWithBooksIssued_ShouldThrowBookNotFoundException() {
        // Arrange
        int cardId = 1;
        Members member = new Members();
        List<Integer> b = new ArrayList<>();
        member.setCardId(cardId);
        member.setName("John Doe");
        member.setCardStatus(CardStatus.ACTIVATED);
        b.add(new Book().getBookId()); 
        member.setBooks(b);// Assuming a book is issued

        when(memberRepository.findById(cardId)).thenReturn(java.util.Optional.of(member));

        // Act & Assert
        assertThrows(BookException.class, () -> memberServiceImplementation.activate_deactivateMember(cardId));
        verify(memberRepository).findById(cardId);
        verify(memberRepository, never()).setCard(anyInt(), any(CardStatus.class));
    }
    
    @Test
    void testCreateMember_Success() throws ValidationException, NotFoundException {
        // Arrange
        MemberInputModel memberInputModel = new MemberInputModel();
        memberInputModel.setName("John Doe");
        memberInputModel.setEmailId("john@example.com");
        memberInputModel.setPassword("password");

        when(memberValidation.checkName(eq(memberInputModel.getName()))).thenReturn(true);
        when(memberValidation.checkEmail(eq(memberInputModel.getEmailId()))).thenReturn(true);
        when(memberValidation.validatePassword(eq(memberInputModel.getPassword()))).thenReturn(true);
        when(memberRepository.find_by_mail(eq(memberInputModel.getEmailId()))).thenReturn(new ArrayList<>());

        // Act
        Members createdMember = memberServiceImplementation.createMember(memberInputModel);

        // Assert
        verify(memberValidation).checkName(eq(memberInputModel.getName()));
        verify(memberValidation).checkEmail(eq(memberInputModel.getEmailId()));
        verify(memberValidation).validatePassword(eq(memberInputModel.getPassword()));
        verify(memberRepository).find_by_mail(eq(memberInputModel.getEmailId()));
        verify(memberRepository).save(any(Members.class));

        Assertions.assertNotNull(createdMember);
        Assertions.assertEquals(memberInputModel.getName(), createdMember.getName());
        Assertions.assertEquals(memberInputModel.getEmailId(), createdMember.getEmailId());
        Assertions.assertEquals(CardStatus.ACTIVATED, createdMember.getCardStatus());
    }

    @Test
    void testCreateMember_InvalidName_ThrowsValidationException() throws ValidationException, NotFoundException {
        // Arrange
        MemberInputModel memberInputModel = new MemberInputModel();
        memberInputModel.setName("");
        memberInputModel.setEmailId("john@example.com");
        memberInputModel.setPassword("password");

        when(memberValidation.checkName(eq(memberInputModel.getName()))).thenReturn(false);

        // Act & Assert
        Assertions.assertThrows(ValidationException.class, () -> memberServiceImplementation.createMember(memberInputModel));

        verify(memberValidation).checkName(eq(memberInputModel.getName()));
        verify(memberRepository, never()).save(any(Members.class));
    }

    @Test
    void testCreateMember_InvalidEmail_ThrowsValidationException() throws ValidationException, NotFoundException {
        // Arrange
        MemberInputModel memberInputModel = new MemberInputModel();
        memberInputModel.setName("John Doe");
        memberInputModel.setEmailId("john@example.com");
        memberInputModel.setPassword("password");

        when(memberValidation.checkName(eq(memberInputModel.getName()))).thenReturn(true);
        when(memberValidation.checkEmail(eq(memberInputModel.getEmailId()))).thenReturn(false);

        // Act & Assert
        Assertions.assertThrows(ValidationException.class, () -> memberServiceImplementation.createMember(memberInputModel));

        verify(memberValidation).checkName(eq(memberInputModel.getName()));
        verify(memberValidation).checkEmail(eq(memberInputModel.getEmailId()));
        verify(memberRepository, never()).save(any(Members.class));
    }

    @Test
    void testCreateMember_InvalidPassword_ThrowsValidationException() throws ValidationException, NotFoundException {
        // Arrange
        MemberInputModel memberInputModel = new MemberInputModel();
        memberInputModel.setName("John Doe");
        memberInputModel.setEmailId("john@example.com");
        memberInputModel.setPassword("password");

        when(memberValidation.checkName(eq(memberInputModel.getName()))).thenReturn(true);
        when(memberValidation.checkEmail(eq(memberInputModel.getEmailId()))).thenReturn(true);
        when(memberValidation.validatePassword(eq(memberInputModel.getPassword()))).thenReturn(false);

        // Act & Assert
        Assertions.assertThrows(ValidationException.class, () -> memberServiceImplementation.createMember(memberInputModel));

        verify(memberValidation).checkName(eq(memberInputModel.getName()));
        verify(memberValidation).checkEmail(eq(memberInputModel.getEmailId()));
        verify(memberValidation).validatePassword(eq(memberInputModel.getPassword()));
        verify(memberRepository, never()).save(any(Members.class));
    }

    @Test
    void testCreateMember_AlreadyRegisteredEmail_ThrowsNotFoundException() throws ValidationException, NotFoundException {
        // Arrange
        MemberInputModel memberInputModel = new MemberInputModel();
        memberInputModel.setName("John Doe");
        memberInputModel.setEmailId("john@example.com");
        memberInputModel.setPassword("password");

        List<Members> existingMembers = new ArrayList<>();
        existingMembers.add(new Members());

        when(memberValidation.checkName(eq(memberInputModel.getName()))).thenReturn(true);
        when(memberValidation.checkEmail(eq(memberInputModel.getEmailId()))).thenReturn(true);
        when(memberValidation.validatePassword(eq(memberInputModel.getPassword()))).thenReturn(true);
        when(memberRepository.find_by_mail(eq(memberInputModel.getEmailId()))).thenReturn(existingMembers);

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> memberServiceImplementation.createMember(memberInputModel));

        verify(memberValidation).checkName(eq(memberInputModel.getName()));
        verify(memberValidation).checkEmail(eq(memberInputModel.getEmailId()));
        verify(memberValidation).validatePassword(eq(memberInputModel.getPassword()));
        verify(memberRepository).find_by_mail(eq(memberInputModel.getEmailId()));
        verify(memberRepository, never()).save(any(Members.class));
    }

}
