package com.CRMThinClient.model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.CRMThinClient.exception.DAOException;
import com.CRMThinClient.model.Domain.Appuntamento;
import com.CRMThinClient.model.Domain.Data;
import com.CRMThinClient.model.Domain.Nota;
import com.CRMThinClient.model.Domain.Orario;

public class ScritturaNotaDAO implements GenericProcedureDAO<Void>{

	@Override
	public Void execute(Object... params) throws DAOException{
		Nota nota= (Nota) params[0];
		Appuntamento appuntamento= nota.getAppuntamento();
		String sede=null;
		Data dataAppuntamento=null;
		Orario orarioAppuntamento=null;
		if(appuntamento!=null) {
			sede=appuntamento.getSede();
			dataAppuntamento=appuntamento.getData();
			orarioAppuntamento=appuntamento.getOrario();
		}
		try {
			Connection conn = ConnectionFactory.getConnection();
			CallableStatement cs = conn.prepareCall("{scrivi_nota(?,?,?,?,?,?,?,?)}");
			cs.setString(1, nota.getCliente());
			cs.setString(2, nota.getOfferta());
			cs.setString(3, nota.getEsito());
			cs.setString(4, nota.getOperatore());
			cs.setDate(5, nota.getData().getDataForDBMS());
			cs.setString(6, sede);
			cs.setDate(7, dataAppuntamento.getDataForDBMS());
			cs.setTime(8, orarioAppuntamento.getTimeForDBMS());
			cs.execute();
		}catch(SQLException e) {
			throw new DAOException("Errore scrittura nota: "+e.getMessage());
		}
		return null;
	}

}