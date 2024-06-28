package com.CRMThinClient.model.Domain;

public class Report {
	private Cliente cliente=null;
	private int contattato;
	
	public Report(Cliente cliente, int contattato) {
		this.cliente=cliente;
		this.contattato=contattato;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public int getContattato() {
		return contattato;
	}
	

	@Override
	public String toString() {
		return cliente.getNome()+" "+cliente.getCognome()+" "+cliente.getCf()+": "+contattato;
	}
}