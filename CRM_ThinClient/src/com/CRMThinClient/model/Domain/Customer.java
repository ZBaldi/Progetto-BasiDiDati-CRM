package com.CRMThinClient.model.Domain;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	private String nome;
	private String cognome;
	private String cf;
	private List<String> email=new ArrayList<String>();
	private Data dataDiRegistrazione;
	private Data dataDiNascita;
	private List<String> telefoni=new ArrayList<String>();
	private Indirizzo indirizzo;
	
	public Customer(String nome,String cognome,String cf, Data dataDiRegistrazione, Data dataDiNascita) {
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
	
	@Override
	public String toString(){
		StringBuilder stringa= new StringBuilder();
		stringa.append(nome+" "+cognome+" "+dataDiNascita+"\n"+cf+"\n"+dataDiRegistrazione+"\n"+indirizzo.toString()+"\n");
		for(String s: email) {
			stringa.append(s+"\n");
		}
		for(String s: telefoni) {
			stringa.append(s+"\n");
		}
		stringa.deleteCharAt(stringa.length()-1); //rimuovo a capo finale
		return stringa.toString();
	}
}