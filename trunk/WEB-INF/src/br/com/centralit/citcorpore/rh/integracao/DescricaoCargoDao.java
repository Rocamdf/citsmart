package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.DescricaoCargoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

//Substituir NomeDaClasseDao
@SuppressWarnings({"rawtypes","unchecked"})
public class DescricaoCargoDao extends CrudDaoDefaultImpl {
	
	private static final long serialVersionUID = 1L;

	public DescricaoCargoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idDescricaoCargo", "idDescricaoCargo", true, true, false, false));
		listFields.add(new Field("nomeCargo", "nomeCargo", false, false, false, false));
		listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", false, false, false, false));
		listFields.add(new Field("idCbo", "idCbo", false, false, false, false));
		listFields.add(new Field("atividades", "atividades", false, false, false, false));
		listFields.add(new Field("observacoes", "observacoes", false, false, false, false));
		listFields.add(new Field("situacao", "situacao", false, false, false, false));
		listFields.add(new Field("idParecerValidacao", "idParecerValidacao", false, false, false, false));
		
		return listFields;
	}
	
	// Substituir NomeDaTabela
	public String getTableName() {
		return "RH_DescricaoCargo";
	}

	
	// Substituir NomeDoDTO
	public Class getBean() {
		
		return DescricaoCargoDTO.class;
	}


    public Collection list() throws Exception {
        List list = new ArrayList();
        list.add(new Order("nomeCargo"));
        return super.list(list);
    }

	public DescricaoCargoDTO findByIdSolicitacaoServico(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idSolicitacaoServico", "=", parm)); 
		ordenacao.add(new Order("idDescricaoCargo"));
		List<DescricaoCargoDTO> result = (List<DescricaoCargoDTO>) super.findByCondition(condicao, ordenacao);
		if (result != null && !result.isEmpty())
			return result.get(0);
		else
			return null;
	}
}
