package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.ExperienciaProfissionalCurriculoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "serial", "rawtypes", "unused" })
public class ExperienciaProfissionalCurriculoDao extends CrudDaoDefaultImpl {
	public ExperienciaProfissionalCurriculoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public Collection getFields() {
		List listFields = new ArrayList();
		
		listFields.add(new Field("idExperienciaProfissional" ,"idExperienciaProfissional", true, true, false, false));
		listFields.add(new Field("periodo" ,"periodo", false, false, false, false));
		listFields.add(new Field("funcao" ,"funcao", false, false, false, false));
		listFields.add(new Field("descricaoEmpresa" ,"descricaoEmpresa", false, false, false, false));
		listFields.add(new Field("idCurriculo" ,"idCurriculo", false, false, false, false));
		listFields.add(new Field("localidade" ,"localidade", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "RH_ExperienciaProfissionalCurriculo";
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return ExperienciaProfissionalCurriculoDTO.class;
	}
	
	public Collection findByIdCurriculo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCurriculo", "=", parm)); 
		ordenacao.add(new Order("idExperienciaProfissional"));
		List<ExperienciaProfissionalCurriculoDTO> result = (List<ExperienciaProfissionalCurriculoDTO>) super.findByCondition(condicao, ordenacao);
		
		return result;
	}
	
	public void deleteByIdCurriculo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCurriculo", "=", parm));
		super.deleteByCondition(condicao);
	}
}
