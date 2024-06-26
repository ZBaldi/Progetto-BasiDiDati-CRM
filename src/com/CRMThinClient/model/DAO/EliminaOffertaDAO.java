package com.CRMThinClient.model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.CRMThinClient.exception.DAOException;

public class EliminaOffertaDAO implements GenericProcedureDAO<Void>{

	@Override
	public Void execute(Object... params) throws DAOException, SQLException {
		try {
			Connection conn = ConnectionFactory.getConnection();
			CallableStatement cs = conn.prepareCall("{elimina_offerte(?)}");
			cs.setString(1, (String)params[0]);
			cs.execute();
		}catch(SQLException e) {
			throw new DAOException("Errore eliminazione offerta: "+e.getMessage());
		}
		return null;
	}

}
