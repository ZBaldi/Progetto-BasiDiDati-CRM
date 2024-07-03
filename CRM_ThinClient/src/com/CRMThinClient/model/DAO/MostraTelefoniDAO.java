package com.CRMThinClient.model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.CRMThinClient.exception.DAOException;
import com.CRMThinClient.model.Domain.Cliente;

public class MostraTelefoniDAO implements GenericProcedureDAO<Void>{

	@Override
	public Void execute(Object... params) throws DAOException {  //METODO PER OTTENERE LA LISTA DEI NUMERI DI TELEFONI DI UN CLIENTE DAL DB
		Cliente cliente= (Cliente) params[0];
		try {
			Connection conn = ConnectionFactory.getConnection();
			CallableStatement cs = conn.prepareCall("{call mostra_telefoni(?)}");
			cs.setString(1, cliente.getCf());
			boolean status = cs.execute();
			if(status) {
				ResultSet rs= cs.getResultSet();
				while(rs.next()) {
					String telefono= rs.getString("NumeroDiTelefono");
					cliente.inserisciTelefono(telefono);
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new DAOException("Problemi nell'ottenere i telefoni dei clienti dal DB: "+e.getMessage());
		}
		return null;
	}

}