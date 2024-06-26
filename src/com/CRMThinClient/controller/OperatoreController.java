package com.CRMThinClient.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.CRMThinClient.main.Main;
import com.CRMThinClient.model.DAO.ConnectionFactory;
import com.CRMThinClient.model.Domain.Appuntamento;
import com.CRMThinClient.model.Domain.Data;
import com.CRMThinClient.model.Domain.Nota;
import com.CRMThinClient.model.Domain.OffertaAccettata;
import com.CRMThinClient.model.Domain.Orario;
import com.CRMThinClient.model.Domain.Role;
import com.CRMThinClient.view.OperatoreView;

public class OperatoreController implements Controller{

	@Override
	public void start() {
		try {
            ConnectionFactory.changeRole(Role.OPERATORE);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
		while(true) {
            int choice;
            try {
                choice = OperatoreView.showMenu();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }

            switch(choice) {
                case 1 -> showNotes();
                case 2 -> writeNote();
                case 3 -> insertAcceptedOffer();
                case 4 -> System.exit(0);
                default -> throw new RuntimeException("Invalid choice");
            }
        }
	}

	public void insertAcceptedOffer() {
		Scanner scanner= Main.getScanner();
		System.out.println("Inserisci codice offerta: ");
		String codOfferta=scanner.nextLine();
		System.out.println("Inserisci codice fiscale cliente: ");
		String codCliente=scanner.nextLine();
		System.out.println("Inserisci codice operatore: ");
		String codOperatore=scanner.nextLine();
		Data data= new Data();
		while(true) {
			System.out.println("Inserisci data di accettazione Formato:gg-mm-aaaa: ");
			try{
				data.inserisciData(scanner.nextLine());
				break;
			}catch(Exception e) {
				System.out.println("Riprova!");
			}
		}
		OffertaAccettata offerta= new OffertaAccettata(codOperatore,codCliente,codOfferta,data);
		OperatoreView.riepilogoOffertaAccettata(offerta.toString());
		System.out.println("Vuoi confermare? Si/No: ");
		if(scanner.nextLine().equalsIgnoreCase("Si")) {
			saveAcceptedOffer(offerta);
		}
		else {
			System.out.println("Inserimento annullato!");
		}
	}

	private void saveAcceptedOffer(OffertaAccettata offerta) {
		// INTERAGISCE CON IL DAO DA IMPLEMENTARE		
	}

	public void writeNote() {
		Scanner scanner= Main.getScanner();
		System.out.println("Inserisci codice offerta: ");
		String codOfferta=scanner.nextLine();
		System.out.println("Inserisci codice fiscale cliente: ");
		String codCliente=scanner.nextLine();
		System.out.println("Inserisci codice operatore: ");
		String codOperatore=scanner.nextLine();
		Nota nota= new Nota(codOfferta,codCliente,codOperatore);
		System.out.println("Inserisci esito chiamata: ");
		nota.inserisciEsito(scanner.nextLine());
		System.out.println("Vuoi allegare un appuntamento? Si/No: ");
		if(scanner.nextLine().equalsIgnoreCase("Si")) {
			Appuntamento appuntamento= new Appuntamento(codCliente);
			System.out.println("Inserisci sede: ");
			appuntamento.inserisciSede(scanner.nextLine());
			Data data= new Data();
			while(true) {
				System.out.println("Inserisci data appuntamento Formato:gg-mm-aaaa: ");
				try{
					data.inserisciData(scanner.nextLine());
					break;
				}catch(Exception e) {
					System.out.println("Riprova!");
				}
			}
			Orario orario= new Orario();
			while(true) {
				System.out.println("Inserisci orario appuntamento Formato: hh:mm : ");
				try{
					orario.inserisciOrario(scanner.nextLine());
					break;
				}catch(Exception e) {
					System.out.println("Riprova!");
				}
			}
			appuntamento.inserisciDataEOrario(data, orario);
			nota.allegaAppuntamento(appuntamento);
		}
		OperatoreView.riepilogoNota(nota.toString());
		System.out.println("Vuoi confermare? Si/No: ");
		if(scanner.nextLine().equalsIgnoreCase("Si")) {
			saveNote(nota);
		}
		else {
			System.out.println("Nota scartata!");
		}
	}

	public void showNotes() {
		//PARTE DAO TROVA NOTE DI QUEL CLIENTE
		//CICLO CHIAMA VIEW OPERATORE CHE RESTITUISCE NOTE!
	}

	private void saveNote(Nota nota) {
		//PARTE DAO SALVATAGGIO NOTA DA IMPLEMENTARE
	}
	
}