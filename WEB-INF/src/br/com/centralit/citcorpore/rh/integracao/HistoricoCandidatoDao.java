package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.HistoricoCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.IdiomaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

//Substituir NomeDaClasseDao
public class HistoricoCandidatoDao extends CrudDaoDefaultImpl {
	
	public HistoricoCandidatoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
	    

		listFields.add(new Field("idHistoricoCandidato", "idHistoricoCandidato", true, true, false, false));
		listFields.add(new Field("idEntrevista", "idEntrevista", false, false, false, false));
		listFields.add(new Field("idCurriculo", "idCurriculo", false, false, false, false));
		listFields.add(new Field("resultado", "resultado", false, false, false, false));
		listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", false, false, false, false));
		
		return listFields;
	}
	
	// Substituir NomeDaTabela
	public String getTableName() {
		return "RH_HISTORICOCANDIDATO";
	}

	
	// Substituir NomeDoDTO
	public Class getBean() {
		
		return HistoricoCandidatoDTO.class;
	}


    public Collection list() throws Exception {
        List list = new ArrayList();
        list.add(new Order("idCurriculo"));
        return super.list(list);
    }
    
    public Collection listByIdCurriculo(Integer idCurriculo) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCurriculo", "=", idCurriculo)); 
		ordenacao.add(new Order("idCurriculo"));
		List list = (List) super.findByCondition(condicao, ordenacao);
		return list;
	}

}
