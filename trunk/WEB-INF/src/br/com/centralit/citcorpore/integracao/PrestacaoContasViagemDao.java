package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author ronnie.lopes
 * 
 */

@SuppressWarnings({ "rawtypes", "unchecked" })
public class PrestacaoContasViagemDao extends CrudDaoDefaultImpl {

	private static final long serialVersionUID = 1153789196419291108L;

	/**
     * 
     */
	public PrestacaoContasViagemDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	
	public Collection getFields() {
		Collection listFields = new ArrayList();

		listFields.add(new Field("idPrestacaoContasViagem", "idPrestacaoContasViagem", true, true, false, false));
		listFields.add(new Field("idResponsavel", "idResponsavel", false, false, false, false));
		listFields.add(new Field("idAprovacao", "idAprovacao", false, false, false, false));
		listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", false, false, false, false));
		listFields.add(new Field("idEmpregado", "idEmpregado", false, false, false, false));
		listFields.add(new Field("dataHora", "dataHora", false, false, false, false));
		listFields.add(new Field("situacao", "situacao", false, false, false, false));
		listFields.add(new Field("iditemtrabalho", "idItemTrabalho", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "prestacaoContasViagem";
	}

	public Collection find(IDto obj) throws Exception {
		List ordem = new ArrayList();
		ordem.add(new Order("idPrestacaoContasViagem"));
		return super.find(obj, ordem);
	}

	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("idPrestacaoContasViagem"));
		return super.list(list);
	}

	public Class getBean() {
		return PrestacaoContasViagemDTO.class;
	}
	
	/*Retorna um objeto PrestacaoContasViagemDTO por SolicitacaoServico e Empregado*/
	public PrestacaoContasViagemDTO findBySolicitacaoAndEmpregado(Integer idSolicitacaoServico, Integer idEmpregado) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition(Condition.AND ,"idEmpregado", "=", idEmpregado));
		List result = (List) super.findByCondition(condicao, null);
		if(result != null){
			return (PrestacaoContasViagemDTO) result.get(0);
		}
		return null;
	}
	
	public List findBySolicitacao(Integer idSolicitacaoServico, Integer idEmpregado) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		return (List) super.findByCondition(condicao, null);
	}
	
	public List findBySolicitacao(Integer idSolicitacaoServico) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("idItemTrabalho", "is", null));
		return (List) super.findByCondition(condicao, null);
	}
	public Collection findBySolicitacaoAndTafera(Integer idSolicitacaoServico) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
        condicao.add(new Condition("situacao", "=", PrestacaoContasViagemDTO.AGUARDANDO_CONFERENCIA)); 
        condicao.add(new Condition("idItemTrabalho", "IS", null)); 
        ordenacao.add(new Order("idPrestacaoContasViagem"));
		return  super.findByCondition(condicao, ordenacao);
	}
	public Collection findBySolicitacaoAndConferencia(Integer idSolicitacaoServico) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("situacao", "=", PrestacaoContasViagemDTO.AGUARDANDO_CONFERENCIA)); 
		condicao.add(new Condition("idItemTrabalho", "is", null)); 
		ordenacao.add(new Order("idPrestacaoContasViagem"));
		return  super.findByCondition(condicao, ordenacao);
	}
	
	public boolean isEstadoPrestacaoContas(RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
		List result = null;
		if(requisicaoViagemDto.getEstado().equalsIgnoreCase(RequisicaoViagemDTO.AGUARDANDO_PRESTACAOCONTAS)){
			if(requisicaoViagemDto.getTarefaIniciada() != null && requisicaoViagemDto.getTarefaIniciada().equalsIgnoreCase("S"))
				return false;
			List condicao = new ArrayList();
			List ordenacao = new ArrayList();
			condicao.add(new Condition("idSolicitacaoServico", "=", requisicaoViagemDto.getIdSolicitacaoServico()));
			condicao.add(new Condition("idItemTrabalho", "IS", null)); 
			ordenacao.add(new Order("idPrestacaoContasViagem"));
			
			result = (List) super.findByCondition(condicao, ordenacao);
			if(result == null)
				return true;
		}
		return  false;
	}
	
	public Collection findByCorrigirAndSolicitacao(Integer idSolicitacaoServico) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("situacao", "=", PrestacaoContasViagemDTO.NAO_APROVADA)); 
