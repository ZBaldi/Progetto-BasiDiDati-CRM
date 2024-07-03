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
	
	public String getCodice() {
		return this.codiceOfferta;
	}
	
	public String getNome() {
		return this.nomeOfferta;
	}
	
	public String getDescrizione() {
		return this.descrizione;
	}
	
	public Data getScadenza() {
		return this.dataDiScadenza;
	}
	
	@Override
	public String toString() {  //METODO PER RESTITUIRE COME STRINGA LE INFORMAZIONI DELL'OFFERTA
		return "Codice offerta: "+codiceOfferta+"\n"+"Nome offerta: "+nomeOfferta+"\n"+"Data di scadenza: "+dataDiScadenza.toString()+"\n"+"Descrizione: "+descrizione;
	}
}