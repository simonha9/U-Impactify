package com.utsc.project_coding_lads.security;
import java.security.MessageDigest;

public class PasswordHash {
	
	public String passwordEncoder(String password) {
		MessageDigest messageDigest;
		
		if (password == null || password.trim().isEmpty()) {
			return null;
		}
		
	    try {
	    	messageDigest = MessageDigest.getInstance("SHA-256");
	    	messageDigest.update(password.getBytes("UTF-8"));
	    	byte[] digest = messageDigest.digest();
	    	return String.format("%064x", new java.math.BigInteger(1, digest));
	      
	    } catch (Exception e) {
	    	return null;
	    }
	}

}
