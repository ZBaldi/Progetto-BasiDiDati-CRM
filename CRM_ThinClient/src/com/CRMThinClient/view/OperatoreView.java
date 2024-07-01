package com.CRMThinClient.view;

import java.io.IOException;
import com.CRMThinClient.main.Main;
import com.CRMThinClient.model.bean.NotaBean;
import com.CRMThinClient.model.bean.OffertaAccettataBean;

public class OperatoreView {

	public static int showMenu() throws IOException {
		stampaMessaggio("--------------------------------\n"+
						"|          |CRM MENU|          |\n"+
						"--------------------------------\n\n"+
						"--------Cosa vuoi fare? --------\n\n"+
						"1) Mostra le note associate ad un cliente\n"+
						"2) Mostra clienti\n"+
						"3) Mostra offerte valide\n"+
						"4) Scrivi una nota\n"+
						"5) Salva offerta accettata\n"+
						"6) Esci\n\n");
		
		int choice = 0;
		while (true) {
			stampaMessaggio("Perfavore inserisci la tua scelta: ");
			 choice = Integer.parseInt(inserisciInput());
			 if (choice >= 1 && choice <= 6) {
			     break;
			 }
			 stampaMessaggio("Opzione invalida\n");
		}
		return choice;
    }
	
	public static void riepilogo(String s) {
		stampaMessaggio("--------------------------------\n"+
						"|         |RIEPILOGO|          |\n"+
						"--------------------------------\n\n"+
						s+"\n\n");
	}
	
	public static OffertaAccettataBean inserisciDatiOffertaAccettata() {
		OffertaAccettataBean bean= new OffertaAccettataBean();
		String codiceOfferta;
		while(true) {
			stampaMessaggio("Inserisci Codice Offerta: ");
			codiceOfferta= inserisciInput();
			if(bean.setCodiceOfferta(codiceOfferta)) {
				break;
			}
			else {
				stampaMessaggio("Codice offerta non valido!\n");
			}
		}
		String cf;
		while(true) {
			stampaMessaggio("Inserisci CF cliente: ");
			cf= inserisciInput();
			if(bean.setCliente(cf)) {
				break;
			}
			else {
				stampaMessaggio("CF non valido!\n");
			}
		}
		String data;
		while(true) {
			stampaMessaggio("Inserisci data di accettazione Formato:gg-mm-aaaa: ");
			data=inserisciInput();
			if(bean.setDataContratto(data)) {
				break;
			}
			else {
				stampaMessaggio("Data non valida!\n");
			}
		}
		return bean;
	}
	
	public static NotaBean inserisciDatiNota() {
		NotaBean bean= new NotaBean();
		String codiceOfferta;
		while(true) {
			stampaMessaggio("Inserisci Codice Offerta: ");
			codiceOfferta= inserisciInput();
			if(bean.setCodiceOfferta(codiceOfferta)) {
				break;
			}
			else {
				stampaMessaggio("Codice offerta non valido!\n");
			}
		}
		String cf;
		while(true) {
			stampaMessaggio("Inserisci CF cliente: ");
			cf= inserisciInput();
			if(bean.setCliente(cf)) {
				break;
			}
			else {
				stampaMessaggio("CF non valido!\n");
			}
		}
		stampaMessaggio("Inserisci esito chiamata: ");
		bean.setEsito(inserisciInput());
		stampaMessaggio("Vuoi allegare un appuntamento? Si/No: ");
		if(inserisciInput().equalsIgnoreCase("Si")) {
			stampaMessaggio("Inserisci sede: ");
			bean.setSede(inserisciInput());
			String data;
			while(true) {
				stampaMessaggio("Inserisci data di appuntamento Formato:gg-mm-aaaa: ");
				data=inserisciInput();
				if(bean.setDataAppuntamento(data)) {
					break;
				}
				else {
					stampaMessaggio("Data non valida!\n");
				}
			}
			String orario;
			while(true) {
				stampaMessaggio("Inserisci orario appuntamento Formato: hh:mm : ");
				orario=inserisciInput();
				if(bean.setOrarioAppuntamento(orario)) {
					break;
				}
				else {
					stampaMessaggio("Orario non valido!\n");
				}
			}
		}
		return bean;
	}
	
	public static String inserisciInput() {
		return Main.getScanner().nextLine();
	}
	
	public static void stampaMessaggio(String s) {
		System.out.print(s);
	}
	
}