//		condicao.add(new Condition("idItemTrabalho", "is not", null)); 
		ordenacao.add(new Order("idPrestacaoContasViagem"));
		return  super.findByCondition(condicao, ordenacao);
	}
	
	public Collection findBySolicitacaoEmConferencia(Integer idSolicitacaoServico) throws Exception{
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("situacao", "=", PrestacaoContasViagemDTO.EM_CONFERENCIA)); 
		condicao.add(new Condition("idItemTrabalho", "is not", null)); 
		ordenacao.add(new Order("idPrestacaoContasViagem"));
		return  super.findByCondition(condicao, ordenacao);
	}


	public PrestacaoContasViagemDTO findBySolicitacaoAndTarefa(Integer idSolicitacaoServico, Integer idTarefa) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		List result = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("idItemTrabalho", "=", idTarefa));
		result = (List) findByCondition(condicao, ordenacao);
		if(result != null)
			return (PrestacaoContasViagemDTO) result.get(0);
		else
			return null;
	}
	
	public PrestacaoContasViagemDTO findNaoAprovados(Integer idSolicitacaoServico, Integer idTarefa) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		List result = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("idItemTrabalho", "is", null));
		condicao.add(new Condition("situacao", "=", PrestacaoContasViagemDTO.EM_CORRECAO));
		result = (List) findByCondition(condicao, ordenacao);
		
		return (PrestacaoContasViagemDTO) result.get(0);
	}
	
	public PrestacaoContasViagemDTO findByTarefaAndSolicitacao(Integer idTarefa, Integer idSolicitacaoServico) throws Exception{
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		List result = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("idItemTrabalho", "=", idTarefa));
		ordenacao.add(new Order("idPrestacaoContasViagem"));
		result = (List) super.findByCondition(condicao, ordenacao);
		
		if(result != null)
			return (PrestacaoContasViagemDTO) result.get(0);
		else
			return null;
	}
	
	public boolean verificaSeTodasPrestacaoAprovadas(Integer idSolicitacao) throws Exception{
		IntegranteViagemDao integrantesDao = new IntegranteViagemDao();
		Integer totalIntegrantes = integrantesDao.retornaQtdeIntegrantes(idSolicitacao);
		
		Collection<PrestacaoContasViagemDTO> colPrestacao = this.findBySolicitacao(idSolicitacao, null);
		if(colPrestacao != null){
			if(colPrestacao.size() != totalIntegrantes)
				return false;	
			for(PrestacaoContasViagemDTO prestacaoDto : colPrestacao){
				if(!prestacaoDto.getSituacao().equalsIgnoreCase(PrestacaoContasViagemDTO.APROVADA)){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public Collection findBySolicitacaoAndTaferaConferencia(Integer idSolicitacaoServico) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
        condicao.add(new Condition("situacao", "=", PrestacaoContasViagemDTO.EM_CONFERENCIA)); 
        condicao.add(new Condition("idItemTrabalho", "IS", null)); 
        ordenacao.add(new Order("idPrestacaoContasViagem"));
		return  super.findByCondition(condicao, ordenacao);
	}
	public Collection findBySolicitacaoAndTaferaCorrecao(Integer idSolicitacaoServico) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("situacao", "=", PrestacaoContasViagemDTO.EM_CORRECAO)); 
		condicao.add(new Condition("idItemTrabalho", "IS", null)); 
		ordenacao.add(new Order("idPrestacaoContasViagem"));
		return  super.findByCondition(condicao, ordenacao);
	}
	
	public Collection findBySolicitacaoEmpregadoSeCorrecao(Integer idSolicitacaoServico, Integer idEmpregado) throws Exception{
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("situacao", "=", PrestacaoContasViagemDTO.EM_CORRECAO)); 
		condicao.add(new Condition("idEmpregado", "=", idEmpregado)); 
		ordenacao.add(new Order("idPrestacaoContasViagem"));
		return  super.findByCondition(condicao, ordenacao);
	}
	
}
