package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CategoriaSolucaoDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

public class CategoriaSolucaoDao extends CrudDaoDefaultImpl {
	public CategoriaSolucaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idCategoriaSolucao" ,"idCategoriaSolucao", true, true, false, false));
		listFields.add(new Field("idCategoriaSolucaoPai" ,"idCategoriaSolucaoPai", false, false, false, false));
		listFields.add(new Field("descricaoCategoriaSolucao" ,"descricaoCategoriaSolucao", false, false, false, false));
		listFields.add(new Field("dataInicio" ,"dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim" ,"dataFim", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "CategoriaSolucao";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return CategoriaSolucaoDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public Collection findByIdCategoriaSolucaoPai(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCategoriaSolucaoPai", "=", parm)); 
		ordenacao.add(new Order("descricaoCategoriaSolucao"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdCategoriaSolucaoPai(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCategoriaSolucaoPai", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findSemPai() throws Exception {
		String sql = "SELECT idCategoriaSolucao, idCategoriaSolucaoPai, descricaoCategoriaSolucao, dataInicio, dataFim FROM categoriasolucao WHERE idCategoriaSolucaoPai IS NULL AND dataFim IS NULL AND ";
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) 
			sql += "(UPPER(deleted) IS NULL OR UPPER(deleted) = 'N') ";
		 else if(CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER))
			sql += "(deleted IS NULL OR deleted = 'N') ";
		 else
			 sql += "(deleted IS NULL OR deleted = 'N') ";
		sql += " ORDER BY descricaoCategoriaSolucao ";
		
	    List colDados = this.execSQL(sql, null);
	    if (colDados != null){
		List fields = new ArrayList();
		fields.add("idCategoriaSolucao");
		fields.add("idCategoriaSolucaoPai");
		fields.add("descricaoCategoriaSolucao");
		fields.add("dataInicio");
		fields.add("dataFim");
		return this.listConvertion(CategoriaSolucaoDTO.class, colDados, fields);
	    }
	    return null;
	}
	public Collection findByIdPai(Integer idPaiParm) throws Exception {
		
	    String sql = "SELECT idCategoriaSolucao, idCategoriaSolucaoPai, descricaoCategoriaSolucao, dataInicio, dataFim FROM categoriasolucao " +
	    		"WHERE idCategoriaSolucaoPai = ? AND dataFim IS NULL AND ";
	    if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) 
			sql += "(UPPER(deleted) IS NULL OR UPPER(deleted) = 'N') ";
		 else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) 
			sql += "(deleted IS NULL OR deleted = 'N') ";
		 else 
			 sql += "(deleted IS NULL OR deleted = 'N') ";
		sql += "ORDER BY descricaoCategoriaSolucao ";
	    
	    
	    List colDados = this.execSQL(sql, new Object[] {idPaiParm});
	    if (colDados != null){
		List fields = new ArrayList();
		fields.add("idCategoriaSolucao");
		fields.add("idCategoriaSolucaoPai");
		fields.add("descricaoCategoriaSolucao");
		fields.add("dataInicio");
		fields.add("dataFim");
		return this.listConvertion(CategoriaSolucaoDTO.class, colDados, fields);
	    }
	    return null;
	}	
}
