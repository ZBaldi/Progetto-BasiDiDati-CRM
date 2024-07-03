package com.CRMThinClient.model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.CRMThinClient.exception.DAOException;
import com.CRMThinClient.model.Domain.Offerta;

public class InserisciOffertaDAO implements GenericProcedureDAO<Void>{

	@Override
	public Void execute(Object... params) throws DAOException{  //METODO PER INSERIRE UN'OFFERTA NEL DB
		Offerta offerta= (Offerta) params[0];
		try {
			Connection conn = ConnectionFactory.getConnection();
			CallableStatement cs = conn.prepareCall("{call inserisci_offerta(?,?,?,?)}");
			cs.setString(1, offerta.getCodice().toUpperCase());
			cs.setString(2, offerta.getNome().toUpperCase());
			cs.setString(3, offerta.getDescrizione().toUpperCase());
			cs.setDate(4, offerta.getScadenza().getDataForDBMS());
			cs.execute();
		}catch(SQLException e) {
			throw new DAOException("Errore inserimento offerta nel DB: "+e.getMessage());
		}
		return null;
	}

}