package br.com.citframework.integracao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.citframework.util.Constantes;

public class MetaDataDao extends DaoTransactDefaultImpl{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6799223758541736527L;


	public MetaDataDao() {
		super(Constantes.getValue("CONEXAO_DEFAULT"), null);
	}

	public Class getBean() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection getFields() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public Collection getCamposByTabela(String nomeTabela) throws Exception{
		
		TransactionControler tc =  getTransactionControler();
		Connection con = (Connection)tc.getTransactionObject();
		DatabaseMetaData dbmd = con.getMetaData();
		ResultSet rs = dbmd.getColumns(null, null, nomeTabela, null);
		List lista = new ArrayList();
		while(rs.next()){
			lista.add(rs.getString("COLUMN_NAME"));	
		}
        try{
            rs.close();

     } catch (Exception eX) {
     }
        try{
    		con.close();
     } catch (Exception eX) {
     }

		con = null;
		rs = null;
		return lista;
		
		
		
		
	}
	
	
	

}
