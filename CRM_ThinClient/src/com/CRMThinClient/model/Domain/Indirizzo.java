package com.CRMThinClient.model.Domain;

public class Indirizzo {
	private String indirizzo,citta,provincia,paese;
	
	public Indirizzo(String indirizzo, String citta, String provincia, String paese) {
		this.indirizzo=indirizzo;
		this.citta=citta;
		this.provincia=provincia;
		this.paese=paese;
	}
	
	public String getIndirizzo() {
		return indirizzo;
	}

	public String getCitta() {
		return citta;
	}

	public String getProvincia() {
		return provincia;
	}

	public String getPaese() {
		return paese;
	}
	
	@Override
	public String toString() {
		return indirizzo+" "+citta+" "+provincia+" "+paese;
	}
}