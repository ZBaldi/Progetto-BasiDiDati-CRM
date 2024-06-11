package com.CRMThinClient.model.Domain;

public class Nota {
	private String codiceOfferta;
	private String codiceCliente;
	private String codiceOperatore;
	private String esito;
	private Data dataDiModifica;
	private Appuntamento appuntamento;
	
	public Nota(String codiceOfferta,String codiceCliente,String codiceOperatore) {
		this.codiceOfferta=codiceOfferta;
		this.codiceCliente=codiceCliente;
		this.codiceOperatore=codiceOperatore;
	}
	
	public void inserisciEsito(String esito) {
		this.esito=esito;
		this.dataDiModifica=new Data();
		dataDiModifica.dataCorrente();
	}
	
	public void allegaAppuntamento(Appuntamento appuntamento) {
		this.appuntamento=appuntamento;
	}
	
	@Override
	public String toString() {
		return codiceOfferta+" "+codiceCliente+" "+codiceOperatore+"\n"+esito+"\n"+dataDiModifica+"\n"+appuntamento.toString();
	}
}