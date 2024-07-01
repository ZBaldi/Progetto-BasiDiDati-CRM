package com.CRMThinClient.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.CRMThinClient.exception.DAOException;
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
import com.CRMThinClient.model.Domain.Nota;
import com.CRMThinClient.model.Domain.Offerta;
import com.CRMThinClient.model.Domain.OffertaAccettata;
import com.CRMThinClient.model.Domain.Role;
import com.CRMThinClient.model.Domain.SchemaRegex;
import com.CRMThinClient.model.Domain.ValidatoreCampi;
import com.CRMThinClient.model.bean.NotaBean;
import com.CRMThinClient.model.bean.OffertaAccettataBean;
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
				OperatoreView.stampaMessaggio("Non sono presenti offerte valide nel DB\n");
			}
			else {
				StringBuilder stringa= new StringBuilder();
				for(Offerta o: offerte) {
					stringa.append(o.toString()+"\n\n---------------------------------\n\n");
				}
				OperatoreView.riepilogo(stringa.toString());
			}
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
	}

	public void insertAcceptedOffer() {
		OffertaAccettataBean bean=OperatoreView.inserisciDatiOffertaAccettata();
		OffertaAccettata offerta= new OffertaAccettata(idOperatore,bean.getCliente(),bean.getCodiceOfferta(),bean.getDataContratto());
		OperatoreView.riepilogo(offerta.toString());
		OperatoreView.stampaMessaggio("Vuoi confermare? Si/No: ");
		if(OperatoreView.inserisciInput().equalsIgnoreCase("Si")) {
			saveAcceptedOffer(offerta);
			OperatoreView.stampaMessaggio("Inserimento effettuato!\n");
		}
		else {
			OperatoreView.stampaMessaggio("Inserimento annullato!\n");
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
		NotaBean bean=OperatoreView.inserisciDatiNota();
		String cf= bean.getCodiceCliente();
		Nota nota= new Nota(bean.getCodiceOfferta(),cf,idOperatore);
		nota.inserisciEsito(bean.getEsito());
		nota.inserisciData(true, null);
		if(bean.getSede()!=null) {
			Appuntamento appuntamento= new Appuntamento(cf);
			appuntamento.inserisciSede(bean.getSede());
			appuntamento.inserisciDataEOrario(bean.getDataAppuntamento(), bean.getOrarioAppuntamento());
			nota.allegaAppuntamento(appuntamento);
		}
		OperatoreView.riepilogo(nota.toString());
		OperatoreView.stampaMessaggio("Vuoi confermare? Si/No: ");
		if(OperatoreView.inserisciInput().equalsIgnoreCase("Si")) {
			saveNote(nota);
			OperatoreView.stampaMessaggio("Nota salvata!\n");
		}
		else {
			OperatoreView.stampaMessaggio("Nota scartata!\n");
		}
	}

	public void showNotes() {
		String cf;
		while(true) {
			OperatoreView.stampaMessaggio("Inserisci CF cliente: ");
			cf= OperatoreView.inserisciInput();
			if(ValidatoreCampi.validatore(SchemaRegex.CF,cf)) {
				break;
			}
			else {
				OperatoreView.stampaMessaggio("CF non valido!\n");
			}
		}
		try {
			List<Nota> note=new ListaNoteDAO().execute(cf);
			if(note.isEmpty()==false) {
				StringBuilder stringa= new StringBuilder();
				for(Nota n: note) {
					stringa.append(n.toString()+"\n\n---------------------------------\n\n");
				}
				OperatoreView.riepilogo(stringa.toString());
			}
			else{
				OperatoreView.stampaMessaggio("Il cliente non ha note associate!\n");
			}
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
	}

	public void showCustomers() {
		try {
			List<Cliente> clienti=new MostraClientiDAO().execute();
			if(clienti.isEmpty()) {
				OperatoreView.stampaMessaggio("Non sono presenti clienti nel DB\n");
			}
			else {
				showRecapiti(clienti);
				StringBuilder stringa= new StringBuilder();
				for(Cliente c: clienti) {
					stringa.append(c.toString()+"\n\n---------------------------------\n\n");
				}
				OperatoreView.riepilogo(stringa.toString());
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