package com.CRMThinClient.model.bean;

import com.CRMThinClient.model.Domain.Data;
import com.CRMThinClient.model.Domain.SchemaRegex;
import com.CRMThinClient.model.Domain.ValidatoreCampi;

public class OffertaBean {
	
	private String codiceOfferta;
	private String nomeOfferta;
	private String descrizione;
	private Data dataDiScadenza;
	
	public boolean setCodiceOfferta(String codiceOfferta) {
		if(ValidatoreCampi.validatore(SchemaRegex.CODICEOFFERTA,codiceOfferta)) {
			this.codiceOfferta=codiceOfferta;
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getNomeOfferta() {
		return nomeOfferta;
	}

	public void setNomeOfferta(String nomeOfferta) {
		this.nomeOfferta = nomeOfferta;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCodiceOfferta() {
		return codiceOfferta;
	}

	public Data getDataDiScadenza() {
		return dataDiScadenza;
	}

	public boolean setDataScadenza(String data) {
		this.dataDiScadenza= new Data();
		try{
			dataDiScadenza.inserisciData(data);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
}