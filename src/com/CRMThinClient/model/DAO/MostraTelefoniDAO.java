package com.CRMThinClient.model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.CRMThinClient.exception.DAOException;
import com.CRMThinClient.model.Domain.Cliente;

public class MostraTelefoniDAO implements GenericProcedureDAO<Void>{

	@Override
	public Void execute(Object... params) throws DAOException {
		@SuppressWarnings("unchecked")
		List<Cliente> clienti= (ArrayList<Cliente>) params[0];
		try {
			Connection conn = ConnectionFactory.getConnection();
			for(Cliente c: clienti) {
				CallableStatement cs = conn.prepareCall("{mostra_telefoni(?)}");
				cs.setString(1, c.getCf());
				boolean status = cs.execute();
				if(status) {
					ResultSet rs= cs.getResultSet();
					while(rs.next()) {
						String telefono= rs.getString("NumeroDiTelefono");
						c.inserisciTelefono(telefono);
					}
				}
			}
		}catch(SQLException e) {
			throw new DAOException("Problemi nell'ottenere i telefoni dei clienti dal DB: "+e.getMessage());
		}
		return null;
	}

}