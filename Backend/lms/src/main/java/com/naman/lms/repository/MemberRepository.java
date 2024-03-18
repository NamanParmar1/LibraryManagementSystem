package com.naman.lms.repository;

import com.naman.lms.entity.CardStatus;
import com.naman.lms.entity.Members;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Transactional
public interface MemberRepository extends JpaRepository<Members, Integer> {


    @Modifying
    @Query("update Members m set m.emailId=:newEmail where m.emailId=:oldEmail")
    int updateMemberEmail(@Param("oldEmail") String oldEmail, @Param("newEmail") String newEmail);


    @Modifying
    @Query("delete from Members m where m.cardId=:id ")
    int deleteCustom(@Param("id") int id);

    @Modifying
    @Query("update Members m set m.cardStatus=:status where m.cardId=:card_id")
    void setCard(@Param("card_id") int card_id, @Param("status") CardStatus status);

    @Modifying
    @Query("update Members m set m.cardStatus=:status where m.cardId=:card_id")
    void activateCard(@Param("card_id") int card_id, @Param("status") CardStatus status);

    @Query("select b from Members b where b.emailId=:mail")
    List<Members> find_by_mail(String mail);

    @Query("select b from Members b where b.phoneNumber=:phone")
    List<Members> find_by_phone(String phone);

    @Query("select b from Members b where b.emailId =:mail and b.phoneNumber=:phone")
    List<Members> find_by_mail_phone(@Param("mail") String mail, @Param("phone") String phone);

    @Query("select b from Members b where b.cardId =:id")
    List<Members> find_by_id(int id);

    @Query("select b from Members b where b.cardStatus =:c")
    List<Members> find_by_status(CardStatus c);



}

