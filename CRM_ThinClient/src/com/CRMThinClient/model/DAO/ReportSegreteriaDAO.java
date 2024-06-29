package com.CRMThinClient.model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.CRMThinClient.exception.DAOException;
import com.CRMThinClient.model.Domain.Cliente;
import com.CRMThinClient.model.Domain.Report;

public class ReportSegreteriaDAO implements GenericProcedureDAO<List<Report>>{

	@Override
	public List<Report> execute(Object... params) throws DAOException{
		List<Report> reports= new ArrayList<Report>();
		try {
			Connection conn = ConnectionFactory.getConnection();
			CallableStatement cs = conn.prepareCall("{call report_clienti(?,?)}");
			cs.setDate(1, (Date)params[0]);
			cs.setDate(2, (Date)params[1]);
			boolean status = cs.execute();
			if(status) {
				ResultSet rs= cs.getResultSet();
				while(rs.next()) {
					String nome=rs.getString("Nome");
					String cf=rs.getString("CodiceFiscale");
					String cognome= rs.getString("Cognome");
					int contattato= rs.getInt("Contattato");
					Cliente cliente = new Cliente(nome,cognome,cf);
					Report report = new Report(cliente,contattato);
					reports.add(report);
				}
			}
		}catch(SQLException e) {
			throw new DAOException("Problemi nell'effettuare il report dei clienti: "+e.getMessage());
		}
		return reports;
	}

}
