package com.CRMThinClient.model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import com.CRMThinClient.exception.DAOException;

public class EliminaClienteDAO implements GenericProcedureDAO<Void>{

	@Override
	public Void execute(Object... params) throws DAOException{
		try {
			Connection conn = ConnectionFactory.getConnection();
			CallableStatement cs = conn.prepareCall("{call elimina_cliente(?)}");
			cs.setString(1,(String) params[0]);
			cs.execute();
		}catch(SQLException e) {
			throw new DAOException("Errore eliminazione cliente nel DB: "+e.getMessage());
		}
		return null;
	}

}
