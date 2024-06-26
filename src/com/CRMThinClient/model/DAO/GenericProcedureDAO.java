package com.CRMThinClient.model.DAO;

import java.sql.SQLException;

import com.CRMThinClient.exception.DAOException;

public interface GenericProcedureDAO<P> {	
	P execute(Object ... params) throws DAOException, SQLException;
}