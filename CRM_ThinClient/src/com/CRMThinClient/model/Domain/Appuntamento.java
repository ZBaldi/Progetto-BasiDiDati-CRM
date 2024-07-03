package com.CRMThinClient.model.Domain;

public class Appuntamento {
	private String sede;
	private Data dataAppuntamento;
	private Orario orarioAppuntamento;
	
	public void inserisciSede(String sede) {
		this.sede=sede;
	}
	
	public void inserisciDataEOrario(Data dataAppuntamento,Orario orarioAppuntamento) {
		this.dataAppuntamento=dataAppuntamento;
		this.orarioAppuntamento=orarioAppuntamento;
	}
	
	public String getSede() {
		return this.sede;
	}
	
	public Data getData() {
		return this.dataAppuntamento;
	}
	
	public Orario getOrario() {
		return this.orarioAppuntamento;
	}
	
	@Override
	public String toString(){  //METODO PER RESTITUIRE COME STRINGA LE INFORMAZIONI DELL'APPUNTAMENTO
		return "Sede: "+sede+"\n"+"Data appuntamento: "+dataAppuntamento.toString()+"\n"+"Orario appuntamento: "+orarioAppuntamento.toString();
	}
}