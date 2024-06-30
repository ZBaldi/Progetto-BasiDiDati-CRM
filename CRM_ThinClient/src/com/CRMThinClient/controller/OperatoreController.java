package com.CRMThinClient.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.CRMThinClient.exception.DAOException;
import com.CRMThinClient.main.Main;
import com.CRMThinClient.model.DAO.ConnectionFactory;
import com.CRMThinClient.model.DAO.InserisciOffertaAccettataDAO;
import com.CRMThinClient.model.DAO.ListaNoteDAO;
import com.CRMThinClient.model.DAO.MostraClientiDAO;
import com.CRMThinClient.model.DAO.MostraEmailDAO;
import com.CRMThinClient.model.DAO.MostraOfferteDAO;
import com.CRMThinClient.model.DAO.MostraTelefoniDAO;
import com.CRMThinClient.model.DAO.ScritturaNotaDAO;
import com.CRMThinClient.model.Domain.Appuntamento;
import com.CRMThinClient.model.Domain.Cliente;
import com.CRMThinClient.model.Domain.Data;
import com.CRMThinClient.model.Domain.Nota;
import com.CRMThinClient.model.Domain.Offerta;
import com.CRMThinClient.model.Domain.OffertaAccettata;
import com.CRMThinClient.model.Domain.Orario;
import com.CRMThinClient.model.Domain.Role;
import com.CRMThinClient.model.Domain.SchemaRegex;
import com.CRMThinClient.model.Domain.ValidatoreCampi;
import com.CRMThinClient.view.OperatoreView;

public class OperatoreController implements Controller{
	private static String idOperatore;
	
	public static void setId(String s) {
		idOperatore=s;
	}
	
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
                case 2 -> showCustomers();
                case 3 -> showOffers();
                case 4 -> writeNote();
                case 5 -> insertAcceptedOffer();
                case 6 -> System.exit(0);
                default -> throw new RuntimeException("Invalid choice");
            }
        }
	}

	public void showOffers() {
		try {
			List<Offerta> offerte=new MostraOfferteDAO().execute(true);
			if(offerte.isEmpty()) {
				System.out.println("Non sono presenti offerte valide nel DB");
			}
			else {
				for(Offerta o: offerte) {
					OperatoreView.riepilogo(o.toString());
				}
			}
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
	}

	public void insertAcceptedOffer() {
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
		Data data= new Data();
		while(true) {
			System.out.print("Inserisci data di accettazione Formato:gg-mm-aaaa: ");
			try{
				data.inserisciData(scanner.nextLine());
				break;
			}catch(Exception e) {
				System.out.println("Riprova!");
			}
		}
		OffertaAccettata offerta= new OffertaAccettata(idOperatore,cf,codiceOfferta,data);
		OperatoreView.riepilogo(offerta.toString());
		System.out.print("Vuoi confermare? Si/No: ");
		if(scanner.nextLine().equalsIgnoreCase("Si")) {
			saveAcceptedOffer(offerta);
		}
		else {
			System.out.println("Inserimento annullato!");
		}
	}

	private void saveAcceptedOffer(OffertaAccettata offerta) {
		try {
			new InserisciOffertaAccettataDAO().execute(offerta);
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
	}

	public void writeNote() {
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
		Nota nota= new Nota(codiceOfferta,cf,idOperatore);
		System.out.print("Inserisci esito chiamata: ");
		nota.inserisciEsito(scanner.nextLine());
		nota.inserisciData(true, null);
		System.out.print("Vuoi allegare un appuntamento? Si/No: ");
		if(scanner.nextLine().equalsIgnoreCase("Si")) {
			Appuntamento appuntamento= new Appuntamento(cf);
			System.out.print("Inserisci sede: ");
			appuntamento.inserisciSede(scanner.nextLine());
			Data data= new Data();
			while(true) {
				System.out.print("Inserisci data appuntamento Formato:gg-mm-aaaa: ");
				try{
					data.inserisciData(scanner.nextLine());
					break;
				}catch(Exception e) {
					System.out.println("Riprova!");
				}
			}
			Orario orario= new Orario();
			while(true) {
				System.out.print("Inserisci orario appuntamento Formato: hh:mm : ");
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
		OperatoreView.riepilogo(nota.toString());
		System.out.print("Vuoi confermare? Si/No: ");
		if(scanner.nextLine().equalsIgnoreCase("Si")) {
			saveNote(nota);
		}
		else {
			System.out.println("Nota scartata!");
		}
	}

	public void showNotes() {
		Scanner scanner = Main.getScanner();
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
		try {
			List<Nota> note=new ListaNoteDAO().execute(cf);
			if(note.isEmpty()==false) {
				for(Nota n: note) {
					OperatoreView.riepilogo(n.toString());
				}
			}
			else{
				System.out.println("Il cliente non ha note associate!");
			}
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
	}

	public void showCustomers() {
		try {
			List<Cliente> clienti=new MostraClientiDAO().execute();
			if(clienti.isEmpty()) {
				System.out.println("Non sono presenti clienti nel DB");
			}
			else {
				showRecapiti(clienti);
				for(Cliente c: clienti) {
					OperatoreView.riepilogo(c.toString());
				}
			}
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
	}

	private void showRecapiti(List<Cliente> clienti) {
		try {
			for(Cliente c:clienti) {
				new MostraTelefoniDAO().execute(c);
			}
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
		try {
			for(Cliente c :clienti) {
				new MostraEmailDAO().execute(c);
			}
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
	}

	private void saveNote(Nota nota) {
		try {
			new ScritturaNotaDAO().execute(nota);
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
	}
	
}