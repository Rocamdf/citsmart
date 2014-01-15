package br.com.centralit.citcorpore.integracao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import br.com.centralit.citcorpore.bean.ExternalConnectionDTO;
import br.com.centralit.citcorpore.bean.ImportManagerDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;;

public class ExternalConnectionDao extends CrudDaoDefaultImpl {
	public ExternalConnectionDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idExternalConnection" ,"idExternalConnection", true, true, false, false));
		listFields.add(new Field("nome" ,"nome", false, false, false, false));
		listFields.add(new Field("tipo" ,"tipo", false, false, false, false));
		listFields.add(new Field("urlJdbc" ,"urlJdbc", false, false, false, false));
		listFields.add(new Field("jdbcDbName" ,"jdbcDbName", false, false, false, false));
		listFields.add(new Field("jdbcDriver" ,"jdbcDriver", false, false, false, false));
		listFields.add(new Field("jdbcUser" ,"jdbcUser", false, false, false, false));
		listFields.add(new Field("jdbcPassword" ,"jdbcPassword", false, false, false, false));
		listFields.add(new Field("fileName" ,"fileName", false, false, false, false));
		listFields.add(new Field("schemadb" ,"schemadb", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ExternalConnection";
	}
	public Collection list() throws Exception {
		return super.list("nome");
	}

	public Class getBean() {
		return ExternalConnectionDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public Object getLastValueFromDestino(ImportManagerDTO importManagerDTO, String idDestino) throws PersistenceException{
		String sql = "SELECT MAX(" + idDestino + ") FROM " + importManagerDTO.getTabelaDestino();
		List lstData = this.execSQL(sql, null);
		if (lstData != null && lstData.size() > 0){
			Object o = ((Object[])lstData.get(0))[0];
			if (o == null){
				return 1;
			}
			if (o instanceof Integer){
				Integer intAux = (Integer)o;
				return intAux.intValue() + 1;
			}
			if (o instanceof Long){
				Long aux = (Long)o;
				return aux.longValue() + 1;
			}
			if (o instanceof Double){
				Double aux = (Double)o;
				return aux.doubleValue() + 1;
			}
			if (o instanceof BigDecimal){
				BigDecimal aux = (BigDecimal)o;
				return aux.doubleValue() + 1;
			}
			if (o instanceof BigInteger){
				BigInteger aux = (BigInteger)o;
				return aux.doubleValue() + 1;
			}	
			//Se chegou aqui, eh que nao passou por lugar algum acima.
			Integer intAux = (Integer)o;
			return intAux.intValue() + 1;			
		}
		return 1;
	}	
	public void executeSQLUpdate(String sql, Object[] objs) throws PersistenceException{
		this.execUpdate(sql, objs);
	}
}
