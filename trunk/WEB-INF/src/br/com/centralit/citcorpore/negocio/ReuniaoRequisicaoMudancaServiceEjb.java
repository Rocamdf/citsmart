package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.AtividadePeriodicaDao;
import br.com.centralit.citcorpore.integracao.ReuniaoRequisicaoMudancaDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class ReuniaoRequisicaoMudancaServiceEjb extends CrudServicePojoImpl implements ReuniaoRequisicaoMudancaService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new ReuniaoRequisicaoMudancaDAO();
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
	public Collection findByIdRequisicaoMudanca(Integer parm) throws Exception {
		ReuniaoRequisicaoMudancaDAO dao = new ReuniaoRequisicaoMudancaDAO();
		try{
			return dao.findByIdRequisicaoMudanca(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
