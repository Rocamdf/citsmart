package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.ExperienciaProfissionalRequisicaoPessoalDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ExperienciaProfissionalRequisicaoPessoalDao extends CrudDaoDefaultImpl {

	public ExperienciaProfissionalRequisicaoPessoalDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public Collection getFields() {
		List listFields = new ArrayList();
		
		listFields.add(new Field("idExperienciaProfissional" ,"idExperienciaProfissional", true, true, false, false));
		listFields.add(new Field("idCurriculo" ,"idCurriculo", false, false, false, false));
		listFields.add(new Field("descricaoEmpresa" ,"descricaoEmpresa", true, false, false, false));
		listFields.add(new Field("descricaoCargo" ,"descricaoCargo", true, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "RH_ExperienciaProfissionalRequisicaoPessoal";
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return ExperienciaProfissionalRequisicaoPessoalDTO.class;
	}
	
	public Collection findByIdCurriculo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCurriculo", "=", parm)); 
		ordenacao.add(new Order("idExperienciaProfissional"));
		return super.findByCondition(condicao, ordenacao);
	}
	
}
