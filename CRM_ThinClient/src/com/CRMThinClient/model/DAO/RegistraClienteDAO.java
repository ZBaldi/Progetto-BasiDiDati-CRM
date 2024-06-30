package com.CRMThinClient.model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.CRMThinClient.exception.DAOException;
import com.CRMThinClient.model.Domain.Cliente;

public class RegistraClienteDAO implements GenericProcedureDAO<Void>{

	@Override
	public Void execute(Object... params) throws DAOException{
		Cliente cliente= (Cliente)params[0];
		StringBuilder telefoni= new StringBuilder();
		for(String s: cliente.getTelefoni()) {
			telefoni.append(s+";");
		}
		telefoni.deleteCharAt(telefoni.length()-1);
		StringBuilder email= new StringBuilder();
		for(String s: cliente.getEmail()) {
			email.append(s+";");
		}
		email.deleteCharAt(email.length()-1);
		try {
			Connection conn = ConnectionFactory.getConnection();
			CallableStatement cs = conn.prepareCall("{call registra_cliente(?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, cliente.getCf().toUpperCase());
			cs.setString(2, cliente.getNome().toUpperCase());
			cs.setString(3, cliente.getCognome().toUpperCase());
			cs.setString(4, cliente.getIndirizzo().getIndirizzo().toUpperCase());
			cs.setString(5, cliente.getIndirizzo().getCitta().toUpperCase());
			cs.setString(6, cliente.getIndirizzo().getProvincia().toUpperCase());
			cs.setString(7, cliente.getIndirizzo().getPaese().toUpperCase());
			cs.setDate(8, cliente.getDataDiNascita().getDataForDBMS());
			cs.setDate(9, cliente.getDataDiRegistrazione().getDataForDBMS());
			cs.setString(10, telefoni.toString());
			cs.setString(11, email.toString());
			cs.execute();
		}catch(SQLException e) {
			throw new DAOException("Errore registrazione cliente nel DB: "+e.getMessage());
		}
		return null;
	}

}
