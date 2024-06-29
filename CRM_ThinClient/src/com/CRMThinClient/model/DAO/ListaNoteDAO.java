package com.CRMThinClient.model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.CRMThinClient.exception.DAOException;
import com.CRMThinClient.model.Domain.Appuntamento;
import com.CRMThinClient.model.Domain.Data;
import com.CRMThinClient.model.Domain.Nota;
import com.CRMThinClient.model.Domain.Orario;


public class ListaNoteDAO implements GenericProcedureDAO<List<Nota>>{

	@Override
	public List<Nota> execute(Object... params) throws DAOException { 
		List<Nota> note= new ArrayList<Nota>();
		try {
			Connection conn = ConnectionFactory.getConnection();
			CallableStatement cs = conn.prepareCall("{call lista_note(?)}");
			cs.setString(1, (String)params[0]);
			boolean status = cs.execute();
			if(status) {
				ResultSet rs= cs.getResultSet();
				while(rs.next()) {
					String cf=rs.getString("CodiceFiscaleCliente");
					String codOfferta= rs.getString("CodiceOfferta");
					String codOperatore= rs.getString("CodiceOperatore");
					Nota nota= new Nota(codOfferta,cf,codOperatore);
					String esito= rs.getString("Esito");
					nota.inserisciEsito(esito);
					Date data= rs.getDate("DataDiModifica");
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	                String dataStr = sdf.format(data);
	                Data dataDiModifica = new Data();
	                try {
						dataDiModifica.inserisciData(dataStr);
					} catch (Exception e) {
						System.err.println("Errore formattazione data nota DB!");
					}
	                nota.inserisciData(false, dataDiModifica);
	                String sede = rs.getString("Sede");
	                if(sede!=null) {
	                	Date dataApp= rs.getDate("DataDiModifica");
		                dataStr = sdf.format(dataApp);
		                Data dataAppuntamento = new Data();
		                try {
		                	dataAppuntamento.inserisciData(dataStr);
		                } catch (Exception e) {
		                	System.err.println("Errore formattazione data appuntamento DB!");
		                }
		                Time orario = rs.getTime("Orario");
		                SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
		                String orarioStr = tf.format(orario);
		                Orario orarioAppuntamento= new Orario();
		                try {
							orarioAppuntamento.inserisciOrario(orarioStr);
						} catch (Exception e) {
							System.err.println("Errore formattazione orario appuntamento DB!");
						}
		                Appuntamento appuntamento=new Appuntamento(cf);
		                appuntamento.inserisciSede(sede);
		                appuntamento.inserisciDataEOrario(dataAppuntamento, orarioAppuntamento);
		                nota.allegaAppuntamento(appuntamento);
	                }
	                note.add(nota);
				}
			}
		}catch(SQLException e) {
			throw new DAOException("Problemi nell'ottenere le note di un cliente dal DB: "+e.getMessage());
		}
		return note;
	}

}