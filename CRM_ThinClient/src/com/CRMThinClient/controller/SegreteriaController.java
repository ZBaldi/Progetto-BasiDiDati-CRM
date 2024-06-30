package com.CRMThinClient.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import com.CRMThinClient.exception.DAOException;
import com.CRMThinClient.main.Main;
import com.CRMThinClient.model.DAO.ConnectionFactory;
import com.CRMThinClient.model.DAO.ReportSegreteriaDAO;
import com.CRMThinClient.model.DAO.EliminaOffertaDAO;
import com.CRMThinClient.model.DAO.InserisciOffertaDAO;
import com.CRMThinClient.model.DAO.MostraOfferteDAO;
import com.CRMThinClient.model.DAO.RegistraClienteDAO;
import com.CRMThinClient.model.Domain.SchemaRegex;
import com.CRMThinClient.model.Domain.Cliente;
import com.CRMThinClient.model.Domain.Data;
import com.CRMThinClient.model.Domain.Indirizzo;
import com.CRMThinClient.model.Domain.Offerta;
import com.CRMThinClient.model.Domain.Report;
import com.CRMThinClient.model.Domain.Role;
import com.CRMThinClient.model.Domain.ValidatoreCampi;
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
                case 4 -> deleteOffer();
                case 5 -> System.exit(0);
                default -> throw new RuntimeException("Invalid choice");
            }
        }
	}

	public void deleteOffer() {
		Scanner scanner= Main.getScanner();
		try {
			List<Offerta> offerte=new MostraOfferteDAO().execute(false);
			if(offerte.isEmpty()) {
				System.out.println("Non sono presenti offerte scadute da eliminare nel DB");
			}
			else {
				for(Offerta o: offerte) {
					SegreteriaView.riepilogo(o.toString());
				}
				int choice=0;
				while (true) {
					 System.out.print("Inserisci il numero dell'offerta da eliminare es: >= 1 e <= "+offerte.size()+": ");
					 choice = Integer.parseInt(scanner.nextLine());
					 if (choice >= 1 && choice <= offerte.size()) {
					     break;
					 }
					 System.out.println("Opzione invalida");
				}
				new EliminaOffertaDAO().execute(offerte.get(choice-1));
			}
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
	}

	public void insertCustomer() {
		Scanner scanner= Main.getScanner();
		String cf;
		while(true) {
			System.out.print("Inserisci CF cliente: ");
			cf= scanner.nextLine();
			if(ValidatoreCampi.validatore(SchemaRegex.CF,cf)) {
				break;
			}
			else {
				System.out.println("CF non valido!");
			}
		}
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
		String telefono;
		while(true) {
			System.out.print("Inserisci numero di telefono: ");
			telefono= scanner.nextLine();
			if(ValidatoreCampi.validatore(SchemaRegex.TELEFONO,telefono)) {
				cliente.inserisciTelefono(telefono);
				System.out.print("Finito di inserire i recapiti telefonici? Si/No: ");
				if(scanner.nextLine().equalsIgnoreCase("Si")) {
					break;
				}
			}
			else {
				System.out.println("Telefono non valido!");
			}
		}
		String email;
		while(true) {
			System.out.print("Inserisci email: ");
			email=scanner.nextLine();
			if(ValidatoreCampi.validatore(SchemaRegex.EMAIL,email)) {
				cliente.inserisciEmail(email);
				System.out.print("Finito di inserire le email? Si/No: ");
				if(scanner.nextLine().equalsIgnoreCase("Si")) {
					break;
				}
			}
			else {
				System.out.println("Email non valida!");
			}
		}
		StringBuilder indirizzo= new StringBuilder();
		System.out.print("Inserisci via: ");
		indirizzo.append(scanner.nextLine()+" ");
		System.out.print("Inserisci civico: ");
		indirizzo.append(scanner.nextLine()+" ");
		String cap;
		while(true) {
			System.out.print("Inserisci cap: ");
			cap= scanner.nextLine();
			if(ValidatoreCampi.validatore(SchemaRegex.CAP,cap)) {
				break;
			}
			else {
				System.out.println("CAP non valido!");
			}
		}
		indirizzo.append(cap);
		System.out.print("Inserisci città: ");
		String citta= scanner.nextLine();
		String provincia;
		while(true) {
			System.out.print("Inserisci provincia: ");
			provincia= scanner.nextLine();
			if(ValidatoreCampi.validatore(SchemaRegex.PROVINCIA,provincia)) {
				break;
			}
			else {
				System.out.println("provincia non valida!");
			}
		}
		System.out.print("Inserisci Paese: ");
		String paese= scanner.nextLine();
		Indirizzo luogo= new Indirizzo(indirizzo.toString(),citta,provincia,paese);
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
			System.err.println(e.getMessage());
		}
	}

	public void doReport() {
		Scanner scanner= Main.getScanner();
		Data dataInizio=new Data();
		while(true) {
			System.out.print("Inserisci Data di inizio report Formato: gg-mm-aaaa: ");
			try{
				dataInizio.inserisciData(scanner.nextLine());
				break;
			}catch(Exception e) {
				System.out.println("Riprova!");
			}
		}
		Data dataFine=new Data();
		while(true) {
			System.out.print("Inserisci Data di fine report Formato: gg-mm-aaaa: ");
			try{
				dataFine.inserisciData(scanner.nextLine());
				break;
			}catch(Exception e) {
				System.out.println("Riprova!");
			}
		}
		try {
			List<Report> reports= new ReportSegreteriaDAO().execute(dataInizio.getDataForDBMS(),dataFine.getDataForDBMS());
			if(reports.isEmpty()) {
				System.out.println("Nessun cliente è stato contattato nel periodo di tempo scelto");
			}
			else {
				StringBuilder reportStr= new StringBuilder();
				reportStr.append("Clienti contattati: "+reports.size()+"\n");
				for(int i=0; i<reports.size();i++) {
					reportStr.append(reports.get(i).toString());
					if(i+1 != reports.size()) {
						reportStr.append("\n");
					}
				}
				SegreteriaView.riepilogo(reportStr.toString());
			}
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
	}

	public void insertOffer() {
		Scanner scanner= Main.getScanner();
		String codiceOfferta;
		while(true) {
			System.out.print("Inserisci Codice Offerta: ");
			codiceOfferta= scanner.nextLine();
			if(ValidatoreCampi.validatore(SchemaRegex.CODICEOFFERTA,codiceOfferta)) {
				break;
			}
			else {
				System.out.println("Codice offerta non valido!");
			}
		}
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
			System.err.println(e.getMessage());
		}
	}
	
}