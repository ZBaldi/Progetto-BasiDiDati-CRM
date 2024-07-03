package com.CRMThinClient.model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.CRMThinClient.exception.DAOException;
import com.CRMThinClient.model.Domain.Offerta;

public class EliminaOffertaDAO implements GenericProcedureDAO<Void>{

	@Override
	public Void execute(Object... params) throws DAOException{  //METODO PER ELIMINARE UN'OFFERTA DAL DB
		try {
			Connection conn = ConnectionFactory.getConnection();
			CallableStatement cs = conn.prepareCall("{call elimina_offerte(?)}");
			Offerta offerta= (Offerta)params[0];
			cs.setString(1, offerta.getCodice());
			cs.execute();
		}catch(SQLException e) {
			throw new DAOException("Errore eliminazione offerta nel DB: "+e.getMessage());
		}
		return null;
	}

}
