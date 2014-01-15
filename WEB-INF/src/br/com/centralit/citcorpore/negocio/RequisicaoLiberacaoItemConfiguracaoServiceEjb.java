/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaItemConfiguracaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
@SuppressWarnings("rawtypes")
public class RequisicaoLiberacaoItemConfiguracaoServiceEjb extends CrudServicePojoImpl implements RequisicaoLiberacaoItemConfiguracaoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RequisicaoLiberacaoItemConfiguracaoDao requisicaoLiberacaoItemConfiguracaoDao;

	protected CrudDAO getDao() throws ServiceException {
		return new RequisicaoLiberacaoItemConfiguracaoDao();
	} 
	
	private RequisicaoLiberacaoItemConfiguracaoDao getRequisicaoLiberacaoItemConfiguracaoDao() throws Exception {
		if(requisicaoLiberacaoItemConfiguracaoDao == null){
			requisicaoLiberacaoItemConfiguracaoDao = new RequisicaoLiberacaoItemConfiguracaoDao();
		}				
		return requisicaoLiberacaoItemConfiguracaoDao;
	}
	


	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}


	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<RequisicaoLiberacaoItemConfiguracaoDTO> listByIdRequisicaoLiberacao(Integer idRequisicaoLiberacao) throws ServiceException, Exception {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		
		condicoes.add(new Condition("idRequisicaoLiberacao", "=", idRequisicaoLiberacao));
		
		return (ArrayList<RequisicaoLiberacaoItemConfiguracaoDTO>) getRequisicaoLiberacaoItemConfiguracaoDao().findByCondition(condicoes, null);
	}
	
	/**
	 * Retorna o item de relacionamento específico sem a chave primária da tabela.
	 * Uma espécie de consulta por chave composta.
	 * 
	 * @param dto
	 * @return
	 * @throws Exception 
	 * @throws ServiceException 
	 */
	@SuppressWarnings("unchecked")
	public RequisicaoMudancaItemConfiguracaoDTO restoreByChaveComposta(RequisicaoMudancaItemConfiguracaoDTO dto) throws ServiceException, Exception{
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		
		condicoes.add(new Condition("idRequisicaoMudanca", "=", dto.getIdRequisicaoMudanca()));
		condicoes.add(new Condition("idItemConfiguracao", "=", dto.getIdItemConfiguracao()));
		
		ArrayList<RequisicaoMudancaItemConfiguracaoDTO> retorno = (ArrayList<RequisicaoMudancaItemConfiguracaoDTO>)
																			getRequisicaoLiberacaoItemConfiguracaoDao().findByCondition(condicoes, null); 
		
		if(retorno != null){
			return retorno.get(0);
		}
		
		return null;
	}
	
	
	public Collection findByIdItemConfiguracao(Integer parm) throws Exception {
		RequisicaoMudancaItemConfiguracaoDao dao = new RequisicaoMudancaItemConfiguracaoDao();
		return dao.findByIdItemConfiguracao(parm);
	}

	@Override
	public RequisicaoLiberacaoItemConfiguracaoDTO restoreByChaveComposta(
			RequisicaoLiberacaoItemConfiguracaoDTO dto)
			throws ServiceException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection findByIdRequisicaoLiberacao(Integer parm) throws Exception {
		RequisicaoLiberacaoItemConfiguracaoDao dao = new RequisicaoLiberacaoItemConfiguracaoDao();
		
		return dao.findByIdRequisicaoLiberacao(parm);
	}

	@Override
	public RequisicaoLiberacaoItemConfiguracaoDTO findByIdReqLiberacao(
			Integer parm) throws Exception {
RequisicaoLiberacaoItemConfiguracaoDao dao = new RequisicaoLiberacaoItemConfiguracaoDao();
		
		return dao.findByIdReqLiberacao(parm);
	}

}
