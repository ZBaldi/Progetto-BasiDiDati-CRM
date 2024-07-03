package com.CRMThinClient.model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.CRMThinClient.exception.DAOException;
import com.CRMThinClient.model.Domain.Cliente;
import com.CRMThinClient.model.Domain.Data;
import com.CRMThinClient.model.Domain.Indirizzo;

public class MostraClientiDAO implements GenericProcedureDAO<List<Cliente>>{

	@Override
	public List<Cliente> execute(Object... params) throws DAOException{  //METODO PER OTTENERE LA LISTA DEI CLIENTI DAL DB
		List<Cliente> clienti= new ArrayList<>();
		try {
			Connection conn = ConnectionFactory.getConnection();
			CallableStatement cs = conn.prepareCall("{call mostra_clienti()}");
			boolean status = cs.execute();
			if(status) {
				ResultSet rs= cs.getResultSet();
				while(rs.next()) {
					String nome=rs.getString("Nome");
					String cf=rs.getString("CodiceFiscale");
					String cognome= rs.getString("Cognome");
					Date data= rs.getDate("DataDiNascita");
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	                String dataStr = sdf.format(data);
	                Data dataDiNascita = new Data();
	                try {
						dataDiNascita.inserisciData(dataStr);
					} catch (Exception e) {
						System.err.println("Errore formattazione data cliente DB!");
					}
	                Date data2= rs.getDate("DataDiRegistrazione");
	                dataStr = sdf.format(data2);
	                Data dataDiRegistrazione = new Data();
	                try {
						dataDiRegistrazione.inserisciData(dataStr);
					} catch (Exception e) {
						System.err.println("Errore formattazione data cliente DB!");
					}
	                String indirizzo= rs.getString("Indirizzo");
	                String citta= rs.getString("Città");
	                String provincia=rs.getString("Provincia");
	                String paese=rs.getString("Paese");
	                Indirizzo luogo= new Indirizzo(indirizzo,citta,provincia,paese);
	                Cliente cliente = new Cliente(nome,cognome,cf,dataDiRegistrazione,dataDiNascita);
	                cliente.inserisciIndirizzo(luogo);
	                clienti.add(cliente);
				}
			}
		}catch(SQLException e) {
			throw new DAOException("Problemi nell'ottenere i clienti dal DB: "+e.getMessage());
		}
		return clienti;
	}

}
