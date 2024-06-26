package com.CRMThinClient.model.Domain;

public class Indirizzo {
	private String via,citta,provincia,paese;
	private int civico,cap;
	
	public Indirizzo(String via, int civico, int cap, String citta, String provincia, String paese) {
		this.via=via;
		this.civico=civico;
		this.cap=cap;
		this.citta=citta;
		this.provincia=provincia;
		this.paese=paese;
	}
	
	@Override
	public String toString() {
		return "via: "+via+" "+civico+" "+citta+" "+cap+" "+provincia+" "+paese;
	}
}