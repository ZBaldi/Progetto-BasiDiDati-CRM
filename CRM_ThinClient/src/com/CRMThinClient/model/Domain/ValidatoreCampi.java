package com.CRMThinClient.model.Domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatoreCampi {
	
	public static boolean validatore(SchemaRegex campo, String daValidare) {
		String schema=campo.getSchema();
		Pattern pattern = Pattern.compile(schema);
		Matcher matcher = pattern.matcher(daValidare);
		return matcher.matches();
	}
}