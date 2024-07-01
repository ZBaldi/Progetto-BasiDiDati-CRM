package com.CRMThinClient.model.bean;

import com.CRMThinClient.model.Domain.Data;
import com.CRMThinClient.model.Domain.Orario;
import com.CRMThinClient.model.Domain.SchemaRegex;
import com.CRMThinClient.model.Domain.ValidatoreCampi;

public class NotaBean {
	private String codiceOfferta;
	private String codiceCliente;
	private String esito;
	private String sede=null;
	private Data dataAppuntamento=null;
	private Orario orarioAppuntamento=null;
	
	public String getCodiceOfferta() {
		return codiceOfferta;
	}
	
	public boolean setCodiceOfferta(String codiceOfferta) {
		if(ValidatoreCampi.validatore(SchemaRegex.CODICEOFFERTA,codiceOfferta)) {
			this.codiceOfferta=codiceOfferta;
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getCodiceCliente() {
		return codiceCliente;
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
	
	public String getEsito() {
		return esito;
	}
	
	public void setEsito(String esito) {
		this.esito = esito;
	}
	
	public void setSede(String sede) {
		this.sede=sede;
	}
	
	public String getSede() {
		return this.sede;
	}
	
	public boolean setDataAppuntamento(String data) {
		this.dataAppuntamento= new Data();
		try{
			dataAppuntamento.inserisciData(data);
			return true;
		}catch(Exception e) {
			return false;
		}
	}	
	
	public Data getDataAppuntamento() {
		return this.dataAppuntamento;
	}
	
	public boolean setOrarioAppuntamento(String orario) {
		this.orarioAppuntamento= new Orario();
		try{
			orarioAppuntamento.inserisciOrario(orario);
			return true;
		}catch(Exception e) {
			return false;
		}
	}	
	
	public Orario getOrarioAppuntamento() {
		return this.orarioAppuntamento;
	}
	
}