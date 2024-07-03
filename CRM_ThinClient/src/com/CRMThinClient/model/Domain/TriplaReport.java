package com.CRMThinClient.model.Domain;

public class TriplaReport {
	private Cliente cliente;
	private int contattato;
	private int accettato;
	
	public TriplaReport(Cliente cliente,int contattato, int accettato) {
		this.cliente=cliente;
		this.contattato=contattato;
		this.accettato=accettato;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public int getContattato() {
		return contattato;
	}

	public int getAccettato() {
		return accettato;
	}
	
	@Override
	public String toString() {  //METODO PER RESTITUIRE COME STRINGA LE INFORMAZIONI DELLA TRIPLA (CLIENTE, NUMERO DI CONTATTI, NUMERO DI OFFERTE ACCETTATE)
		return cliente.getNome()+"\n"+cliente.getCognome()+"\n"+cliente.getCf()+"\n"+"Contattato: "+contattato+" volte"+"\n"+"Accettato: "+accettato+" offerte";
	}
	
}