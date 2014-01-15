package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ProblemaMudancaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoProblemaDTO;
import br.com.centralit.citcorpore.integracao.ProblemaMudancaDAO;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoProblemaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
@SuppressWarnings("rawtypes")
public class SolicitacaoServicoProblemaServiceEjb extends CrudServicePojoImpl implements SolicitacaoServicoProblemaService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		// TODO Auto-generated method stub
		return new SolicitacaoServicoProblemaDao();
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
	public Collection findByIdProblema(Integer parm) throws Exception {
		SolicitacaoServicoProblemaDao solicitacaoServicoProblemaDao =  new SolicitacaoServicoProblemaDao();
		return solicitacaoServicoProblemaDao.findByIdProblema(parm);
	}
	
	public SolicitacaoServicoProblemaDTO restoreByIdProblema(Integer idProblema) throws Exception {
		try {
			SolicitacaoServicoProblemaDao dao = new SolicitacaoServicoProblemaDao();
			return dao.restoreByIdProblema(idProblema);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
