package com.CRMThinClient.model.Domain;

public class OffertaAccettata{
	private String codiceOperatore;
	private String codiceCliente;
	private Data dataDiAccettazione;
	
	public OffertaAccettata(String codiceOperatore,String codiceCliente,Data dataDiAccettazione) {
		this.codiceOperatore=codiceOperatore;
		this.codiceCliente=codiceCliente;
		this.dataDiAccettazione=dataDiAccettazione;
	}
	
	@Override
	public String toString() {
		return codiceOperatore+" "+codiceCliente+" "+dataDiAccettazione.toString();
	}
}