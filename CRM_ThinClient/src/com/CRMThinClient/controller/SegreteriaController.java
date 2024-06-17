package com.CRMThinClient.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.CRMThinClient.main.Main;
import com.CRMThinClient.model.DAO.ConnectionFactory;
import com.CRMThinClient.model.Domain.Data;
import com.CRMThinClient.model.Domain.Offerta;
import com.CRMThinClient.model.Domain.Role;
import com.CRMThinClient.view.SegreteriaView;

public class SegreteriaController implements Controller{

	@Override
	public void start() {
		try {
            ConnectionFactory.changeRole(Role.SEGRETERIA);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
		while(true) {
            int choice;
            try {
                choice = SegreteriaView.showMenu();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }

            switch(choice) {
                case 1 -> insertOffer();
                case 2 -> doReport();
                case 3 -> System.exit(0);
                default -> throw new RuntimeException("Invalid choice");
            }
        }
	}

	public Object doReport() {
		// TODO Auto-generated method stub
		return null;
	}

	public void insertOffer() {
		Scanner scanner= Main.getScanner();
		System.out.println("Inserisci Codice Offerta: ");
		String codiceOfferta= scanner.nextLine();
		System.out.println("Inserisci Nome Offerta: ");
		String nomeOfferta= scanner.nextLine();
		Offerta offerta= new Offerta(codiceOfferta,nomeOfferta);
		System.out.println("Inserisci Descrizione Offerta: ");
		offerta.inserisciDescrizione(scanner.nextLine());
		Data data=new Data();
		while(true) {
			System.out.println("Inserisci Data di Scadenza Offerta Formato: gg-mm-aaaa: ");
			try{
				data.inserisciData(scanner.nextLine());
				break;
			}catch(Exception e) {
				System.out.println("Riprova!");
			}
		}
		offerta.inserisciScadenza(data);
		SegreteriaView.riepilogoOfferta(offerta.toString());
		System.out.println("Vuoi confermare? Si/No: ");
		if(scanner.nextLine().equalsIgnoreCase("Si")) {
			saveOffer(offerta);
		}
		else {
			System.out.println("Offerta scartata!");
		}
	}

	private void saveOffer(Offerta offerta) {
		// PARTE COMUNICANTE CON DAO
	}

}