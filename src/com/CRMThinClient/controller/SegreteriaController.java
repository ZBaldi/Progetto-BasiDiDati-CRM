package com.CRMThinClient.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.CRMThinClient.exception.DAOException;
import com.CRMThinClient.main.Main;
import com.CRMThinClient.model.DAO.ConnectionFactory;
import com.CRMThinClient.model.DAO.InserisciOffertaDAO;
import com.CRMThinClient.model.DAO.RegistraClienteDAO;
import com.CRMThinClient.model.Domain.Cliente;
import com.CRMThinClient.model.Domain.Data;
import com.CRMThinClient.model.Domain.Indirizzo;
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
                case 3 -> insertCustomer();
                case 4 -> System.exit(0);
                default -> throw new RuntimeException("Invalid choice");
            }
        }
	}

	public void insertCustomer() {
		Scanner scanner= Main.getScanner();
		System.out.print("Inserisci CF cliente: ");
		String cf= scanner.nextLine();
		System.out.print("Inserisci nome cliente: ");
		String nome= scanner.nextLine();
		System.out.print("Inserisci cognome cliente: ");
		String cognome= scanner.nextLine();
		Data dataDiNascita=new Data();
		while(true) {
			System.out.print("Inserisci Data di Nascita Formato: gg-mm-aaaa: ");
			try{
				dataDiNascita.inserisciData(scanner.nextLine());
				break;
			}catch(Exception e) {
				System.out.println("Riprova!");
			}
		}
		Data dataDiRegistrazione=new Data();
		dataDiRegistrazione.dataCorrente();
		Cliente cliente= new Cliente(nome,cognome,cf,dataDiRegistrazione,dataDiNascita);
		while(true) {
			System.out.print("Inserisci numero di telefono: ");
			cliente.inserisciTelefono(scanner.nextLine());
			System.out.print("Finito di inserire i recapiti telefonici? Si/No: ");
			if(scanner.nextLine().equalsIgnoreCase("Si")) {
				break;
			}
		}
		while(true) {
			System.out.print("Inserisci email: ");
			cliente.inserisciEmail(scanner.nextLine());
			System.out.print("Finito di inserire le email? Si/No: ");
			if(scanner.nextLine().equalsIgnoreCase("Si")) {
				break;
			}
		}
		System.out.print("Inserisci indirizzo: via, civico e cap: ");
		String indirizzo= scanner.nextLine();
		System.out.print("Inserisci città: ");
		String citta= scanner.nextLine();
		System.out.print("Inserisci provincia: ");
		String provincia= scanner.nextLine();
		System.out.print("Inserisci Paese: ");
		String paese= scanner.nextLine();
		Indirizzo luogo= new Indirizzo(indirizzo,citta,provincia,paese);
		cliente.inserisciIndirizzo(luogo);
		SegreteriaView.riepilogo(cliente.toString());
		System.out.print("Vuoi confermare? Si/No: ");
		if(scanner.nextLine().equalsIgnoreCase("Si")) {
			saveCustomer(cliente);
		}
		else {
			System.out.println("Modifiche scartate!");
		}
	}

	private void saveCustomer(Cliente cliente) {
		try {
			new RegistraClienteDAO().execute(cliente);
		}catch(DAOException e) {
			System.err.println("Problemi con la registrazione del cliente nel DB. RIPROVARE!");
		}
	}

	public Object doReport() {
		// TODO Auto-generated method stub
		return null;
	}

	public void insertOffer() {
		Scanner scanner= Main.getScanner();
		System.out.print("Inserisci Codice Offerta: ");
		String codiceOfferta= scanner.nextLine();
		System.out.print("Inserisci Nome Offerta: ");
		String nomeOfferta= scanner.nextLine();
		Offerta offerta= new Offerta(codiceOfferta,nomeOfferta);
		System.out.print("Inserisci Descrizione Offerta: ");
		offerta.inserisciDescrizione(scanner.nextLine());
		Data data=new Data();
		while(true) {
			System.out.print("Inserisci Data di Scadenza Offerta Formato: gg-mm-aaaa: ");
			try{
				data.inserisciData(scanner.nextLine());
				break;
			}catch(Exception e) {
				System.out.println("Riprova!");
			}
		}
		offerta.inserisciScadenza(data);
		SegreteriaView.riepilogo(offerta.toString());
		System.out.print("Vuoi confermare? Si/No: ");
		if(scanner.nextLine().equalsIgnoreCase("Si")) {
			saveOffer(offerta);
		}
		else {
			System.out.println("Offerta scartata!");
		}
	}

	private void saveOffer(Offerta offerta) {
		try {
			new InserisciOffertaDAO().execute(offerta);
		}catch(DAOException e) {
			System.err.println("Problemi con l'inserimento dell'offerta nel DB. RIPROVARE!");
		}
	}
	
}