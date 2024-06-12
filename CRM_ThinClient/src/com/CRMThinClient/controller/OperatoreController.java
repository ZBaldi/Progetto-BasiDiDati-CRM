package com.CRMThinClient.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.CRMThinClient.main.Main;
import com.CRMThinClient.model.DAO.ConnectionFactory;
import com.CRMThinClient.model.Domain.Appuntamento;
import com.CRMThinClient.model.Domain.Data;
import com.CRMThinClient.model.Domain.Nota;
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
                case 3 -> System.exit(0);
                default -> throw new RuntimeException("Invalid choice");
            }
        }
	}

	public void writeNote() {
		Scanner scanner= Main.getScanner();
		System.out.println("Inserisci codice offerta: ");
		String codOfferta=scanner.nextLine();
		System.out.println("Inserisci codice cliente: ");
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
			System.out.println("Inserisci data Formato:gg-mm-aaaa: ");
			data.inserisciData(scanner.nextLine());
			System.out.println("Inserisci orario appuntamento Formato: hh:mm : ");
			Orario orario= new Orario();
			orario.inserisciOrario(scanner.nextLine());
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