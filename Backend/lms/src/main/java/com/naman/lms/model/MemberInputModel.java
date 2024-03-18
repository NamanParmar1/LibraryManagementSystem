package com.naman.lms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberInputModel {

    private String name;
    private String emailId;
    private String phoneNumber;
    private String password;
}
