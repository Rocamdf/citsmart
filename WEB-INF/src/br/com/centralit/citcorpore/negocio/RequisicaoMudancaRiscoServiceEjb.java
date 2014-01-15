package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.RequisicaoMudancaLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaRiscoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaServicoDTO;
import br.com.centralit.citcorpore.integracao.ProblemaMudancaDAO;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaRiscoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaServicoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class RequisicaoMudancaRiscoServiceEjb extends CrudServicePojoImpl implements RequisicaoMudancaRiscoService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public RequisicaoMudancaRiscoDTO restoreByChaveComposta(
			RequisicaoMudancaRiscoDTO dto) throws ServiceException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new RequisicaoMudancaRiscoDao();
	}
	
	public RequisicaoMudancaRiscoDao getRequisicaoMudancaRiscoDao() throws ServiceException {
		return (RequisicaoMudancaRiscoDao) this.getDao();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaDelete(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaFind(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<RequisicaoMudancaRiscoDTO> listByIdRequisicaoMudanca(Integer idRequisicaoMudanca) throws ServiceException, Exception {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();

		condicoes.add(new Condition("idRequisicaoMudanca", "=", idRequisicaoMudanca));

		return (ArrayList<RequisicaoMudancaRiscoDTO>) getRequisicaoMudancaRiscoDao().findByCondition(condicoes, null);
	}
	
	public Collection findByIdRequisicaoMudanca(Integer parm) throws Exception {
		RequisicaoMudancaRiscoDao dao = new RequisicaoMudancaRiscoDao();
		try {
			return dao.findByIdRequisicaoMudancaEDataFim(parm);
					/*findByIdRequisicaoMudanca(parm);*/
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
}
