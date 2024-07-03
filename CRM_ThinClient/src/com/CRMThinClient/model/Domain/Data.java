package com.CRMThinClient.model.Domain;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Data {
	private LocalDate data;
	
	public void inserisciData(String str) throws Exception {  //METODO PER INSERIRE LA DATA SECONDO IL PATTERN ITALIANO
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        while(true) {
        	try {
        		data = LocalDate.parse(str, formatter);
        		break;
        	} catch (DateTimeParseException e) {
        		System.out.println("Data non valida!");
        		throw new Exception();
        	}
        }
	}
	
	public void dataCorrente() {  //METODO PER ASSOCIARE LA DATA CORRENTE
		data=LocalDate.now();
	}
	
	public Date getDataForDBMS() {  //METODO PER OTTENERE LA DATA NEL FORMATO DEL DBMS
	    Date sqlDate = Date.valueOf(this.data);
	    return sqlDate;
	}
	
	@Override
	public String toString() {  //METODO PER RESTITUIRE COME STRINGA LA DATA
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	     return data.format(formatter);
	}
}