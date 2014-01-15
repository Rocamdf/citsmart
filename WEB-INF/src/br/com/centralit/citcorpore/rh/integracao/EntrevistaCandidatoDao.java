package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.EntrevistaCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.TriagemRequisicaoPessoalDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({"rawtypes","unchecked"})//Substituir NomeDaClasseDao
public class EntrevistaCandidatoDao extends CrudDaoDefaultImpl {
	
	private static final long serialVersionUID = 1L;

	public EntrevistaCandidatoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idEntrevista", "idEntrevista", true, true, false, false));
		listFields.add(new Field("idCurriculo", "idCurriculo", false, false, false, false));
		listFields.add(new Field("idEntrevistadorRH", "idEntrevistadorRH", false, false, false, false));
		listFields.add(new Field("idEntrevistadorGestor", "idEntrevistadorGestor", false, false, false, false));		
		listFields.add(new Field("idTriagem", "idTriagem", false, false, false, false));
		listFields.add(new Field("dataHora", "dataHora", false, false, false, false));
		listFields.add(new Field("caracteristicas", "caracteristicas", false, false, false, false));
		listFields.add(new Field("trabalhoEmEquipe", "trabalhoEmEquipe", false, false, false, false));
		listFields.add(new Field("possuiOutraAtividade", "possuiOutraAtividade", false, false, false, false));
		listFields.add(new Field("outraAtividade", "outraAtividade", false, false, false, false));
		listFields.add(new Field("concordaExclusividade", "concordaExclusividade", false, false, false, false));
		listFields.add(new Field("salarioAtual", "salarioAtual", false, false, false, false));
		listFields.add(new Field("pretensaoSalarial", "pretensaoSalarial", false, false, false, false));
		listFields.add(new Field("dataDisponibilidade", "dataDisponibilidade", false, false, false, false));
		listFields.add(new Field("competencias", "competencias", false, false, false, false));
		listFields.add(new Field("observacoes", "observacoes", false, false, false, false));
		listFields.add(new Field("resultado", "resultado", false, false, false, false));
		listFields.add(new Field("planoCarreira", "planoCarreira", false, false, false, false));
		listFields.add(new Field("metodosAdicionais", "metodosAdicionais", false, false, false, false));
		listFields.add(new Field("notaAvaliacao", "notaAvaliacao", false, false, false, false));
		listFields.add(new Field("classificacao", "classificacao", false, false, false, false));
		listFields.add(new Field("adimitido", "adimitido", false, false, false, false));
		
		return listFields;
	}
	
	// Substituir NomeDaTabela
	public String getTableName() {
		return "RH_EntrevistaCandidato";
	}

	
	// Substituir NomeDoDTO
	public Class getBean() {
		
		return EntrevistaCandidatoDTO.class;
	}


    public Collection list() throws Exception {
        List list = new ArrayList();
        list.add(new Order("idCurriculo"));
        return super.list(list);
    }

	public EntrevistaCandidatoDTO findByIdTriagemAndIdCurriculo(Integer idTriagem, Integer idCurriculo) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idTriagem", "=", idTriagem)); 
		condicao.add(new Condition("idCurriculo", "=", idCurriculo));
		ordenacao.add(new Order("idTriagem"));
		List<EntrevistaCandidatoDTO> result = (List<EntrevistaCandidatoDTO>) super.findByCondition(condicao, ordenacao);
		if (result != null && !result.isEmpty())
			return result.get(0);
		else
			return null;
		
	}
	
	public Collection findByIdTriagemAndResultado(Integer idTriagem, String resultado) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idTriagem", "=", idTriagem)); 
		condicao.add(new Condition("resultado", "=", resultado)); 
		ordenacao.add(new Order("idTriagem"));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection findFinalizadasByIdTriagemAndResultado(Integer idTriagem, String resultado) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idEntrevistadorRH", ">", new Integer(0))); 
		condicao.add(new Condition("idEntrevistadorGestor", ">", new Integer(0))); 
		condicao.add(new Condition("idTriagem", "=", idTriagem)); 
		condicao.add(new Condition("resultado", "=", resultado)); 
		ordenacao.add(new Order("idTriagem"));
		return super.findByCondition(condicao, ordenacao);
	}
	
	public Collection listCurriculosAprovadosPorOrdemMaiorNota(Integer idTriagem) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("resultado", "=", "A")); 
		condicao.add(new Condition("idTriagem", "=", idTriagem)); 
//		condicao.add(new Condition("classificacao", "<>", "R")); 
		ordenacao.add(new Order("notaAvaliacao", Order.DESC));
		return super.findByCondition(condicao, ordenacao);
	}
	
	@Override
	public void updateNotNull(IDto obj) throws Exception {
		// TODO Auto-generated method stub
		super.updateNotNull(obj);
	}
	
	public Boolean seCandidatoAprovado(TriagemRequisicaoPessoalDTO triagemRequisicaoPessoalDTO) throws Exception {
		List parametro = new ArrayList();
		String  sql = "SELECT * FROM rh_entrevistacandidato WHERE idtriagem = ? and resultado = 'A' ";
		
		if (triagemRequisicaoPessoalDTO != null && triagemRequisicaoPessoalDTO.getIdTriagem() != null) {
			parametro.add(triagemRequisicaoPessoalDTO.getIdTriagem());
		}
		List list = this.execSQL(sql.toString(), parametro.toArray());
		if (list == null || list.size() == 0)
			return false;
		else
			return true;
	}
	
}
