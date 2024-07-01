package com.CRMThinClient.model.Domain;

import java.util.ArrayList;
import java.util.List;

public class Report {
	private int totale;
	private List<TriplaReport> lista= new ArrayList<TriplaReport>();
	
	public void setTotale(int totale) {
		this.totale = totale;
	}

	public void inserisciTripla(TriplaReport t) {
		lista.add(t);
	}
	
	public int getTotale() {
		return totale;
	}

	public List<TriplaReport> getLista() {
		return lista;
	}
	
	@Override
	public String toString() {
		StringBuilder stringa=new StringBuilder();
		stringa.append("Totale clienti contattati: "+totale+"\n\n");
		for(TriplaReport t: lista) {
			stringa.append(t.toString()+"\n\n---------------------------------\n\n");
		}
		stringa.deleteCharAt(stringa.length()-1);
		return stringa.toString();
	}
	
}