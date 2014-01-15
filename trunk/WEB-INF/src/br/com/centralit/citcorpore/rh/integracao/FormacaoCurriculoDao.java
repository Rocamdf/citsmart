package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.FormacaoCurriculoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class FormacaoCurriculoDao extends CrudDaoDefaultImpl {
	
	public FormacaoCurriculoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public Collection getFields() {
		List listFields = new ArrayList();
		
		listFields.add(new Field("idFormacao" ,"idFormacao", true, true, false, false));
		listFields.add(new Field("idTipoFormacao" ,"idTipoFormacao", false, false, false, false));
		listFields.add(new Field("instituicao" ,"instituicao", false, false, false, false));
		listFields.add(new Field("idSituacao" ,"idSituacao", false, false, false, false));
		listFields.add(new Field("descricao" ,"descricao", false, false, false, false));
		listFields.add(new Field("idCurriculo" ,"idCurriculo", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "RH_FormacaoCurriculo";
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return FormacaoCurriculoDTO.class;
	}
	
	public Collection findByIdCurriculo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCurriculo", "=", parm)); 
		ordenacao.add(new Order("idFormacao"));
		List<FormacaoCurriculoDTO> result = (List<FormacaoCurriculoDTO>) super.findByCondition(condicao, ordenacao);
		
		return result;
	}
	public void deleteByIdCurriculo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCurriculo", "=", parm));
		super.deleteByCondition(condicao);
	}
}
