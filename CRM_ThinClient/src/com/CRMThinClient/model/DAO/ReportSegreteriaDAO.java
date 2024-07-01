package com.CRMThinClient.model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.CRMThinClient.exception.DAOException;
import com.CRMThinClient.model.Domain.Cliente;
import com.CRMThinClient.model.Domain.Report;
import com.CRMThinClient.model.Domain.TriplaReport;

public class ReportSegreteriaDAO implements GenericProcedureDAO<Report>{

	@Override
	public Report execute(Object... params) throws DAOException{
		Report report= new Report();
		try {
			Connection conn = ConnectionFactory.getConnection();
			CallableStatement cs = conn.prepareCall("{call report_clienti(?,?)}");
			cs.setDate(1, (Date)params[0]);
			cs.setDate(2, (Date)params[1]);
			boolean status = cs.execute();
			if(status) {
				ResultSet rs=cs.getResultSet();
				if (rs.next()) {
		            report.setTotale(rs.getInt("Totale"));
		        }
			}
			status= cs.getMoreResults();
			if(status) {
				ResultSet rs=cs.getResultSet();
				while(rs.next()) {
					String nome=rs.getString("Nome");
					String cf=rs.getString("CodiceFiscale");
					String cognome= rs.getString("Cognome");
					int contattato= rs.getInt("Contattato");
					int accettato= rs.getInt("Accettato");
					Cliente cliente = new Cliente(nome,cognome,cf);
					TriplaReport tripla=new TriplaReport(cliente,contattato,accettato);
					report.inserisciTripla(tripla);
				}
			}
		}catch(SQLException e) {
			throw new DAOException("Problemi nell'effettuare il report dei clienti: "+e.getMessage());
		}
		return report;
	}

}
