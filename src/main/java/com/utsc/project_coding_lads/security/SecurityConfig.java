package com.utsc.project_coding_lads.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.utsc.project_coding_lads.domain.User;
import com.utsc.project_coding_lads.exception.MissingInformationException;
import com.utsc.project_coding_lads.exception.UsernameNotFoundException;
import com.utsc.project_coding_lads.exception.WrongPasswordException;
import com.utsc.project_coding_lads.repository.UserRepository;

@Service
public class SecurityConfig {
	@Autowired
	UserRepository userRepo;

	public Integer authentication(User entrant) throws WrongPasswordException, MissingInformationException, UsernameNotFoundException {
		String eUsername = entrant.getUsername();
		String hashedPassword = entrant.getHashedPassword();

		// check if inputed username is null
		if (eUsername == "") {
			throw new MissingInformationException("No username inputed");
		}
		// check if inputed password is null
		if (hashedPassword == "") {
			throw new MissingInformationException("No password inputed");
		}

		User confirmation = new User();
		confirmation = loadUserByUsername(eUsername);
		

		if (!hashedPassword.equals(confirmation.getHashedPassword())) {
			throw new WrongPasswordException("Password is incorrect: " + hashedPassword + " correct pass: " + confirmation.getHashedPassword());
		}

		return confirmation.getId();
	}

	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findUserByUsername(username);
	}

}
