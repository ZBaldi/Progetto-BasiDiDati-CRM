package com.CRMThinClient.model.Domain;

public class Indirizzo {
	private String via,citta,provincia,paese;
	
	public Indirizzo(String via,String citta, String provincia, String paese) {
		this.via=via;
		this.citta=citta;
		this.provincia=provincia;
		this.paese=paese;
	}
	
	public String getVia() {
		return via;
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
		return via+" "+citta+" "+provincia+" "+paese;
	}
}