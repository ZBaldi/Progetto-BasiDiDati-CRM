package com.CRMThinClient.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.CRMThinClient.exception.DAOException;
import com.CRMThinClient.model.DAO.ConnectionFactory;
import com.CRMThinClient.model.DAO.ReportSegreteriaDAO;
import com.CRMThinClient.model.DAO.EliminaOffertaDAO;
import com.CRMThinClient.model.DAO.InserisciOffertaDAO;
import com.CRMThinClient.model.DAO.MostraOfferteDAO;
import com.CRMThinClient.model.DAO.RegistraClienteDAO;
import com.CRMThinClient.model.Domain.Cliente;
import com.CRMThinClient.model.Domain.Data;
import com.CRMThinClient.model.Domain.Indirizzo;
import com.CRMThinClient.model.Domain.Offerta;
import com.CRMThinClient.model.Domain.Report;
import com.CRMThinClient.model.Domain.Role;
import com.CRMThinClient.model.bean.ClienteBean;
import com.CRMThinClient.model.bean.OffertaBean;
import com.CRMThinClient.view.OperatoreView;
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
		try {
			List<Offerta> offerte=new MostraOfferteDAO().execute(false);
			if(offerte.isEmpty()) {
				SegreteriaView.stampaMessaggio("Non sono presenti offerte scadute da eliminare nel DB\n");
			}
			else {
				StringBuilder stringa=new StringBuilder();
				for(Offerta o: offerte) {
					stringa.append(o.toString()+"\n\n---------------------------------\n\n");
				}
				SegreteriaView.riepilogo(stringa.toString());
				int choice=0;
				while (true) {
					SegreteriaView.stampaMessaggio("Inserisci il numero dell'offerta da eliminare es: >= 1 e <= "+offerte.size()+"(0 per annullare): ");
					 choice = Integer.parseInt(SegreteriaView.inserisciInput());
					 if (choice >= 1 && choice <= offerte.size()) {
					     break;
					 }
					 else if(choice==0) {
						 SegreteriaView.stampaMessaggio("Eliminazione annullata\n");
						 return;
					 }
					 SegreteriaView.stampaMessaggio("Opzione invalida\n");
				}
				new EliminaOffertaDAO().execute(offerte.get(choice-1));
				OperatoreView.stampaMessaggio("Eliminazione effettuata!\n");
			}
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
	}

	public void insertCustomer() {
		ClienteBean bean=SegreteriaView.inserisciDatiCliente();
		Data dataRegistrazione= new Data();
		dataRegistrazione.dataCorrente();
		Cliente cliente=new Cliente(bean.getNome(),bean.getCognome(),bean.getCf(),dataRegistrazione,bean.getDataDiNascita());
		for(String s:bean.getTelefoni()) {
			cliente.inserisciTelefono(s);
		}
		for(String s:bean.getEmail()) {
			cliente.inserisciEmail(s);
		}
		Indirizzo luogo= new Indirizzo(bean.getIndirizzo(),bean.getCitta(),bean.getProvincia(),bean.getPaese());
		cliente.inserisciIndirizzo(luogo);
		SegreteriaView.riepilogo(cliente.toString());
		SegreteriaView.stampaMessaggio("Vuoi confermare? Si/No: ");
		if(SegreteriaView.inserisciInput().equalsIgnoreCase("Si")) {
			saveCustomer(cliente);
			OperatoreView.stampaMessaggio("Cliente registrato!\n");
		}
		else {
			SegreteriaView.stampaMessaggio("Modifiche scartate!\n");
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
		Data dataInizio=new Data();
		while(true) {
			SegreteriaView.stampaMessaggio("Inserisci Data di inizio report Formato: gg-mm-aaaa: ");
			try{
				dataInizio.inserisciData(SegreteriaView.inserisciInput());
				break;
			}catch(Exception e) {
				SegreteriaView.stampaMessaggio("Riprova!\n");
			}
		}
		Data dataFine=new Data();
		while(true) {
			SegreteriaView.stampaMessaggio("Inserisci Data di fine report Formato: gg-mm-aaaa: ");
			try{
				dataFine.inserisciData(SegreteriaView.inserisciInput());
				break;
			}catch(Exception e) {
				SegreteriaView.stampaMessaggio("Riprova!\n");
			}
		}
		try {
			Report report= new ReportSegreteriaDAO().execute(dataInizio.getDataForDBMS(),dataFine.getDataForDBMS());
			if(report.getTotale()==0) {
				SegreteriaView.stampaMessaggio("Nessun cliente è stato contattato nel periodo di tempo scelto\n");
			}
			else {
				SegreteriaView.riepilogo(report.toString());
			}
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
	}

	public void insertOffer() {
		OffertaBean bean= SegreteriaView.inserisciDatiOfferta();
		Offerta offerta= new Offerta(bean.getCodiceOfferta(),bean.getNomeOfferta());
		offerta.inserisciDescrizione(bean.getDescrizione());
		offerta.inserisciScadenza(bean.getDataDiScadenza());
		SegreteriaView.riepilogo(offerta.toString());
		SegreteriaView.stampaMessaggio("Vuoi confermare? Si/No: ");
		if(SegreteriaView.inserisciInput().equalsIgnoreCase("Si")) {
			saveOffer(offerta);
			OperatoreView.stampaMessaggio("Offerta inserita!\n");
		}
		else {
			SegreteriaView.stampaMessaggio("Offerta scartata!\n");
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