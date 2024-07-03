package com.CRMThinClient.model.Domain;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Orario {
	private LocalTime orario;
	
	public void inserisciOrario(String str) throws Exception {  //METODO PER INSERIRE L'ORARIO CON IL PATTERN ORA E MINUTI
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
	
	public Time getTimeForDBMS() {  //METODO PER OTTENERE L'ORARIO NEL FORMATO PER IL DBMS
	    Time sqlTime = Time.valueOf(this.orario);
	    return sqlTime;
	}
	
	@Override
	public String toString(){  //METODO PER RESTITUIRE COME STRINGA L'ORARIO
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		return orario.format(formatter);
	}
}