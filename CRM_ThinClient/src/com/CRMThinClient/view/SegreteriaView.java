package com.CRMThinClient.view;

import java.io.IOException;
import com.CRMThinClient.main.Main;
import com.CRMThinClient.model.bean.ClienteBean;
import com.CRMThinClient.model.bean.OffertaBean;

public class SegreteriaView {
	
	public static int showMenu() throws IOException {
			stampaMessaggio("--------------------------------\n"+
							"|          |CRM MENU|          |\n"+
							"--------------------------------\n\n"+
							"--------Cosa vuoi fare? --------\n\n"+
							"1) Inserisci nuova offerta\n"+
							"2) Effettua il report dei clienti\n"+
							"3) Registra nuovo cliente\n"+
							"4) Elimina un'offerta\n"+
							"5) Esci\n\n");
		
		int choice = 0;
		while (true) {
			stampaMessaggio("Perfavore inserisci la tua scelta: ");
			 choice = Integer.parseInt(inserisciInput());
			 if (choice >= 1 && choice <= 5) {
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
	
	public static ClienteBean inserisciDatiCliente() {
		ClienteBean bean= new ClienteBean();
		String cf;
		while(true) {
			stampaMessaggio("Inserisci CF cliente: ");
			cf= inserisciInput();
			if(bean.setCf(cf)) {
				break;
			}
			else {
				stampaMessaggio("CF non valido!\n");
			}
		}
		stampaMessaggio("Inserisci nome cliente: ");
		bean.setNome(inserisciInput());
		stampaMessaggio("Inserisci cognome cliente: ");
		bean.setCognome(inserisciInput());
		while(true) {
			stampaMessaggio("Inserisci Data di Nascita Formato: gg-mm-aaaa: ");
			if(bean.setDataDiNascita(inserisciInput())) {
				break;
			}
			else{
				stampaMessaggio("Data non valida!\n");
			}
		}
		String telefono;
		while(true) {
			stampaMessaggio("Inserisci numero di telefono: ");
			telefono= inserisciInput();
			if(bean.inserisciTelefono(telefono)) {
				stampaMessaggio("Finito di inserire i recapiti telefonici? Si/No: ");
				if(inserisciInput().equalsIgnoreCase("Si")) {
					break;
				}
			}
			else {
				stampaMessaggio("Telefono non valido!\n");
			}
		}
		String email;
		while(true) {
			stampaMessaggio("Inserisci email: ");
			email=inserisciInput();
			if(bean.inserisciEmail(email)) {
				stampaMessaggio("Finito di inserire le email? Si/No: ");
				if(inserisciInput().equalsIgnoreCase("Si")) {
					break;
				}
			}
			else {
				stampaMessaggio("Email non valida!\n");
			}
		}
		StringBuilder indirizzo=new StringBuilder();
		stampaMessaggio("Inserisci via: ");
		indirizzo.append(inserisciInput()+" ");
		stampaMessaggio("Inserisci civico: ");
		indirizzo.append(inserisciInput()+" ");
		String cap;
		while(true) {
			stampaMessaggio("Inserisci cap: ");
			cap= inserisciInput();
			if(bean.checkCap(cap)) {
				indirizzo.append(cap);
				break;
			}
			else {
				stampaMessaggio("CAP non valido!\n");
			}
		}
		bean.setIndirizzo(indirizzo.toString());
		stampaMessaggio("Inserisci città: ");
		bean.setCitta(inserisciInput());
		String provincia;
		while(true) {
			stampaMessaggio("Inserisci provincia: ");
			provincia= inserisciInput();
			if(bean.setProvincia(provincia)) {
				break;
			}
			else {
				stampaMessaggio("provincia non valida!\n");
			}
		}
		stampaMessaggio("Inserisci Paese: ");
		bean.setPaese(inserisciInput());
		return bean;
	}
	
	public static OffertaBean inserisciDatiOfferta() {
		OffertaBean bean= new OffertaBean();
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
		stampaMessaggio("Inserisci Nome Offerta: ");
		bean.setNomeOfferta(inserisciInput());
		stampaMessaggio("Inserisci Descrizione Offerta: ");
		bean.setDescrizione(inserisciInput());;
		String data;
		while(true) {
			stampaMessaggio("Inserisci data di scadenza Formato:gg-mm-aaaa: ");
			data=inserisciInput();
			if(bean.setDataScadenza(data)) {
				break;
			}
			else {
				stampaMessaggio("Data non valida!\n");
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