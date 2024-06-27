package com.CRMThinClient.model.Domain;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
	private String nome;
	private String cognome;
	private String cf;
	private List<String> email=new ArrayList<String>();
	private Data dataDiRegistrazione;
	private Data dataDiNascita;
	private List<String> telefoni=new ArrayList<String>();
	private Indirizzo indirizzo;
	
	public Cliente(String nome,String cognome,String cf, Data dataDiRegistrazione, Data dataDiNascita) {
		this.nome=nome;
		this.cognome=cognome;
		this.cf=cf;
		this.dataDiRegistrazione=dataDiRegistrazione;
		this.dataDiNascita=dataDiNascita;
	}
	
	public void inserisciEmail(String email) {
		this.email.add(email);
	}
	
	public void inserisciTelefono(String telefono) {
		this.telefoni.add(telefono);
	}
	
	public void inserisciIndirizzo(Indirizzo indirizzo) {
		this.indirizzo=indirizzo;
	}
	
	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getCf() {
		return cf;
	}

	public List<String> getEmail() {
		return email;
	}

	public Data getDataDiRegistrazione() {
		return dataDiRegistrazione;
	}

	public Data getDataDiNascita() {
		return dataDiNascita;
	}

	public List<String> getTelefoni() {
		return telefoni;
	}

	public Indirizzo getIndirizzo() {
		return indirizzo;
	}

	@Override
	public String toString(){
		StringBuilder stringa= new StringBuilder();
		stringa.append("Nome: "+nome+" "+"Cognome: "+cognome+" "+"Data di nascita: "+dataDiNascita+"\n"+"CodiceFiscale: "+cf+"\n"+"Data di registrazione: "+dataDiRegistrazione+"\n"+"Indirizzo: "+indirizzo.toString()+"\n");
		stringa.append("Email: \n");
		for(String s: email) {
			stringa.append(s+"\n");
		}
		stringa.append("Telefoni: \n");
		for(String s: telefoni) {
			stringa.append(s+"\n");
		}
		stringa.deleteCharAt(stringa.length()-1); //rimuovo a capo finale
		return stringa.toString();
	}
}