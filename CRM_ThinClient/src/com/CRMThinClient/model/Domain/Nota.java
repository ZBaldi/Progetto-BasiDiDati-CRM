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
	}
	
	public void inserisciData(boolean bool,Data data) {
		if(bool) {
			this.dataDiModifica=new Data();
			dataDiModifica.dataCorrente();
		}
		else {
			this.dataDiModifica=data;
		}
	}
	
	public void allegaAppuntamento(Appuntamento appuntamento) {
		this.appuntamento=appuntamento;
	}
	
	public Appuntamento getAppuntamento() {
		if(this.appuntamento != null) {
			return appuntamento;
		}
		else {
			return null;
		}
	}
	
	public String getCliente() {
		return this.codiceCliente;
	}
	
	public String getOfferta() {
		return this.codiceOfferta;
	}
	
	public String getOperatore() {
		return this.codiceOperatore;
	}
	
	public String getEsito() {
		return this.esito;
	}
	
	public Data getData() {
		return this.dataDiModifica;
	}
	
	@Override
	public String toString() {
		String str="Codice offerta: "+codiceOfferta+"\n"+"Codice fiscale cliente: "+codiceCliente+"\n"+"Codice operatore: "+codiceOperatore+"\n"+"esito: "+esito+"\n"+"data di modifica: "+dataDiModifica.toString();
		if(appuntamento!=null) {
			str= str+"\n"+"Appuntamento:\n"+appuntamento.toString();
		}
		return str;
	}
}