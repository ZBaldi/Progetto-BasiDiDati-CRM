package com.CRMThinClient.model.Domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Orario {
	private LocalTime orario;
	
	public void inserisciOrario(int ore, int minuti) {
		while(true) {
			try {
				orario = LocalTime.of(ore, minuti);
				break;
			} catch (Exception e) {
				System.out.println("Orario non valido! Riprova.");
			}
		}
	}
	
	@Override
	public String toString(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		return orario.format(formatter);
	}
}
