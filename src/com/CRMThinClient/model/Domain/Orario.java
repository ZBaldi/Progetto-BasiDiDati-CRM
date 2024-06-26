package com.CRMThinClient.model.Domain;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Orario {
	private LocalTime orario;
	
	public void inserisciOrario(String str) throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		while(true) {
			try {
				orario = LocalTime.parse(str,formatter);
				break;
			} catch (DateTimeParseException e) {
				System.out.println("Orario non valido!");
				throw new Exception();
			}
		}
	}
	
	public Time getTimeForDBMS() {
	    Time sqlTime = Time.valueOf(this.orario);
	    return sqlTime;
	}
	
	@Override
	public String toString(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		return orario.format(formatter);
	}
}
