package com.CRMThinClient.model.Domain;

public class Offerta {
	private String codiceOfferta;
	private String nomeOfferta;
	private String descrizione;
	private Data dataDiScadenza;
	
	public Offerta(String codiceOfferta,String nomeOfferta) {
		this.codiceOfferta=codiceOfferta;
		this.nomeOfferta=nomeOfferta;
	}
	
	public void inserisciDescrizione(String descrizione) {
		this.descrizione=descrizione;
	}
	
	public void inserisciScadenza(Data dataDiScadenza) {
		this.dataDiScadenza=dataDiScadenza;
	}
	
	@Override
	public String toString() {
		return codiceOfferta+" "+nomeOfferta+" "+dataDiScadenza.toString()+"\n"+descrizione;
	}
}