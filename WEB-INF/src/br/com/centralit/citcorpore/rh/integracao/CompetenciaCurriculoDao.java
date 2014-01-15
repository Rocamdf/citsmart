package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.CompetenciaCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.EmailCurriculoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "serial", "rawtypes", "unused" })
public class CompetenciaCurriculoDao extends CrudDaoDefaultImpl {
	public CompetenciaCurriculoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto obj) throws Exception {
		List lst = new ArrayList();
		lst.add("idCompetencia");
		return super.find(obj, lst);
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		
        listFields.add(new Field("idCompetencia", "idCompetencia", true, true, false, false));
        listFields.add(new Field("descricaoCompetencia", "descricaoCompetencia", false, false, false, false));
        listFields.add(new Field("idCurriculo" ,"idCurriculo", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "rh_competencia";
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return CompetenciaCurriculoDTO.class;
	}
	
	public Collection findByIdCurriculo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCurriculo", "=", parm)); 
		ordenacao.add(new Order("idCompetencia"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdCurriculo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCurriculo", "=", parm));
		super.deleteByCondition(condicao);
	}
}
