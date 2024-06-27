package com.CRMThinClient.model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.CRMThinClient.exception.DAOException;
import com.CRMThinClient.model.Domain.Offerta;

public class InserisciOffertaDAO implements GenericProcedureDAO<Void>{

	@Override
	public Void execute(Object... params) throws DAOException{
		Offerta offerta= (Offerta) params[0];
		try {
			Connection conn = ConnectionFactory.getConnection();
			CallableStatement cs = conn.prepareCall("{inserisci_offerta(?,?,?,?)}");
			cs.setString(1, offerta.getCodice());
			cs.setString(2, offerta.getNome());
			cs.setString(3, offerta.getDescrizione());
			cs.setDate(4, offerta.getScadenza().getDataForDBMS());
			cs.execute();
		}catch(SQLException e) {
			throw new DAOException("Errore inserimento offerta nel DB: "+e.getMessage());
		}
		return null;
	}

}