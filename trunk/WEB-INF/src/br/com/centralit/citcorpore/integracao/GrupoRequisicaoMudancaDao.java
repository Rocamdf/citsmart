package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.GrupoRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.ProblemaMudancaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;

/**
 * @author mario.haysaki
 *
 */
public class GrupoRequisicaoMudancaDao extends CrudDaoDefaultImpl {

	private static final long serialVersionUID = 1L;

	public GrupoRequisicaoMudancaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws Exception {
		return find(obj);
	}

	@Override
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idgruporequisicaomudanca", "idGrupoRequisicaoMudanca", true, true, false, false));
		listFields.add(new Field("idgrupo", "idGrupo", false, false, false, false));
		listFields.add(new Field("idrequisicaomudanca", "idRequisicaoMudanca", false, false, false, false));
		listFields.add(new Field("nomegrupo", "nomeGrupo", false, false, false, false));
		listFields.add(new Field("datafim" ,"dataFim", false, false, false, false));		
		return listFields;
	}

	@Override
	public String getTableName() {
		return  this.getOwner() +"gruporequisicaomudanca";
	}

	@Override
	public Collection list() throws Exception {
		return super.list("idGrupoRequisicaoMudanca");
	}

	@Override
	public Class getBean() {
		return GrupoRequisicaoMudancaDTO.class;
	}
	
	public Collection findByIdGrupoRequisicaoMudanca(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idGrupoRequisicaoMudanca", "=", parm)); 
		ordenacao.add(new Order("idGrupoRequisicaoMudanca"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdGrupoRequisicaoMudanca(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idGrupoRequisicaoMudanca", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdGrupo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idGrupo", "=", parm)); 
		ordenacao.add(new Order("idGrupo"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdGrupo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idGrupo", "=", parm));
		super.deleteByCondition(condicao);
	}
	
	public Collection findByIdRequisicaoMudanca(Integer parm) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		StringBuffer sql =  new StringBuffer();
		sql.append(" SELECT distinct grupo.idgrupo, grupo.nomegrupo, grupo.idgruporequisicaomudanca, grupo.idrequisicaomudanca "
				+ " FROM gruporequisicaomudanca grupo "
				+ " JOIN requisicaomudanca rm ON grupo.idrequisicaomudanca = rm.idrequisicaomudanca "
				+ " WHERE rm.idrequisicaomudanca = ? and grupo.datafim is null ORDER BY grupo.idgrupo ");
		parametro.add(parm);
		list = this.execSQL(sql.toString(), parametro.toArray());
		fields.add("idGrupo");
		fields.add("nomeGrupo");
		if (list != null && !list.isEmpty()) {
			return (List<GrupoRequisicaoMudancaDTO>) this.listConvertion(getBean(), list, fields);
		} else {
			return null;
		}		
	}
	
	public Collection findByIdMudancaEDataFim(Integer idRequisicaoMudanca) throws Exception {
		List fields = new ArrayList();
		List parametro = new ArrayList();
		StringBuffer sql =  new StringBuffer();
		
		
	  sql.append("select idgruporequisicaomudanca, idgrupo, idrequisicaomudanca, nomegrupo, datafim from gruporequisicaomudanca WHERE idrequisicaomudanca = ? and datafim is null");
	  parametro.add(idRequisicaoMudanca);
	  List resultado = 	this.execSQL(sql.toString(),  parametro.toArray());
	  
	  fields.add("idGrupoRequisicaoMudanca");
	  fields.add("idGrupo");
	  fields.add("idRequisicaoMudanca");
	  fields.add("nomeGrupo");
	  fields.add("dataFim");
	  
	  return listConvertion(getBean(), resultado,fields) ;
	}
	
	public Collection listByIdHistoricoMudanca(Integer idHistoricoMudanca) throws Exception {
		List fields = new ArrayList(); 
		
		
		String sql = "select distinct pr.idproblemamudanca, pr.idproblema, pr.idrequisicaomudanca, pr.datafim "+
				"from problemamudanca pr "+
				"inner join ligacao_mud_hist_pr ligpr on ligpr.idproblemamudanca = pr.idproblemamudanca "+
				 "WHERE ligpr.idhistoricomudanca = ?";
		
		List resultado = 	execSQL(sql, new Object[]{idHistoricoMudanca});
		
		
		
		fields.add("idProblemaMudanca");
		fields.add("idProblema");
		fields.add("idRequisicaoMudanca");
		fields.add("dataFim");
		
		return listConvertion(getBean(), resultado,fields) ;
	}
	
	public void deleteByIdRequisicaoMudanca(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idRequisicaoMudanca", "=", parm));
		super.deleteByCondition(condicao);
	}
	
	
	
}
