package com.CRMThinClient.model.bean;

import com.CRMThinClient.model.Domain.Data;
import com.CRMThinClient.model.Domain.SchemaRegex;
import com.CRMThinClient.model.Domain.ValidatoreCampi;

public class OffertaAccettataBean {  //INCAPSULA LA CLASSE OFFERTA ACCETTATA
	private String codiceCliente;
	private String codiceOfferta;
	private Data dataDiAccettazione;
	
	public boolean setCodiceOfferta(String codiceOfferta) {
		if(ValidatoreCampi.validatore(SchemaRegex.CODICEOFFERTA,codiceOfferta)) {
			this.codiceOfferta=codiceOfferta;
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean setCliente(String cf) {
		if(ValidatoreCampi.validatore(SchemaRegex.CF,cf)) {
			this.codiceCliente=cf;
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean setDataContratto(String data) {
		this.dataDiAccettazione= new Data();
		try{
			dataDiAccettazione.inserisciData(data);
			return true;
		}catch(Exception e) {
			return false;
		}
	}	
	
	public String getCodiceOfferta() {
		return this.codiceOfferta;
	}	
	
	public String getCliente() {
		return this.codiceCliente;
	}
	
	public Data getDataContratto() {
		return this.dataDiAccettazione;
	}
	
}