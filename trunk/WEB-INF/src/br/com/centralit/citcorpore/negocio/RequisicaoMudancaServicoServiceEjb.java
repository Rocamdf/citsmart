/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.RequisicaoMudancaServicoDTO;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaServicoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class RequisicaoMudancaServicoServiceEjb extends CrudServicePojoImpl implements RequisicaoMudancaServicoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		return new RequisicaoMudancaServicoDao();
	}

	public RequisicaoMudancaServicoDao getRequisicaoMudancaServicoDao() throws ServiceException {
		return (RequisicaoMudancaServicoDao) this.getDao();
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
	public ArrayList<RequisicaoMudancaServicoDTO> listByIdRequisicaoMudanca(Integer idRequisicaoMudanca) throws ServiceException, Exception {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();

		condicoes.add(new Condition("idRequisicaoMudanca", "=", idRequisicaoMudanca));

		return (ArrayList<RequisicaoMudancaServicoDTO>) getRequisicaoMudancaServicoDao().findByCondition(condicoes, null);
	}

	/**
	 * Retorna o item de relacionamento específico sem a chave primária da tabela. Uma espécie de consulta por chave composta.
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public RequisicaoMudancaServicoDTO restoreByChaveComposta(RequisicaoMudancaServicoDTO dto) throws ServiceException, Exception {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();

		condicoes.add(new Condition("idRequisicaoMudanca", "=", dto.getIdRequisicaoMudanca()));
		condicoes.add(new Condition("idServico", "=", dto.getIdServico()));

		ArrayList<RequisicaoMudancaServicoDTO> retorno = (ArrayList<RequisicaoMudancaServicoDTO>) getRequisicaoMudancaServicoDao().findByCondition(condicoes, null);
		if (retorno != null) {
			return retorno.get(0);
		}

		return null;
	}

}
