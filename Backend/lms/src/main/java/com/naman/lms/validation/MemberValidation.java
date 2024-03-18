package com.naman.lms.validation;

import org.springframework.stereotype.Component;


@Component
public class MemberValidation {

	public boolean checkName(String name) {
		if (name.matches("^(?!.*\\s{4})[A-Za-z\\s.]+$")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkEmail(String emailId) {
		if (emailId.matches("^[A-Za-z0-9+_.]+@(.+)$")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validatePassword(String password){
		if (password.length() < 8 || password.length() > 16) {
			return false;
		} else if (password.matches("[A-Za-z]+")) {
			return false;
		} else if (password.matches("[0-9]+")) {
			return false;
		} else {
			return true;
		}
	}
}
