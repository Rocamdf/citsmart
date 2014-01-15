package br.com.citframework.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.util.AdaptacaoBD;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.ParametroDTO;
import br.com.citframework.integracao.ParametroDao;

@SuppressWarnings("rawtypes")
public class SQLConfig {
	public static String strSGBDPrincipal = null;
	public static final String MYSQL = "MYSQL";
	public static final String ORACLE = "ORACLE";
	public static final String DB2 = "DB2";
	public static final String SQLSERVER = "SQLSERVER";
	public static final String POSTGRESQL = "POSTGRES";

	public static String traduzSQL(String sql) {
		String sqlAux = sql;

		if (strSGBDPrincipal == null) {
			if (CITCorporeUtil.SGBD_PRINCIPAL == null || CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase("")) {
				AdaptacaoBD.getBancoUtilizado(); // este metodo atualizada o valor de CITCorporeUtil.SGBD_PRINCIPAL
			}
			strSGBDPrincipal = CITCorporeUtil.SGBD_PRINCIPAL;
			if (strSGBDPrincipal != null) {
				strSGBDPrincipal = strSGBDPrincipal.toUpperCase();
			}
			strSGBDPrincipal = UtilStrings.nullToVazio(strSGBDPrincipal).trim();
			System.out.println("CITFRAMEWORK --->>>> IDENTIFICANDO O SGBD: " + strSGBDPrincipal);
		}
		if (strSGBDPrincipal == null || strSGBDPrincipal.trim().equalsIgnoreCase("")) {
			strSGBDPrincipal = ORACLE;
			System.out.println("CITFRAMEWORK --->>>> :::::::::::::::::: IDENTIFICANDO O SGBD: " + strSGBDPrincipal);
		}
		// --- INFORMACOES PARA DB2 ----
		if (strSGBDPrincipal.equalsIgnoreCase(DB2)) {
			sqlAux = sqlAux.replaceAll("UPPER", "UCASE");
			sqlAux = sqlAux.replaceAll("\\{DATAATUAL\\}", "CURRENT_DATE");
			sqlAux = sqlAux.replaceAll(" \\_AS\\_ ", " AS "); // TIRA A PALAVRA AS DOS SQLs
			sqlAux = sqlAux.replaceAll(" \\_as\\_ ", " AS "); // TIRA A PALAVRA AS DOS SQLs
			sqlAux = sqlAux.replaceAll(" \\_As\\_ ", " AS "); // TIRA A PALAVRA AS DOS SQLs
			sqlAux = sqlAux.replaceAll(" \\_aS\\_ ", " AS "); // TIRA A PALAVRA AS DOS SQLs
		}

		// --- INFORMACOES PARA ORACLE ----
		if (strSGBDPrincipal.equalsIgnoreCase(ORACLE)) {
			sqlAux = sqlAux.toUpperCase();
			sqlAux = sqlAux.replaceAll("UCASE", "UPPER");
			sqlAux = sqlAux.replaceAll("\\{DATAATUAL\\}", "CURRENT_DATE");
			sqlAux = sqlAux.replaceAll("'9999-12-31'", "TO_DATE('31/12/9999')");
			if (sqlAux.contains("CAST(DOCUMENTACAO AS VARCHAR2(4000)")) {
				sqlAux = sqlAux.replaceAll(" AS ", " AS "); // TIRA A PALAVRA AS DOS SQLs
			}else{
				sqlAux = sqlAux.replaceAll(" AS ", " ");
			}
			sqlAux = sqlAux.replaceAll(" \\_AS\\_ ", " AS "); // TIRA A PALAVRA AS DOS SQLs
			sqlAux = sqlAux.replaceAll("\"", "'");
			sqlAux = sqlAux.replaceAll("SUBSTRING", "SUBSTR");
			sqlAux = sqlAux.replaceAll("!=", "<>"); // TRATA A SINTAXE DE DIFERENTE
			if (sqlAux.indexOf("FETCH FIRST 1 ROWS ONLY") > -1) {
				sqlAux = sqlAux.replaceAll(" FETCH FIRST 1 ROWS ONLY ", " "); // TIRA A PALAVRA AS DOS SQLs
				if (sqlAux.indexOf(" ORDER ") > -1) {
					if (sqlAux.indexOf(" WHERE ") > -1) {
						sqlAux = sqlAux.replaceAll(" ORDER ", " AND ROWNUM <= 1 ORDER "); // TIRA A PALAVRA AS DOS SQLs
					} else {
						sqlAux = sqlAux.replaceAll(" ORDER ", " WHERE ROWNUM <= 1 ORDER "); // TIRA A PALAVRA AS DOS SQLs
					}
				} else {
					if (sqlAux.indexOf(" WHERE ") > -1) {
						sqlAux = sqlAux + " AND ROWNUM <= 1";
					} else {
						sqlAux = sqlAux + " WHERE ROWNUM <= 1";
					}
				}
			}
			
			if(!StringUtils.contains(sqlAux, "'DELETED'")){
				Pattern pat = Pattern.compile("[\\w*\\.]*DELETED");
				Matcher mat = pat.matcher(sqlAux);
				if (mat.find() && StringUtils.contains(sqlAux, "SELECT")) {
					sqlAux = sqlAux.replaceAll("[\\w*\\.]*DELETED", "UPPER(" + mat.group() + ")");
				}
			}
			sqlAux = sqlAux.toUpperCase();
		}

		// --- INFORMACOES PARA POSTGRESQL ----
		if (strSGBDPrincipal.equalsIgnoreCase(POSTGRESQL)) {
			sqlAux = sqlAux.replaceAll("SYSDATE", "now()");
			sqlAux = sqlAux.replaceAll(" \\_AS\\_ ", " AS "); // TIRA A PALAVRA AS DOS SQLs
			sqlAux = sqlAux.replaceAll(" \\_as\\_ ", " AS "); // TIRA A PALAVRA AS DOS SQLs
			sqlAux = sqlAux.replaceAll(" \\_As\\_ ", " AS "); // TIRA A PALAVRA AS DOS SQLs
			sqlAux = sqlAux.replaceAll(" \\_aS\\_ ", " AS "); // TIRA A PALAVRA AS DOS SQLs
			sqlAux = sqlAux.replaceAll("\\{DATAATUAL\\}", "now()");
			sqlAux = sqlAux.replaceAll("TRUNC", "");
			sqlAux = sqlAux.replaceAll("\"", "'");
			sqlAux = sqlAux.replaceAll("UCASE", "UPPER");
			sqlAux = sqlAux.replaceAll("'9999-12-31'", "TO_DATE('31/12/9999')");

			if(!StringUtils.contains(sqlAux, "'DELETED'")){
				Pattern pat = Pattern.compile("[\\w*\\.]*DELETED");
				Matcher mat = pat.matcher(sqlAux);
				if (mat.find() && (!StringUtils.contains(sqlAux, "UPDATE") && !StringUtils.contains(sqlAux, "INSERT") )) {
					sqlAux = sqlAux.replaceAll("[\\w*\\.]*DELETED", "UPPER(" + mat.group() + ")");
				}
			}
		}

		// --- INFORMACOES PARA SQLSERVER ----
		if (strSGBDPrincipal.equalsIgnoreCase(SQLSERVER)) {
			sqlAux = sqlAux.replaceAll("LENGTH", "LEN");
			sqlAux = sqlAux.replaceAll("UCASE", "UPPER");
		}

		if (strSGBDPrincipal.equalsIgnoreCase(MYSQL)) {
			sqlAux = sqlAux.toLowerCase();
		}

		// Converte Elementos configurados conforme o banco de dados.
		XmlReadDBItemConvertion xmlReadDB = XmlReadDBItemConvertion.getInstance(strSGBDPrincipal);
		if (xmlReadDB != null) {
			Collection col = xmlReadDB.getItens();
			if (col != null && col.size() > 0) {
				for (Iterator it = col.iterator(); it.hasNext();) {
					DBItemConvertion dbItemConvertion = (DBItemConvertion) it.next();
					sqlAux = sqlAux.replaceAll(dbItemConvertion.getNameToBeConverted().toUpperCase(), dbItemConvertion.getNameAfterConversion());
				}
			}
		}

		while (sqlAux.toUpperCase().indexOf("{GETPARAMETER") > -1) {
			String valorParametro = "NULL";
			int pos = sqlAux.toUpperCase().indexOf("{GETPARAMETER");
			int ini = sqlAux.indexOf("(", pos);
			int fim = sqlAux.indexOf(")", ini);
			int sep = sqlAux.indexOf(",", ini);
			String nomeModulo = sqlAux.substring(ini + 1, sep);
			String nomeParametro = sqlAux.substring(sep + 1, fim);
			try {
				ParametroDao parametroDao = new ParametroDao();
				ParametroDTO parametroDto = parametroDao.getValue(nomeModulo, nomeParametro, new Integer(Constantes.getValue("ID_EMPRESA_PROC_BATCH")));
				if (parametroDto.getValor() != null && !parametroDto.getValor().trim().equals("")) {
					valorParametro = parametroDto.getValor();
					valorParametro = valorParametro.replaceAll("\n", "");
					valorParametro = valorParametro.replaceAll("\r", "");
					valorParametro = valorParametro.replaceAll("\\\n", "");
					valorParametro = valorParametro.replaceAll("\\\r", "");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			sqlAux = sqlAux.substring(0, pos) + valorParametro + sqlAux.substring(fim + 2, sqlAux.length());
		}

		sqlAux = sqlAux.replaceAll("\\{OWNER\\}", Constantes.getValue("OWNER_DB"));
		sqlAux = sqlAux.replaceAll("\\{OWNER_BD\\}", Constantes.getValue("OWNER_DB"));
		sqlAux = sqlAux.replaceAll("citsaudebb.", Constantes.getValue("OWNER_DB")); // ISTO FORCA COMPATIBILIDADE DO CITGESMT
		sqlAux = sqlAux.replaceAll("CITSAUDEBB.", Constantes.getValue("OWNER_DB")); // ISTO FORCA COMPATIBILIDADE DO CITGESMT

		return sqlAux;
	}
}
