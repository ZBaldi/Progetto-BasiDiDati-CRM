package com.CRMThinClient.model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.CRMThinClient.exception.DAOException;
import com.CRMThinClient.model.Domain.OffertaAccettata;

public class InserisciOffertaAccettataDAO implements GenericProcedureDAO<Void>{

	@Override
	public Void execute(Object... params) throws DAOException{
		OffertaAccettata offerta= (OffertaAccettata) params[0];
		try {
			Connection conn = ConnectionFactory.getConnection();
			CallableStatement cs = conn.prepareCall("{inserisci_offerta_accettata(?,?,?,?)}");
			cs.setString(1, offerta.getCodice());
			cs.setString(2, offerta.getCliente());
			cs.setDate(3, offerta.getDataContratto().getDataForDBMS());
			cs.setString(4, offerta.getOperatore());
			cs.execute();
		}catch(SQLException e) {
			throw new DAOException("Errore inserimento offerta accettata: "+e.getMessage());
		}
		return null;
	}

}
