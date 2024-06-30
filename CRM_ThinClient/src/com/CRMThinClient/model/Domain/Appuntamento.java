package com.CRMThinClient.model.Domain;

public class Appuntamento {
	private String codiceCliente;
	private String sede;
	private Data dataAppuntamento;
	private Orario orarioAppuntamento;
	
	public Appuntamento(String codiceCliente) {
		this.codiceCliente=codiceCliente;
	}
	
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
	public String toString(){
		return "Codice cliente: "+codiceCliente+" "+"Sede: "+sede+"\n"+"Data appuntamento: "+dataAppuntamento.toString()+"\n"+"Orario appuntamento: "+orarioAppuntamento.toString();
	}
}