package com.CRMThinClient.model.Domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatoreCampi {
	
	public static boolean validatore(SchemaRegex campo, String daValidare) {  //METODO PER EFFETTUARE UN CONTROLLO DELL'INPUT INSERITO UTILIZZANDO LE ESPRESSIONI REGOLARI
		String schema=campo.getSchema();
		Pattern pattern = Pattern.compile(schema);
		Matcher matcher = pattern.matcher(daValidare);
		return matcher.matches();
	}
}