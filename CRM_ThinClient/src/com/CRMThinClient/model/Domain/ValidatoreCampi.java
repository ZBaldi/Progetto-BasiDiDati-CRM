package com.CRMThinClient.model.Domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatoreCampi {
	
	public static boolean validaEmail(String email) {
		String email_schema = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		Pattern pattern = Pattern.compile(email_schema);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	public static boolean validaTelefono(String telefono) {
		String telefono_schema = "^\\+\\d{1,3}\\s\\d{10}$";
		Pattern pattern = Pattern.compile(telefono_schema);
		Matcher matcher = pattern.matcher(telefono);
		return matcher.matches();
	}
	
}