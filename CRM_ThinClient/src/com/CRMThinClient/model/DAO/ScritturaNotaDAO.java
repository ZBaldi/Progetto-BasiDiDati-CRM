package com.CRMThinClient.model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import com.CRMThinClient.exception.DAOException;
import com.CRMThinClient.model.Domain.Appuntamento;
import com.CRMThinClient.model.Domain.Nota;

public class ScritturaNotaDAO implements GenericProcedureDAO<Void>{

	@Override
	public Void execute(Object... params) throws DAOException{  //METODO PER INSERIRE UNA NOTA NEL DB
		Nota nota= (Nota) params[0];
		Appuntamento appuntamento= nota.getAppuntamento();
		String sede=null;
		Date dataAppuntamento=null;
		Time orarioAppuntamento=null;
		if(appuntamento!=null) {
			sede=appuntamento.getSede().toUpperCase();
			dataAppuntamento=appuntamento.getData().getDataForDBMS();
			orarioAppuntamento=appuntamento.getOrario().getTimeForDBMS();
		}
		try {
			Connection conn = ConnectionFactory.getConnection();
			CallableStatement cs = conn.prepareCall("{call scrivi_nota(?,?,?,?,?,?,?,?)}");
			cs.setString(1, nota.getCliente().toUpperCase());
			cs.setString(2, nota.getOfferta().toUpperCase());
			cs.setString(3, nota.getEsito().toUpperCase());
			cs.setString(4, nota.getOperatore().toUpperCase());
			cs.setDate(5, nota.getData().getDataForDBMS());
			cs.setString(6, sede);
			cs.setDate(7, dataAppuntamento);
			cs.setTime(8, orarioAppuntamento);
			cs.execute();
		}catch(SQLException e) {
			throw new DAOException("Errore scrittura nota nel DB: "+e.getMessage());
		}
		return null;
	}

}