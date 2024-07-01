package com.CRMThinClient.model.bean;

import java.util.ArrayList;
import java.util.List;
import com.CRMThinClient.model.Domain.Data;
import com.CRMThinClient.model.Domain.SchemaRegex;
import com.CRMThinClient.model.Domain.ValidatoreCampi;

public class ClienteBean {
	private String nome;
	private String cognome;
	private String cf;
	private List<String> email=new ArrayList<String>();
	private Data dataDiNascita;
	private List<String> telefoni=new ArrayList<String>();
	private String indirizzo;
	private String citta;
	private String provincia;
	private String paese;
	
	public boolean checkCap(String cap) {
		if(ValidatoreCampi.validatore(SchemaRegex.CAP,cap)) {
			return true;
		}
		else {
			return false;
		}
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getProvincia() {
		return provincia;
	}

	public boolean setProvincia(String provincia) {
		if(ValidatoreCampi.validatore(SchemaRegex.PROVINCIA,provincia)) {
			this.provincia=provincia;
			return true;
		}
		else {
			return false;
		}
	}

	public String getPaese() {
		return paese;
	}

	public void setPaese(String paese) {
		this.paese = paese;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	
	public String getIndirizzo() {
		return indirizzo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String getCf() {
		return cf;
	}
	
	public boolean setCf(String cf) {
		if(ValidatoreCampi.validatore(SchemaRegex.CF,cf)) {
			this.cf=cf;
			return true;
		}
		else {
			return false;
		}
	}
	
	public List<String> getEmail() {
		return email;
	}
	public boolean inserisciEmail(String e) {
		if(ValidatoreCampi.validatore(SchemaRegex.EMAIL,e)) {
			email.add(e);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean inserisciTelefono(String telefono) {
		if(ValidatoreCampi.validatore(SchemaRegex.TELEFONO,telefono)) {
			telefoni.add(telefono);
			return true;
		}
		else {
			return false;
		}
	}
	
	public Data getDataDiNascita() {
		return dataDiNascita;
	}
	
	public boolean setDataDiNascita(String data) {
		this.dataDiNascita= new Data();
		try{
			dataDiNascita.inserisciData(data);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public List<String> getTelefoni() {
		return telefoni;
	}
		
}