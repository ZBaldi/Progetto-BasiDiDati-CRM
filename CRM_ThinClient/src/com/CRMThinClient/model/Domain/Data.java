package com.CRMThinClient.model.Domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Data {
	private LocalDate data;
	
	public void inserisciData(String str) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        while(true) {
        	try {
        		data = LocalDate.parse(str, formatter);
        		break;
        	} catch (DateTimeParseException e) {
        		System.out.println("Data non valida! Riprova.");
        	}
        }
	}
	
	public void dataCorrente() {
		data=LocalDate.now();
	}
	
	@Override
	public String toString() {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	     return data.format(formatter);
	}
}