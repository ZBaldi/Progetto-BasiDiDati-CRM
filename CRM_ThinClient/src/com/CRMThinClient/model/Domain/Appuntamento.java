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
	
	@Override
	public String toString(){
		return codiceCliente+" "+sede+"\n"+dataAppuntamento.toString()+"\n"+orarioAppuntamento.toString();
	}
}