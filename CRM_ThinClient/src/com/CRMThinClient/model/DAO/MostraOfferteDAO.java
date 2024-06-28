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
import com.CRMThinClient.model.Domain.Data;
import com.CRMThinClient.model.Domain.Offerta;


public class MostraOfferteDAO implements GenericProcedureDAO<List<Offerta>>{

	@Override
	public List<Offerta> execute(Object... params) throws DAOException{
		List<Offerta> offerte= new ArrayList<Offerta>();
		try {
			Connection conn = ConnectionFactory.getConnection();
			CallableStatement cs;
			if((boolean)params[0]) {
				cs = conn.prepareCall("{mostra_offerte()}");
			}
			else {
				cs = conn.prepareCall("{mostra_offerte_scadute()}");
			}
			boolean status = cs.execute();
			if(status) {
				ResultSet rs= cs.getResultSet();
				while(rs.next()) {
					String codiceOfferta=rs.getString("CodiceOfferta");
					String nomeOfferta=rs.getString("NomeOfferta");
					String descrizione= rs.getString("Descrizione");
					Date data= rs.getDate("DataDiScadenza");
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	                String dataStr = sdf.format(data);
	                Data dataDiScadenza = new Data();
	                try {
						dataDiScadenza.inserisciData(dataStr);
					} catch (Exception e) {
						System.err.println("Errore formattazione data offerta DB!");
					}
	                Offerta offerta= new Offerta(codiceOfferta,nomeOfferta);
	                offerta.inserisciDescrizione(descrizione);
	                offerta.inserisciScadenza(dataDiScadenza);
	                offerte.add(offerta);
				}
			}
		}catch(SQLException e) {
			throw new DAOException("Problemi nell'ottenere le offerte dal DB: "+e.getMessage());
		}
		return offerte;
	}

}