package com.CRMThinClient.model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import com.CRMThinClient.exception.DAOException;
import com.CRMThinClient.model.Domain.Credentials;
import com.CRMThinClient.model.Domain.Role;



public class LoginProcedureDAO implements GenericProcedureDAO<Credentials> {
	@Override
	public Credentials execute(Object... params) throws DAOException{
		String username = (String) params[0];
        String password = (String) params[1];
        int role=0;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call login(?,?,?)}");
            cs.setString(1, username);
            cs.setString(2, password);
            cs.registerOutParameter(3, Types.NUMERIC);
            cs.executeQuery();
            role = cs.getInt(3);
        } catch(SQLException e) {
            throw new DAOException("Login error: " + e.getMessage());
        }
        return new Credentials(username, password, Role.fromInt(role));
	}
}
