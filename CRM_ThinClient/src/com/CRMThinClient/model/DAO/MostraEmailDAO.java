package com.CRMThinClient.model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.CRMThinClient.exception.DAOException;
import com.CRMThinClient.model.Domain.Cliente;

public class MostraEmailDAO implements GenericProcedureDAO<Void>{
	
	@Override
	public Void execute(Object... params) throws DAOException {
		@SuppressWarnings("unchecked")
		List<Cliente> clienti= (ArrayList<Cliente>) params[0];
		try {
			Connection conn = ConnectionFactory.getConnection();
			for(Cliente c: clienti) {
				CallableStatement cs = conn.prepareCall("{mostra_email(?)}");
				cs.setString(1, c.getCf());
				boolean status = cs.execute();
				if(status) {
					ResultSet rs= cs.getResultSet();
					while(rs.next()) {
						String email= rs.getString("NomeEmail");
						c.inserisciEmail(email);
					}
				}
			}
		}catch(SQLException e) {
			throw new DAOException("Problemi nell'ottenere le email dei clienti dal DB: "+e.getMessage());
		}
		return null;
	}
	
}