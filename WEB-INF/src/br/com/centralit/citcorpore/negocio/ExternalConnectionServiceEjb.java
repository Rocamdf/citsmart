package br.com.centralit.citcorpore.negocio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.ExternalConnectionDTO;
import br.com.centralit.citcorpore.bean.ImportManagerDTO;
import br.com.centralit.citcorpore.integracao.ExternalConnectionDao;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.integracao.VisaoDao;
import br.com.centralit.citcorpore.metainfo.util.DataBaseMetaDadosUtil;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilNumbersAndDecimals;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({"serial","rawtypes","unused"})
public class ExternalConnectionServiceEjb extends CrudServicePojoImpl implements ExternalConnectionService {
	protected CrudDAO getDao() throws ServiceException {
		return new ExternalConnectionDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	public Collection getTables(Integer idExternalConnection) throws Exception {
		Collection colTables = null;
		ExternalConnectionDao externalConnectionDao = new ExternalConnectionDao();
		ExternalConnectionDTO externalConnectionDTO = new ExternalConnectionDTO();
		externalConnectionDTO.setIdExternalConnection(idExternalConnection);
		externalConnectionDTO = (ExternalConnectionDTO) externalConnectionDao.restore(externalConnectionDTO);
		if (externalConnectionDTO != null) {
			String url = UtilStrings.nullToVazio(externalConnectionDTO.getUrlJdbc()).trim();
			if (externalConnectionDTO.getJdbcDbName() != null) {
				url = url + UtilStrings.nullToVazio(externalConnectionDTO.getJdbcDbName()).trim();
			}
			String driver = UtilStrings.nullToVazio(externalConnectionDTO.getJdbcDriver());
			String userName = UtilStrings.nullToVazio(externalConnectionDTO.getJdbcUser());
			String password = UtilStrings.nullToVazio(externalConnectionDTO.getJdbcPassword());
			String schemadb = UtilStrings.nullToVazio(externalConnectionDTO.getSchemadb());

			Connection conn = null;
			try {
				Class.forName(driver).newInstance();
				conn = DriverManager.getConnection(url, userName, password);
				System.out.println("CITSMART - ExternalConnectionServiceEjb - Connected to the database - "	+ url);

				DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
				colTables = dataBaseMetaDadosUtil.readTables(conn, schemadb, schemadb, null, false);

				conn.close();
				conn = null;
				System.out.println("CITSMART - ExternalConnectionServiceEjb - Connected to the database - "	+ url);
			} catch (Exception e) {
				System.out.println("CITSMART - ExternalConnectionServiceEjb - PROBLEM - Connected to the database - " + url);
				e.printStackTrace();
				throw e;
			}
		}
		return colTables;
	}
	
	public Collection getLocalTables() throws Exception {
		Collection colTables = null;
		VisaoDao visaoDao = new VisaoDao();
		Connection con = (Connection) visaoDao.getTransactionControler().getTransactionObject();
		String DB_SCHEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DB_SCHEMA, "");
		if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.SQLSERVER)) {
			DB_SCHEMA = null;
		} else if (DB_SCHEMA == null || DB_SCHEMA.trim().equalsIgnoreCase("")){
		    DB_SCHEMA = "citsmart";
		}
		DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
		colTables = dataBaseMetaDadosUtil.readTables(con, DB_SCHEMA, DB_SCHEMA, null, false);
		
		con.close();
		con = null;		
		return colTables;
	}
	
	public Collection getFieldsTable(Integer idExternalConnection, String tableName) throws Exception {
		Collection colTables = null;
		ExternalConnectionDao externalConnectionDao = new ExternalConnectionDao();
		ExternalConnectionDTO externalConnectionDTO = new ExternalConnectionDTO();
		externalConnectionDTO.setIdExternalConnection(idExternalConnection);
		externalConnectionDTO = (ExternalConnectionDTO) externalConnectionDao.restore(externalConnectionDTO);
		if (externalConnectionDTO != null) {
			String url = UtilStrings.nullToVazio(externalConnectionDTO.getUrlJdbc()).trim();
			if (externalConnectionDTO.getJdbcDbName() != null) {
				url = url + UtilStrings.nullToVazio(externalConnectionDTO.getJdbcDbName()).trim();
			}
			String driver = UtilStrings.nullToVazio(externalConnectionDTO.getJdbcDriver());
			String userName = UtilStrings.nullToVazio(externalConnectionDTO.getJdbcUser());
			String password = UtilStrings.nullToVazio(externalConnectionDTO.getJdbcPassword());
			String schemadb = UtilStrings.nullToVazio(externalConnectionDTO.getSchemadb());

			Connection conn = null;
			try {
				Class.forName(driver).newInstance();
				conn = DriverManager.getConnection(url, userName, password);
				System.out.println("CITSMART - ExternalConnectionServiceEjb - Connected to the database - "	+ url);

				DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
				colTables = dataBaseMetaDadosUtil.readTables(conn, schemadb, schemadb, tableName, false);

				conn.close();
				conn = null;
				System.out.println("CITSMART - ExternalConnectionServiceEjb - Connected to the database - "	+ url);
			} catch (Exception e) {
				System.out.println("CITSMART - ExternalConnectionServiceEjb - PROBLEM - Connected to the database - " + url);
				e.printStackTrace();
				throw e;
			}
		}
		if (colTables != null && colTables.size() > 0){
			ObjetoNegocioDTO objNegocioDto = (ObjetoNegocioDTO) ((List)colTables).get(0);
			return objNegocioDto.getColCampos();
		}
		return null;		
	}
	public Collection getFieldsLocalTable(String tableName) throws Exception {
		Collection colTables = null;
		VisaoDao visaoDao = new VisaoDao();
		Connection con = (Connection) visaoDao.getTransactionControler().getTransactionObject();
		String DB_SCHEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DB_SCHEMA, "");
		if (DB_SCHEMA == null || DB_SCHEMA.trim().equalsIgnoreCase("")){
		    DB_SCHEMA = "citsmart";
		}
		DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
		colTables = dataBaseMetaDadosUtil.readTables(con, DB_SCHEMA, DB_SCHEMA, tableName, false);
		
		con.close();
		con = null;	
		if (colTables != null && colTables.size() > 0){
			ObjetoNegocioDTO objNegocioDto = (ObjetoNegocioDTO) ((List)colTables).get(0);
			return objNegocioDto.getColCampos();
		}
		return null;		
	}
	
	public Connection getConnectionExternal(Integer idExternalConnection) throws Exception {
		Collection colTables = null;
		ExternalConnectionDao externalConnectionDao = new ExternalConnectionDao();
		ExternalConnectionDTO externalConnectionDTO = new ExternalConnectionDTO();
		externalConnectionDTO.setIdExternalConnection(idExternalConnection);
		externalConnectionDTO = (ExternalConnectionDTO) externalConnectionDao.restore(externalConnectionDTO);
		Connection conn = null;
		if (externalConnectionDTO != null) {
			String url = UtilStrings.nullToVazio(externalConnectionDTO.getUrlJdbc()).trim();
			if (externalConnectionDTO.getJdbcDbName() != null) {
				url = url + UtilStrings.nullToVazio(externalConnectionDTO.getJdbcDbName()).trim();
			}
			String driver = UtilStrings.nullToVazio(externalConnectionDTO.getJdbcDriver());
			String userName = UtilStrings.nullToVazio(externalConnectionDTO.getJdbcUser());
			String password = UtilStrings.nullToVazio(externalConnectionDTO.getJdbcPassword());

			try {
				Class.forName(driver).newInstance();
				conn = DriverManager.getConnection(url, userName, password);
				System.out.println("CITSMART - ExternalConnectionServiceEjb - Connected to the database - "	+ url);
				
				return conn;
			} catch (Exception e) {
				System.out.println("CITSMART - ExternalConnectionServiceEjb - PROBLEM - Connected to the database - " + url);
				e.printStackTrace();
				throw e;
			}
		}
		return conn;
	}
	public void processImport(ImportManagerDTO importManagerDTO, ArrayList colMatrizTratada) throws Exception {
		ExternalConnectionDao externalConnectionDao = new ExternalConnectionDao();
		String sqlOrigem = "";
		String sqlDestino = "";
		String sqlDestinoValues = "";
		for (Iterator it = colMatrizTratada.iterator(); it.hasNext();){
			HashMap mapItem = (HashMap)it.next();
			String idOrigem = (String) mapItem.get("IDORIGEM");
			
			if (!idOrigem.equalsIgnoreCase("##AUTO##")){
				if (!sqlOrigem.trim().equalsIgnoreCase("")){
					sqlOrigem += ",";
				}
				sqlOrigem += idOrigem;
			}
			
			String idDestino = (String) mapItem.get("IDDESTINO");
			if (!sqlDestino.trim().equalsIgnoreCase("")){
				sqlDestino += ",";
				sqlDestinoValues += ",";
			}			
			sqlDestino += idDestino;
			sqlDestinoValues += "?";
		}	
		sqlOrigem = "SELECT " + sqlOrigem + " FROM " + importManagerDTO.getTabelaOrigem();
		sqlDestino = "INSERT INTO " + importManagerDTO.getTabelaDestino() + " (" + sqlDestino + ") VALUES (" + sqlDestinoValues + ")";		
		
		Connection conn = getConnectionExternal(importManagerDTO.getIdExternalConnection());
		ResultSet rs = null;
		PreparedStatement prepStmtOrigem = null;
		try{
			prepStmtOrigem = conn.prepareStatement(sqlOrigem);
			rs = prepStmtOrigem.executeQuery();
			synchronized (colMatrizTratada) {
				while (rs.next()) {
					
					List lstParms = new ArrayList();
					int i = 0;
					Object[] objs = new Object[colMatrizTratada.size()];
					for (Iterator it = colMatrizTratada.iterator(); it.hasNext();){
						Object o = null;
						HashMap mapItem = (HashMap)it.next();
						String idOrigem = (String) mapItem.get("IDORIGEM");
						String idDestino = (String) mapItem.get("IDDESTINO");
						String script = (String) mapItem.get("SCRIPT");
						
						if (idOrigem.equalsIgnoreCase("##AUTO##")){
							o = externalConnectionDao.getLastValueFromDestino(importManagerDTO, idDestino);
						}else{
							o = rs.getObject(i);
						}
						if (script != null && !script.trim().equalsIgnoreCase("")){
							org.mozilla.javascript.Context cx = org.mozilla.javascript.Context.enter();
							org.mozilla.javascript.Scriptable scope = cx.initStandardObjects();
							
							String sourceName = this.getClass().getName() + "_Script";
							
							script = script.replaceAll("TEXTSEARCH", "utilStrings.generateNomeBusca");
							script = script.replaceAll("GETFIELD", "hashMapUtil.getFieldInHash");
							
							String compl = "var importNames = JavaImporter();\n";

							compl += "importNames.importPackage(Packages.br.com.citframework.util);\n";
							compl += "importNames.importPackage(Packages.br.com.centralit.citcorpore.metainfo.util);\n";
							
							script = compl + "\n" + script;
							
							scope.put("object", scope, o);
							scope.put("mapItem", scope, mapItem);
							scope.put("importManagerDTO", scope, importManagerDTO);
							scope.put("utilStrings", scope, new UtilStrings());
							scope.put("utilNumbersAndDecimals", scope, new UtilNumbersAndDecimals());
							scope.put("utilDatas", scope, new UtilDatas());
							
							Object result = cx.evaluateString(scope, script, sourceName, 1, null);	
							
							o = importManagerDTO.getResult();
						}
						objs[i] = o;
						
						i++;
					}
					externalConnectionDao.executeSQLUpdate(sqlDestino, objs);
				}
			}
			
			prepStmtOrigem.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				rs.close();
			}catch(Exception ex){}
			try{
				prepStmtOrigem.close();
			}catch(Exception ex){}			
			try{
				conn.close();
			}catch(Exception ex){}	
			rs = null;
			prepStmtOrigem = null;				
			conn = null;				
		}
	}
}
